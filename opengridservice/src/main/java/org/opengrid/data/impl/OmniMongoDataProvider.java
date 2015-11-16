package org.opengrid.data.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.opengrid.constants.Exceptions;
import org.opengrid.data.GenericRetrievable;
import org.opengrid.data.MongoDBHelper;
import org.opengrid.data.meta.OpenGridColumn;
import org.opengrid.data.meta.OpenGridDataset;
import org.opengrid.data.meta.OpenGridMeta;
import org.opengrid.exception.ServiceException;
import org.opengrid.util.ExceptionUtil;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class OmniMongoDataProvider implements GenericRetrievable {

	@Override
	public String getData(String dataSetId, String metaCollectionName, String filter, int max, String sort) throws ServiceException {		
		MongoDBHelper ds = null;
		MongoDatabase db = null;
				
		try {
			OpenGridDataset desc = this.getDescriptorInternal(metaCollectionName, dataSetId, false);
						
			if (desc.getDataSource() == null) {				
				throw ExceptionUtil.getException(Exceptions.ERR_SERVICE, "Missing data source info on descriptor for dataset '" + dataSetId + "'.");
			} else if (desc.getDataSource().getDbConnectionString() != null) {
				ds = new MongoDBHelper(
						desc.getDataSource().getDbConnectionString()
						);
			} else {
				//use default DB if there's no override
				ds = new MongoDBHelper();
			}
			
			db = ds.getConnection();
			MongoCollection<Document> c = db.getCollection(desc.getDataSource().getSourceName());
			
			BasicDBObject q = new BasicDBObject();	    		    	
			String baseFilter = desc.getDataSource().getBaseFilter(); 
			if (filter !=null && filter.length() > 0) {				
				if (baseFilter!=null) {
					//append base filter
					filter = "{ \"$and\": [" + filter + ", " + baseFilter + "] }";
				}
				q =  (BasicDBObject) JSON.parse(filter);
			} else {
				if (baseFilter !=null) {
					q = (BasicDBObject) JSON.parse(baseFilter);
				} else {
					//no filter
					q = (BasicDBObject) JSON.parse("{}");
				}				
			}

	    	FindIterable<Document> cur = c.find(q);
	    	
	    	//determine sort order
	    	BasicDBObject orderBy = null;
	    	if (sort !=null && sort.length() > 0) {
	    		//from client
	    		orderBy = (BasicDBObject) JSON.parse(sort);	    		
	    	} else if (desc.getOptions().getDefaultSort()!=null && desc.getOptions().getDefaultSort().length() > 0) {
	    		//from dataset descriptor
	    		orderBy = (BasicDBObject) JSON.parse(desc.getOptions().getDefaultSort());
	    	}
	    	if (orderBy !=null) {
	    		cur.sort(orderBy);
	    	}
	    	
	    	//return geoJson object as part of our mock implementation
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("{ \"type\" : \"FeatureCollection\", \"features\" : [");
	    	
	    	MongoCursor<Document> it = cur.iterator();	    	 
	    	
	    	int i=0;
	        while(it.hasNext()) {
	        	if (i==max)
	        		break;
	        	
	        	Document doc = it.next();
	        	if (i > 0)
	        		sb.append(",");
	        	sb.append(getFeature(doc, desc));
	        	i++;
	        	
	        	//temp limit
	        	//if (i==500) 
	        	//	break;
	        }
	        sb.append("],");
	        sb.append(getMeta(metaCollectionName, dataSetId));
	        sb.append("}");
	        
	        return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			
			//wrap and bubble up
			throw ExceptionUtil.getException(Exceptions.ERR_DB, ex.getMessage());
		} finally {
			if (ds !=null) {
				ds.closeConnection();
			}
		}
	}

	
	private String getFeature(Document doc2, OpenGridDataset desc) {

		String s = "{\"type\": \"Feature\", \"properties\": ";
		
		Document doc = new Document();
		//iterate through available columns and build JSON		
		for (OpenGridColumn c: desc.getColumns()) {
			
			//support dotNotation on the id
			//if (c.getDataSource() == null) {
			//	c.setDataSource(c.getId());
			//}
			Document o = resolveObject(doc2, c.getId());
			String colName = resolveName(c.getId());
			if ( o.containsKey(colName) ) {
				if (c.getDataType().equals("date")) {
					//format date to string
					
					//convert epoch to formatted date
					//SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy h:m a");
					SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
				
					//doc.put(c.getId(), f.format( new Date((Long) o.get(colName))) );
					doc.put(c.getId(), f.format( new Date(getDateLong(o.get(colName))) ));
				} else {
					doc.put(c.getId(), o.get(colName));					
				}
			}
		}
		s += doc.toJson();
		
		//build the coordinates in the geometry
		//lat long are required attributes
		if (!doc.containsKey(desc.getOptions().getLong())) {
			//default longitude value
			doc.put(desc.getOptions().getLong(), -1);
			//throw ExceptionUtil.getException(Exceptions.ERR_SERVICE, "Data is missing required '" +  desc.getOptions().getLong() + "' attribute for document " + doc.toJson());
		}
		
		String lng = doc.get( desc.getOptions().getLong() ).toString();
		
		if (!doc.containsKey(desc.getOptions().getLat())) {
			//throw ExceptionUtil.getException(Exceptions.ERR_SERVICE, "Data is missing required '" +  desc.getOptions().getLat() + "' attribute for document " + doc.toJson());
			//default latitude value
			doc.put(desc.getOptions().getLat(), -1);
		}

		String lat = doc.get (desc.getOptions().getLat() ).toString();
		
		s +=", \"geometry\": {\"type\": \"Point\", \"coordinates\": [" + lng + "," + lat + "]}, \"autoPopup\": false }";
		return s;		
	}
	
	private Long getDateLong(Object value) {
		if (value !=null) {
			if (value instanceof Double) {
				return ( (Double)value).longValue();
			} else if (value instanceof Long) {
				return (Long) value;
			} else 
				throw new ServiceException("Unsupported data type for date value");
			
		} else {
			return (new Long(-1));
		}
	}
	
	public String resolveName(String dataSource) {
		String[] s = dataSource.split("\\.");
		if (s.length > 0)
			return s[s.length - 1];
		else
			return dataSource;
	}

	//get Document that owns the field
	public Document resolveObject(Document doc2, String dataSource) {
		String[] s = dataSource.split("\\.");

		Document o = doc2;
		if (s.length > 1) {
			for (String c: s) {
				if (o.get(c) instanceof Document)				
					o = (Document) o.get(c);
				else
					break;
			}			
		}
		return o;
	}


	private String getMeta(String metaCollectionName, String dataSetId) throws JsonParseException, JsonMappingException, ServiceException, IOException {
		//return default descriptor, can be overridden by user preferences later
		//return "\"meta\": { \"view\": { \"id\": \"twitter\", \"displayName\": \"Twitter\", \"options\": { \"rendition\": { \"icon\":\"default\", \"color\": \"#001F7A\", \"fillColor\": \"#00FFFF\", \"opacity\":85, \"size\":6 } }, \"columns\": [ {\"id\":\"_id\", \"displayName\":\"ID\", \"dataType\":\"string\", \"filter\":false, \"popup\":false, \"list\":false}, {\"id\":\"date\", \"displayName\":\"Date\", \"dataType\":\"date\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":1}, {\"id\":\"screenName\", \"displayName\":\"Screen Name\", \"dataType\":\"string\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":2}, {\"id\":\"text\", \"displayName\":\"Text\", \"dataType\":\"string\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":3}, {\"id\":\"city\", \"displayName\":\"City\", \"dataType\":\"string\", \"filter\":true, \"popup\":true, \"list\":true, \"sortOrder\":4}, {\"id\":\"bio\", \"displayName\":\"Bio\", \"dataType\":\"string\",\"sortOrder\":5}, {\"id\":\"hashtags\", \"displayName\":\"Hashtags\", \"dataType\":\"string\", \"sortOrder\":6}, {\"id\":\"lat\", \"displayName\":\"Latitude\", \"dataType\":\"float\", \"list\":true, \"sortOrder\":7}, {\"id\":\"long\", \"displayName\":\"Longitude\", \"dataType\":\"float\", \"list\":true, \"sortOrder\":8} ] } }";
		return "\"meta\": { \"view\": " + getDescriptor(metaCollectionName, dataSetId).toString() + " }";
	}

	
	
	@Override
	public OpenGridDataset getDescriptor(String metaCollectionName, String dataSetId) throws ServiceException, JsonParseException, JsonMappingException, IOException {
		return this.getDescriptorInternal(metaCollectionName, dataSetId, true);		
	}

	
	
	@Override
	public OpenGridDataset getDescriptorInternal(String metaCollectionName, String dataSetId, boolean removePrivates) throws ServiceException, JsonParseException, JsonMappingException, IOException {
		MongoDBHelper ds = new MongoDBHelper();
				
		try {
			MongoDatabase db = ds.getConnection();
			
			MongoCollection<Document> c = db.getCollection(metaCollectionName);

			//we only have one doc in our meta collection
			FindIterable<Document> docs = c.find();
			Document doc = docs.first();
			
			if (doc==null || !doc.containsKey("datasets"))
				throw new ServiceException("Cannot find 'datasets' document from collection '" + metaCollectionName + "'.");
			
			OpenGridMeta meta = (new ObjectMapper()).readValue(doc.toJson(), OpenGridMeta.class);
			OpenGridDataset desc = meta.getDataset(dataSetId);
			
			if (removePrivates) {
				//nullify some private info
				desc.setDataSource(null);
			}

			if (desc == null)
				throw new ServiceException("Cannot find dataset descriptor from meta store for dataset '" + dataSetId + "'.");
			return desc;
		} finally {
			ds.closeConnection();
		}
	}


	@Override
	public List<String> getAllDatasetIds(String metaCollectionName)
			throws ServiceException, JsonParseException, JsonMappingException, IOException {
		MongoDBHelper ds = new MongoDBHelper();
		List<String> a = new ArrayList<String>();
		
		try {
			MongoDatabase db = ds.getConnection();
			
			MongoCollection<Document> c = db.getCollection(metaCollectionName);

			//we only have one doc in our meta collection
			FindIterable<Document> docs = c.find();
			Document doc = docs.first();
			
			if (doc==null || !doc.containsKey("datasets"))
				throw new ServiceException("Cannot find 'datasets' document from collection '" + metaCollectionName + "'.");
			
			OpenGridMeta meta = (new ObjectMapper()).readValue(doc.toJson(), OpenGridMeta.class);
			for (OpenGridDataset o: meta.getDatasets()) {
				a.add(o.getId());
			}			
			return a;
		} finally {
			ds.closeConnection();
		}
	}
}
