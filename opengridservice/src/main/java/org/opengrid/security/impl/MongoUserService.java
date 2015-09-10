package org.opengrid.security.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.opengrid.constants.Exceptions;
import org.opengrid.data.MongoDBHelper;
import org.opengrid.security.OpenGridUserRole;
import org.opengrid.util.ExceptionUtil;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoUserService implements org.springframework.security.core.userdetails.UserDetailsService {
	private static final Logger log = Logger.getLogger(MongoUserService.class);
	 
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    private List<DBObject> allGroups = null;
 
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
 			DB db = ds.getConnection();
 			DBCollection c = db.getCollection(org.opengrid.constants.DB.USERS_COLLECTION_NAME);
 			
 			BasicDBObject q = new BasicDBObject();
 			q = new BasicDBObject("userId", new BasicDBObject("$eq", username));

 			DBObject doc = c.findOne(q);
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
 		    	getRoles(u, (BasicDBList) doc.get("groups"), db);
 		    	
 		    	//read subscriptions later
 		    	return u;			
 			}			
 		} finally {
			if (ds !=null) {
				ds.closeConnection();
			}
		}		
	}
    
    
    private void getRoles(User u, BasicDBList roles, DB db) {
    	Map<String, String> res = new HashMap<String, String>(); 
    	for(Object o : roles) {
    		u.grantRole(new OpenGridUserRole( org.opengrid.constants.Security.AUTH_PREFIX_GROUP + (String) o));
		  		  
    		//we need to get all resources accessible to this group
    		BasicDBList a = getGroupResources(db, (String) o);
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
		
		for (DBObject o: allGroups) {
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

	
	private List<DBObject> loadGroups() {
		List<DBObject> l = new ArrayList<DBObject>();
	
		MongoDBHelper ds = new MongoDBHelper();
		DB db = ds.getConnection();
		
		try {
			DBCollection c = db.getCollection(org.opengrid.constants.DB.GROUPS_COLLECTION_NAME);
			DBCursor cur = c.find();
	        while(cur.hasNext()) {
	        	l.add( (DBObject)cur.next());			
	        }
		} finally {
			if (ds !=null) {
				ds.closeConnection();
			}
		}
		return l;
	}
	

	private BasicDBList getGroupResources(DB db, String groupId) {
		DBCollection c = db.getCollection(org.opengrid.constants.DB.GROUPS_COLLECTION_NAME);
		BasicDBObject q = new BasicDBObject();
		q = new BasicDBObject("groupId", new BasicDBObject("$eq", groupId));

		DBObject doc = c.findOne(q);
		return (BasicDBList) doc.get("datasets");
	}
	

	public void addUser(User user) {
		//not implemented
    }
}