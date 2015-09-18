package org.opengrid.security.impl;

import java.util.ArrayList;

import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.opengrid.constants.Exceptions;
import org.opengrid.data.MongoDBHelper;
import org.opengrid.security.RoleAccessValidator;
import org.opengrid.security.TokenHandler;
import org.opengrid.util.ExceptionUtil;
import org.opengrid.util.ServiceProperties;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class JwtRoleAccessValidator implements RoleAccessValidator {
	private static final Logger log = Logger.getLogger(JwtRoleAccessValidator.class);
	private ArrayList resourceSecurity = null;
	
	private ArrayList loadResourceSecurity() {
		MongoDBHelper ds = new MongoDBHelper();
		MongoDatabase db = ds.getConnection();
		
		try {
			MongoCollection<Document> c = db.getCollection(org.opengrid.constants.DB.META_COLLECTION_NAME);

			//we only have one doc in our meta collection
			FindIterable<Document> docs = c.find();
			Document doc = docs.first();
			ArrayList a = (ArrayList) doc.get("resourceSecurity");
			if (a == null) {
				throw ExceptionUtil.getException(Exceptions.ERR_SERVICE, "Cannot find resourceSecurity map.");
			}
			return a;			
		} finally {
			if (ds !=null) {
				ds.closeConnection();
			}
		}
	}
	

	@Override
	//generates an exception if user does not have access to the given resource
	public boolean hasAccess(String token, HttpServletRequest request) {		
		//resource | method | roles
		String allowedList = getAllowedResources(token);
		//String resourceName = extractResourceName(request);
		
		return (lookupAccessMap(request.getPathInfo(), request.getMethod(), allowedList) );
	}
	

	public boolean lookupAccessMap(String resourcePath, String method, String allowedList) {
		//sample map entry
		//{ "resourcePath": "groups", "httpMethod": "GET", "requiredAuthorities": "?" }
		
		if (resourceSecurity == null) {
			//lazy-load access matrix and additional info from Mongo
			resourceSecurity = loadResourceSecurity();			
		}
		
		//find the resource from our map
		for (Object o: resourceSecurity) {
			String urlPattern = ((Document) o).get("urlPattern").toString(); 			
			if ( resourcePath.matches(urlPattern) &&
					matchesMethod( ((Document) o).get("httpMethod").toString(), method)
				) {
				log.debug("urlPattern: " + urlPattern +", resourcePath=" + resourcePath + ", method: " + method);
				//found our map
				String requiredAuth = ((Document) o).get("requiredAuthorities").toString();
				return hasAllRequiredAuth(requiredAuth, allowedList);
			}
		}
		//if no map is found, default is to allow access
		return true;
	}
	
	private boolean hasAllRequiredAuth(String requiredAuth, String allowedList) {
		//* => any auth is allowed
		if (requiredAuth.equals("?"))
			return true;
		String req[] = requiredAuth.split(",");
		String allowed[] = allowedList.split(",");
		for (String s: req) {
			if (!findValue(s, allowed)) {
				return false;
			}
		}
		return true;
	}


	private boolean findValue(String s, String[] allowed) {
		for (String a: allowed) {
			if (a.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}


	private boolean matchesMethod(String value1, String value2) {
		//* => matches all
		if (value1.equals("*")) {
			return true;
		}
		return (value1.equalsIgnoreCase(value2));
	}

	
	private String extractResourceName(HttpServletRequest request) {
    	String[] a = request.getPathInfo().split("/");
    	
    	//just get last resource from path
    	if (a == null)
    		throw ExceptionUtil.getException(Exceptions.ERR_SYSTEM, "Resource name cannot be determined from request.");
    	return a[a.length - 1];
	}
	

	private String getAllowedResources(String token) {
		TokenHandler tokenHandler = new TokenHandler();
		tokenHandler.setSecret(ServiceProperties.getProperties().getStringProperty("auth.key"));
		Claims c = tokenHandler.getClaims(token);
		return ( (String )c.get("resources") );
	}	
}
