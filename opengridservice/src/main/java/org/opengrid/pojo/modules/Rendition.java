package org.opengrid.pojo.modules;

public class Rendition
{
    private String icon;

    private String fillColor;

    private String color;

    private String opacity;

    private String size;

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getFillColor ()
    {
        return fillColor;
    }

    public void setFillColor (String fillColor)
    {
        this.fillColor = fillColor;
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
    }

    public String getOpacity ()
    {
        return opacity;
    }

    public void setOpacity (String opacity)
    {
        this.opacity = opacity;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [icon = "+icon+", fillColor = "+fillColor+", color = "+color+", opacity = "+opacity+", size = "+size+"]";
    }
}
			
			