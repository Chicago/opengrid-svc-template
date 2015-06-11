package org.opengrid.pojo.modules;

public class Features
{
    private Properties properties;

    private String type;

    private Geometry geometry;

    public Properties getProperties ()
    {
        return properties;
    }

    public void setProperties (Properties properties)
    {
        this.properties = properties;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public Geometry getGeometry ()
    {
        return geometry;
    }

    public void setGeometry (Geometry geometry)
    {
        this.geometry = geometry;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [properties = "+properties+", type = "+type+", geometry = "+geometry+"]";
    }
}
			
		