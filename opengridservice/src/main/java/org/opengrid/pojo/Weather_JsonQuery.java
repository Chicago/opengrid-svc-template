package org.opengrid.pojo;


                 
public class Weather_JsonQuery
{
    private String icon;

    private String condition;

    private String forecast;

    private String zipcode;

    private String lng;

    private String type;

    private String date;

    private String precipIntensity;

    private String windspeed;

    private String humidity;

    private String _id;

    private String temp;

    private String lat;

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getCondition ()
    {
        return condition;
    }

    public void setCondition (String condition)
    {
        this.condition = condition;
    }

    public String getForecast ()
    {
        return forecast;
    }

    public void setForecast (String forecast)
    {
        this.forecast = forecast;
    }

    public String getZipcode ()
    {
        return zipcode;
    }

    public void setZipcode (String zipcode)
    {
        this.zipcode = zipcode;
    }

    public String getLong ()
    {
        return lng;
    }

    public void setLong (String lng)
    {
        this.lng = lng;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getPrecipIntensity ()
    {
        return precipIntensity;
    }

    public void setPrecipIntensity (String precipIntensity)
    {
        this.precipIntensity = precipIntensity;
    }

    public String getWindspeed ()
    {
        return windspeed;
    }

    public void setWindspeed (String windspeed)
    {
        this.windspeed = windspeed;
    }

    public String getHumidity ()
    {
        return humidity;
    }

    public void setHumidity (String humidity)
    {
        this.humidity = humidity;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getTemp ()
    {
        return temp;
    }

    public void setTemp (String temp)
    {
        this.temp = temp;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [icon = "+icon+", condition = "+condition+", forecast = "+forecast+", zipcode = "+zipcode+", long = "+lng+", type = "+type+", date = "+date+", precipIntensity = "+precipIntensity+", windspeed = "+windspeed+", humidity = "+humidity+", _id = "+_id+", temp = "+temp+", lat = "+lat+"]";
    }
}
			
			