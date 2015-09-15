package com.fengpanhome.weatherapp.controller;

import com.fengpanhome.weatherapp.model.AviationInfo;

import java.io.IOException;
import java.net.URLEncoder;

public class AddsInquery
{
    private AviationInfo aviationInfo;
    private String airportCode;

    public AddsInquery(String airportCode)
    {
        this.airportCode = airportCode;
    }

    private void generateAviationWeatherInfo() throws IOException
    {

    }

}
