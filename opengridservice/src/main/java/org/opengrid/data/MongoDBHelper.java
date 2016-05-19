package org.opengrid.data;

import org.opengrid.util.PropertiesManager;
import org.opengrid.util.ServiceProperties;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoDBHelper {
	protected PropertiesManager properties = ServiceProperties.getProperties();
	//private DB db = null;
	private MongoDatabase db = null;
	private MongoClient m = null;

	private String dbHostName;
	private String dbInstanceName;
	private int dbPort=0;
	private String dbUser;
	private String dbPassword;
	private String dbConnectionString;
	
	public MongoDBHelper(String dbHostName, String dbInstanceName, int dbPort, String dbUser, String dbPassword) {
		this.dbHostName = dbHostName;
		this.dbInstanceName = dbInstanceName;
		this.dbPort = dbPort;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}
	
	public MongoDBHelper(String dbConnectionString) {
		this.dbConnectionString = dbConnectionString;
	}
	
	public MongoDBHelper() {
		
	}
	
	public MongoDatabase getConnection() {
		if (db == null) {
			//try {
			//MongoCredential credential = MongoCredential.createMongoCRCredential(dbUser, dbInstanceName, dbPassword.toCharArray());
				
				MongoClientURI uri = new MongoClientURI(getMongoConnectString());				
				m = new MongoClient(uri);
				db = m.getDatabase(uri.getDatabase());
			//} catch (Exception ) {
			//	System.out.println(String.format("MongoDBHelper: cannot connect to Mongo @ %s - %s", dbHostName, dbPort));
			//	return null;
			//}
		}
		return db;
	}

	//old way
	private String getMongoConnectStringFromParams() { 
		if (dbPort == 0)
			dbPort = Integer.parseInt(properties.getStringProperty("mongo.port"));
		if (dbHostName == null)
			dbHostName = properties.getStringProperty("mongo.hostname");
		ServerAddress sa = new ServerAddress(dbHostName, dbPort);
		
		if (dbUser == null)
			dbUser = properties.getStringProperty("mongo.user");
		if (dbPassword == null)
			dbPassword = properties.getStringProperty("mongo.password");
		if (dbInstanceName == null)
			dbInstanceName = properties.getStringProperty("mongo.database");
		return "mongodb://" + dbUser + ":" +  dbPassword + "@" + dbHostName + ":" + Integer.toString(dbPort) +  "/" + dbInstanceName +  "?authSource=" + dbInstanceName + "&authMechanism=MONGODB-CR";
	}
	
	private String getMongoConnectString() {
		if (dbConnectionString == null)
			return properties.getStringProperty("mongo.connectionString");
		return dbConnectionString;
	}


	public void closeConnection() {
		if (db != null && m != null) m.close();
	}
	
	public static String makeId(String id) {
		return "{ \"$oid\": \"" + id + "\"}";
	}
	
	
}
