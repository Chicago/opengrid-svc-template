package org.opengrid.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class PropertiesManager {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private Properties props;

    public PropertiesManager(String propertyFile) {
		props = new Properties();
        try {
            ResourceBundle rb = ResourceBundle.getBundle(propertyFile);
            Enumeration<String> keys = rb.getKeys();
            String key;
            while (keys.hasMoreElements()) {
                key = (String) keys.nextElement();
                props.setProperty(key, rb.getString(key));
            }
        } catch (Exception e) {
        	//Logger.log(PropertiesManager.class.getName(),Level.SEVERE, "Exception - ", e);
        }
    }

    public String getStringProperty(String propertyName) {
        return props.getProperty(propertyName);
    }
    
    public boolean getBooleanProperty(String propertyName) {
        return (props.getProperty(propertyName).equalsIgnoreCase("true") ? true : false);
    }

    public static String today(){
        return sdf.format(new Date(System.currentTimeMillis()));
     }

    public static String yesterday(){
  	   GregorianCalendar gc = new GregorianCalendar(); 
  	   gc.add(GregorianCalendar.DATE,-1);
  	   return sdf.format(gc.getTime());
     }
    
}
