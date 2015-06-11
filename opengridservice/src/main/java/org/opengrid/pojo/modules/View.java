package org.opengrid.pojo.modules;

public class View
{
    private String id;

    private Columns[] columns;

    private String displayName;

    private Options options;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Columns[] getColumns ()
    {
        return columns;
    }

    public void setColumns (Columns[] columns)
    {
        this.columns = columns;
    }

    public String getDisplayName ()
    {
        return displayName;
    }

    public void setDisplayName (String displayName)
    {
        this.displayName = displayName;
    }

    public Options getOptions ()
    {
        return options;
    }

    public void setOptions (Options options)
    {
        this.options = options;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", columns = "+columns+", displayName = "+displayName+", options = "+options+"]";
    }
}
			
			
