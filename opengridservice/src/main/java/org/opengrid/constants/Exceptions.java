package org.opengrid.constants;

public final class Exceptions {
	//error codes - must be in kept sync with exceptions.properties
	
	//general service error
	static public final String ERR_SERVICE = "0100";
	
	//general system error (outside of service)
	static public final String ERR_SYSTEM = "0101";	
	
	//DB-related errors
	static public final String ERR_DB = "0200";
	
	//client/request related errors
	static public final String ERR_CLIENT = "0300";
	
	
	//add more here, as needed
	
	static public final String ERR_MESSAGE_DELIM = ";";
	
	
	
	static public final String RESOURCE_BUNDLE = "config/exceptionlist";
	
	private Exceptions(){
	}
}
