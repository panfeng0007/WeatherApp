package com.fengpanhome.weatherapp.model;

public class WeatherForecastCurrent
{
    private String cityName;
    private String skyCondition;
    private String temperature;
    private String wind;
    private String humidity;
    private String sunriseTime;
    private String sunsetTime;
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

    public String getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }
}
