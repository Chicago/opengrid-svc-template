package org.opengrid.data;

import java.net.UnknownHostException;
import java.util.Arrays;
import org.opengrid.util.PropertiesManager;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDBCollection {
	protected PropertiesManager properties = new PropertiesManager("config/application");
	private DB db = null;
	private MongoClient m = null;

	public DB getConnection() {
		if (db == null) {
			int port = Integer.parseInt(properties.getStringProperty("mongo.port"));
			String host = properties.getStringProperty("mongo.hostname");
			try {
				ServerAddress sa = new ServerAddress(host,port);
				MongoCredential credential = MongoCredential.createMongoCRCredential(properties.getStringProperty("mongo.user"), properties.getStringProperty("mongo.database"), properties.getStringProperty("mongo.password").toCharArray());
				m = new MongoClient(sa, Arrays.asList(credential));				
				db = m.getDB(properties.getStringProperty("mongo.database"));
			} catch (UnknownHostException e) {
				System.out.println(String.format("MongoDBCollection: cannot connect to Mongo @ %s - %s",properties.getStringProperty("mongo.hostname") ,port));
				return null;
			}
		}
		return db;
	}

	public void closeConnection() {
		if (db != null && m != null) m.close();
	}
	
}
