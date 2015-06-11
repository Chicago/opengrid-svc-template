package org.opengrid.pojo.modules;

public class Columns
{
    private String id;

    private String dataType;

    private String popup;

    private String list;

    private String displayName;

    private String filter;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDataType ()
    {
        return dataType;
    }

    public void setDataType (String dataType)
    {
        this.dataType = dataType;
    }

    public String getPopup ()
    {
        return popup;
    }

    public void setPopup (String popup)
    {
        this.popup = popup;
    }

    public String getList ()
    {
        return list;
    }

    public void setList (String list)
    {
        this.list = list;
    }

    public String getDisplayName ()
    {
        return displayName;
    }

    public void setDisplayName (String displayName)
    {
        this.displayName = displayName;
    }

    public String getFilter ()
    {
        return filter;
    }

    public void setFilter (String filter)
    {
        this.filter = filter;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", dataType = "+dataType+", popup = "+popup+", list = "+list+", displayName = "+displayName+", filter = "+filter+"]";
    }
}
			
			