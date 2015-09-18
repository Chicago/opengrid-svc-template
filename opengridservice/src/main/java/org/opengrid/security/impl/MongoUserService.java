package org.opengrid.security.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.opengrid.data.MongoDBHelper;
import org.opengrid.security.OpenGridUserRole;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoUserService implements org.springframework.security.core.userdetails.UserDetailsService {
	private static final Logger log = Logger.getLogger(MongoUserService.class);
	 
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    private List<Document> allGroups = null;
 
    @Override
    public final User loadUserByUsername(String username) throws UsernameNotFoundException {
    	final User user = getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        detailsChecker.check(user);
        return user;
    }
 
    private User getUser(String username) {
    	MongoDBHelper ds = new MongoDBHelper();
		
 		try {
 			MongoDatabase db = ds.getConnection();
 			MongoCollection<Document> c = db.getCollection(org.opengrid.constants.DB.USERS_COLLECTION_NAME);
 			
 			BasicDBObject q = new BasicDBObject();
 			q = new BasicDBObject("userId", new BasicDBObject("$eq", username));

 			FindIterable<Document> docs = c.find(q);
 			Document doc = docs.first();
 			if (doc == null) {
 				return null;
 			} else {
 				User u = new User();
 		    	u.setUsername(doc.get("userId").toString());
 		    	u.setFirstName(doc.get("firstName").toString());
 		    	u.setLastName(doc.get("lastName").toString());
 		    	
 		    	//on the LDAP impl, we won't have this
 		    	u.setPassword(doc.get("password").toString());
 		    	
 		    	//read roles/groups
 		    	//roles 
 		    	getRoles(u, (ArrayList) doc.get("groups"), db);
 		    	
 		    	//read subscriptions later
 		    	return u;			
 			}			
 		} finally {
			if (ds !=null) {
				ds.closeConnection();
			}
		}		
	}
    
    
    private void getRoles(User u, ArrayList arrayList, MongoDatabase db) {
    	Map<String, String> res = new HashMap<String, String>(); 
    	for(Object o : arrayList) {
    		u.grantRole(new OpenGridUserRole( org.opengrid.constants.Security.AUTH_PREFIX_GROUP + (String) o));
		  		  
    		//we need to get all resources accessible to this group
    		ArrayList a = getGroupResources(db, (String) o);
    		if (a !=null) {
    			for(Object s : a) {    	    		
    	    		if (!res.containsKey(s)) {
    	    			res.put((String) s, (String) s);
    	    		}
    			}
    		}
    		
    		//isAdmin is a system flag (there could be more special flags later depending on the need)
    		if (isAdminGroup((String) o)) {
    			String adminResourceString = org.opengrid.constants.Security.AUTH_PREFIX_RESOURCE + 
   					 org.opengrid.constants.Security.ADMIN_AUTH;
    			if (!res.containsKey(adminResourceString)) {
    				u.grantRole( new OpenGridUserRole(adminResourceString) );
    			}    			
    		}
    	}
    	//add resources to authorities using special prefix
    	for(String s : res.keySet()) {   
    		u.grantRole(new OpenGridUserRole( org.opengrid.constants.Security.AUTH_PREFIX_RESOURCE + s));
    	}
    	
    }
    

	private boolean isAdminGroup(String groupName) {
		//all ways re-read group info, as this may change a lot
		//if (allGroups == null) {
			allGroups = loadGroups();			
		//}
		
		for (Document o: allGroups) {
			if ( ( (String) o.get("groupId")).equals(groupName)) {
				boolean b = false;
				if (o.get("isAdmin") != null) {
					b = (Boolean) o.get("isAdmin");
				}
				return b;
			}
		}
		return false;
	}

	
	private List<Document> loadGroups() {
		List<Document> l = new ArrayList<Document>();
	
		MongoDBHelper ds = new MongoDBHelper();
		MongoDatabase db = ds.getConnection();
		
		try {
			MongoCollection<Document> c = db.getCollection(org.opengrid.constants.DB.GROUPS_COLLECTION_NAME);
			FindIterable<Document> cur = c.find();
			MongoCursor<Document> it = cur.iterator();
	        while(it.hasNext()) {
	        	l.add(it.next());			
	        }
		} finally {
			if (ds !=null) {
				ds.closeConnection();
			}
		}
		return l;
	}
	

	private ArrayList getGroupResources(MongoDatabase db, String groupId) {
		MongoCollection<Document> c = db.getCollection(org.opengrid.constants.DB.GROUPS_COLLECTION_NAME);
		BasicDBObject q = new BasicDBObject();
		q = new BasicDBObject("groupId", new BasicDBObject("$eq", groupId));

		FindIterable<Document> docs = c.find(q);
		Document doc = docs.first();
		return (ArrayList) doc.get("datasets");
	}
	

	public void addUser(User user) {
		//not implemented
    }
}