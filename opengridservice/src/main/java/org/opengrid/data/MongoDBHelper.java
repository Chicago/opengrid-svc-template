package org.opengrid.data;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.opengrid.util.PropertiesManager;
import org.opengrid.util.ServiceProperties;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDBHelper {
	protected PropertiesManager properties = ServiceProperties.getProperties();
	private DB db = null;
	private MongoClient m = null;

	private String dbHostName;
	private String dbInstanceName;
	private int dbPort=0;
	private String dbUser;
	private String dbPassword;
	
	public MongoDBHelper(String dbHostName, String dbInstanceName, int dbPort, String dbUser, String dbPassword) {
		this.dbHostName = dbHostName;
		this.dbInstanceName = dbInstanceName;
		this.dbPort = dbPort;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}
	
	
	public MongoDBHelper() {
		
	}
	
	public DB getConnection() {
		if (db == null) {
			if (dbPort == 0)
				dbPort = Integer.parseInt(properties.getStringProperty("mongo.port"));
			if (dbHostName == null)
				dbHostName = properties.getStringProperty("mongo.hostname");
			try {
				ServerAddress sa = new ServerAddress(dbHostName, dbPort);
				
				if (dbUser == null)
					dbUser = properties.getStringProperty("mongo.user");
				if (dbPassword == null)
					dbPassword = properties.getStringProperty("mongo.password");
				if (dbInstanceName == null)
					dbInstanceName = properties.getStringProperty("mongo.database");
					
				MongoCredential credential = MongoCredential.createMongoCRCredential(dbUser, dbInstanceName, dbPassword.toCharArray());
				m = new MongoClient(sa, Arrays.asList(credential));				
				db = m.getDB(dbInstanceName);
			} catch (UnknownHostException e) {
				System.out.println(String.format("MongoDBHelper: cannot connect to Mongo @ %s - %s", dbHostName, dbPort));
				return null;
			}
		}
		return db;
	}

	public void closeConnection() {
		if (db != null && m != null) m.close();
	}
	
	public static String makeId(String id) {
		return "{ \"$oid\": \"" + id + "\"}";
	}
	
	
}
