package com.fengpanhome.weatherapp;

public class WeatherForecastCurrent
{
    private String cityName;
    private String skyCondition;
    private String temperature;
    private String wind;
    private String humidity;
    private int iconDrawable;

    public int getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(int iconDrawable) {
        this.iconDrawable = iconDrawable;
    }


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setSkyCondition(String skyCondition) {
        this.skyCondition = skyCondition;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getCityName() {

        return cityName;
    }

    public String getSkyCondition() {
        return skyCondition;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWind() {
        return wind;
    }

    public String getHumidity() {
        return humidity;
    }



}
