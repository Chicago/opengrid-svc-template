package org.opengrid.pojo.modules;


public class Meta
{
private View view;

public View getView ()
{
return view;
}

public void setView (View view)
{
this.view = view;
}

@Override
public String toString()
{
return "ClassPojo [view = "+view+"]";
}
}
