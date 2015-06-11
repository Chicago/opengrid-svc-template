package org.opengrid.pojo.modules;



public class Options
{
    private Rendition rendition;

    public Rendition getRendition ()
    {
        return rendition;
    }

    public void setRendition (Rendition rendition)
    {
        this.rendition = rendition;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rendition = "+rendition+"]";
    }
}
