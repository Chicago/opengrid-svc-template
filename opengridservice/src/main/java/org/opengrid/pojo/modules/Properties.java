package org.opengrid.pojo.modules;

public class Properties
{
    private String text;

    private String bio;

    private String _id;

    private String screenName;

    private String hashtags;

    private String lng;

    private String date;

    private String lat;

    private String city;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getBio ()
    {
        return bio;
    }

    public void setBio (String bio)
    {
        this.bio = bio;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getScreenName ()
    {
        return screenName;
    }

    public void setScreenName (String screenName)
    {
        this.screenName = screenName;
    }

    public String getHashtags ()
    {
        return hashtags;
    }

    public void setHashtags (String hashtags)
    {
        this.hashtags = hashtags;
    }

    public String getlng ()
    {
        return lng;
    }

    public void setlng (String lng)
    {
        this.lng = lng;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [text = "+text+", bio = "+bio+", _id = "+_id+", screenName = "+screenName+", hashtags = "+hashtags+", lng = "+lng+", date = "+date+", lat = "+lat+", city = "+city+"]";
    }
}
			
	