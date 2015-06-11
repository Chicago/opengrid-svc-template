package org.opengrid.pojo;

import org.opengrid.pojo.modules.*;

public class Twitter_GeoQuery
{
    private Features[] features;

    private String type;

    private Meta meta;

    public Features[] getFeatures ()
    {
        return features;
    }

    public void setFeatures (Features[] features)
    {
        this.features = features;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public Meta getMeta ()
    {
        return meta;
    }

    public void setMeta (Meta meta)
    {
        this.meta = meta;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [features = "+features+", type = "+type+", meta = "+meta+"]";
    }
}
			
	