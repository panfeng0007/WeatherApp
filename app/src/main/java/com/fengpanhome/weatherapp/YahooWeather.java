package com.fengpanhome.weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class YahooWeather
{
    public String getForecast(String location) throws IOException
    {
        final String baseUrl = "https://query.yahooapis.com/v1/public/yql?q=";
        String query = "select * from weather.forecast where woeid in (select woeid from geo.places where text=\"" + location + "\")";
        String fullUrlString = baseUrl + URLEncoder.encode(query, "UTF-8") + "&format=json";
        URL fullUrl = new URL(fullUrlString);

        StringBuilder buffer = new StringBuilder("");
        try
        {
            HttpURLConnection conn = (HttpURLConnection)fullUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "");
            conn.setDoInput(true);
            conn.connect();

            InputStream in = conn.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = r.readLine()) != null)
            {
                buffer.append(line);
            }
            return buffer.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
