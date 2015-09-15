package com.fengpanhome.weatherapp.model;

import java.util.ArrayList;

public class WeatherForecast
{
    private WeatherForecastCurrent weatherForecastCurrent;
    private ArrayList<WeatherForecastFiveDay> weatherForecastFiveDayArrayList;
    private AviationInfo aviationInfo;

    public AviationInfo getAviationInfo()
    {
        return aviationInfo;
    }

    public void setAviationInfo(AviationInfo aviationInfo)
    {
        this.aviationInfo = aviationInfo;
    }


    public WeatherForecast(WeatherForecastCurrent current,
                           ArrayList<WeatherForecastFiveDay> fiveday)
    {
        this.weatherForecastCurrent = current;
        this.weatherForecastFiveDayArrayList = fiveday;
    }

    public WeatherForecast(WeatherForecastCurrent current,
                           ArrayList<WeatherForecastFiveDay> fiveDays,
                           AviationInfo aviationInfo)
    {
        this.weatherForecastCurrent = current;
        this.weatherForecastFiveDayArrayList = fiveDays;
        this.aviationInfo = aviationInfo;
    }

    public WeatherForecastCurrent getWeatherForecastCurrent() {
        return weatherForecastCurrent;
    }

    public ArrayList<WeatherForecastFiveDay> getWeatherForecastFiveDayArrayList() {
        return weatherForecastFiveDayArrayList;
    }
}
