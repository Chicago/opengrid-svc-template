package org.opengrid.util;

public class ServiceProperties {
	private static PropertiesManager properties = new PropertiesManager("config/application");
	
	public static PropertiesManager getProperties() {
		return properties;
	}
}
