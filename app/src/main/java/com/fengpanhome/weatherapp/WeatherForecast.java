package com.fengpanhome.weatherapp;

import java.util.ArrayList;

/**
 * Created by Feng on 7/29/2015.
 */
public class WeatherForecast
{
    private WeatherForecastCurrent weatherForecastCurrent;
    private ArrayList<WeatherForecastFiveDay> weatherForecastFiveDayArrayList;

    public WeatherForecast()
    {
        weatherForecastCurrent = new WeatherForecastCurrent();
        weatherForecastFiveDayArrayList = new ArrayList<>();
    }
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
