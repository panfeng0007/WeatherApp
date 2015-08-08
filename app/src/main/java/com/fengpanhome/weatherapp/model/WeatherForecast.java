package com.fengpanhome.weatherapp.model;

import java.util.ArrayList;

public class WeatherForecast
{
    private WeatherForecastCurrent weatherForecastCurrent;
    private ArrayList<WeatherForecastFiveDay> weatherForecastFiveDayArrayList;
    public WeatherForecast(WeatherForecastCurrent current, ArrayList<WeatherForecastFiveDay> fiveday)
    {
        this.weatherForecastCurrent = current;
        this.weatherForecastFiveDayArrayList = fiveday;
    }

    public WeatherForecastCurrent getWeatherForecastCurrent() {
        return weatherForecastCurrent;
    }

    public void setWeatherForecastCurrent(WeatherForecastCurrent weatherForecastCurrent) {
        this.weatherForecastCurrent = weatherForecastCurrent;
    }

    public ArrayList<WeatherForecastFiveDay> getWeatherForecastFiveDayArrayList() {
        return weatherForecastFiveDayArrayList;
    }

    public void setWeatherForecastFiveDayArrayList(ArrayList<WeatherForecastFiveDay> weatherForecastFiveDayArrayList) {
        this.weatherForecastFiveDayArrayList = weatherForecastFiveDayArrayList;
    }
}
