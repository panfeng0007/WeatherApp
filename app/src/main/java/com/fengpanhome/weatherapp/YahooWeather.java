package com.fengpanhome.weatherapp;


import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class YahooWeather
{
    private String location = null;
    private String forecastRaw = null;
    private WeatherForecast fullForecast = null;
    private WeatherForecastCurrent forecastCurrent = null;
    private ArrayList<WeatherForecastFiveDay> forecastFiveDayList = null;

    public YahooWeather(String location)
    {
        this.location = location;
    }

    public WeatherForecast getFullForecast() throws IOException, JSONException
    {
        if (fullForecast == null)
            generateForecast();
        return fullForecast;
    }

    private void generateForecast() throws IOException, JSONException {
        final String baseUrl = "https://query.yahooapis.com/v1/public/yql?q=";
        String queryStr = "select * from weather.forecast where woeid in (select woeid from geo.places where text=\"" + location + "\")";
        String fullUrlString = baseUrl + URLEncoder.encode(queryStr, "UTF-8") + "&format=json";
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
            forecastRaw = buffer.toString();

            JSONObject query = new JSONObject(forecastRaw).getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject wind = channel.getJSONObject("wind");
            String windDirection = wind.getString("direction");
            String windSpeed = wind.getString("speed");
            String windData = "Wind: " + windDirection + " at " + windSpeed + "mph";
            JSONObject location = channel.getJSONObject("location");
            String city = location.getString("city") + ", " + location.getString("region").toUpperCase();
            JSONObject item = channel.getJSONObject("item");
            JSONObject condition = item.getJSONObject("condition");
            JSONObject atmosphere = channel.getJSONObject("atmosphere");
            String humidity = "Humidity: " + atmosphere.getString("humidity") + "%";
            String temp = condition.getString("temp");
            String skyCondition = condition.getString("text");
            String code = condition.getString("code");
            forecastCurrent = new WeatherForecastCurrent();
            forecastCurrent.setCityName(city);
            forecastCurrent.setSkyCondition(skyCondition);
            forecastCurrent.setHumidity(humidity);
            forecastCurrent.setTemperature(temp + (char) 0x00b0);
            forecastCurrent.setWind(windData);
            if (code.equals("0") || code.equals("1") || code.equals("2"))
                forecastCurrent.setIconDrawable(R.drawable.tornado);
            else if (code.equals("3") || code.equals("4"))
                forecastCurrent.setIconDrawable(R.drawable.storm);
            else if (code.equals("5") || code.equals("6") || code.equals("7") || code.equals("8") ||
                    code.equals("9") || code.equals("10") || code.equals("18") || code.equals("35"))
                forecastCurrent.setIconDrawable(R.drawable.sleet);
            else if (code.equals("11") || code.equals("12") || code.equals("40"))
                forecastCurrent.setIconDrawable(R.drawable.downpour);
            else if (code.equals("13"))
                forecastCurrent.setIconDrawable(R.drawable.lightsnow);
            else if (code.equals("14") || code.equals("15") || code.equals("16") || code.equals("46"))
                forecastCurrent.setIconDrawable(R.drawable.snow);
            else if (code.equals("17"))
                forecastCurrent.setIconDrawable(R.drawable.hail);
            else if (code.equals("20") || code.equals("21") || code.equals("22"))
                forecastCurrent.setIconDrawable(R.drawable.fogday);
            else if (code.equals("23") || code.equals("24"))
                forecastCurrent.setIconDrawable(R.drawable.windy);
            else if (code.equals("25"))
                forecastCurrent.setIconDrawable(R.drawable.cold);
            else if (code.equals("26") || code.equals("27") || code.equals("28") || code.equals("29")
                    || code.equals("30") || code.equals("44"))
                forecastCurrent.setIconDrawable(R.drawable.clouds);
            else if (code.equals("31") || code.equals("32") || code.equals("33") || code.equals("34"))
                forecastCurrent.setIconDrawable(R.drawable.sunny);
            else if (code.equals("36"))
                forecastCurrent.setIconDrawable(R.drawable.hot);
            else if (code.equals("37") || code.equals("38") || code.equals("39") || code.equals("45")
                    || code.equals("47"))
                forecastCurrent.setIconDrawable(R.drawable.storm);
            else if (code.equals("41") || code.equals("42") || code.equals("43"))
                forecastCurrent.setIconDrawable(R.drawable.snowstorm);

            JSONArray forecast = item.getJSONArray("forecast");
            forecastFiveDayList = new ArrayList<>();

            for (int i = 0; i < 5; i++)
            {
                WeatherForecastFiveDay forecastFiveDay = new WeatherForecastFiveDay();
                JSONObject forecastDay = forecast.getJSONObject(i);
                String codeDay = forecastDay.getString("code");
                forecastFiveDay.setDayOfWeek(forecastDay.getString("day").toUpperCase());
                forecastFiveDay.setTempMax(forecastDay.getString("high") + (char) 0x00B0);
                forecastFiveDay.setTempMin(forecastDay.getString("low") + (char) 0x00B0);

                if (codeDay.equals("0") || codeDay.equals("1") || codeDay.equals("2"))
                    forecastFiveDay.setIconDrawable(R.drawable.tornado);
                else if (codeDay.equals("3") || codeDay.equals("4"))
                    forecastFiveDay.setIconDrawable(R.drawable.storm);
                else if (codeDay.equals("5") || codeDay.equals("6") || codeDay.equals("7") || codeDay.equals("8") ||
                        codeDay.equals("9") || codeDay.equals("10") || codeDay.equals("18") || codeDay.equals("35"))
                    forecastFiveDay.setIconDrawable(R.drawable.sleet);
                else if (codeDay.equals("11") || codeDay.equals("12") || codeDay.equals("40"))
                    forecastFiveDay.setIconDrawable(R.drawable.downpour);
                else if (codeDay.equals("13"))
                    forecastFiveDay.setIconDrawable(R.drawable.lightsnow);
                else if (codeDay.equals("14") || codeDay.equals("15") || codeDay.equals("16") || codeDay.equals("46"))
                    forecastFiveDay.setIconDrawable(R.drawable.snow);
                else if (codeDay.equals("17"))
                    forecastFiveDay.setIconDrawable(R.drawable.hail);
                else if (codeDay.equals("20") || codeDay.equals("21") || codeDay.equals("22"))
                    forecastFiveDay.setIconDrawable(R.drawable.fogday);
                else if (codeDay.equals("23") || codeDay.equals("24"))
                    forecastFiveDay.setIconDrawable(R.drawable.windy);
                else if (codeDay.equals("25"))
                    forecastFiveDay.setIconDrawable(R.drawable.cold);
                else if (codeDay.equals("26") || codeDay.equals("27") || codeDay.equals("28") || codeDay.equals("29")
                        || codeDay.equals("30") || codeDay.equals("44"))
                    forecastFiveDay.setIconDrawable(R.drawable.clouds);
                else if (codeDay.equals("31") || codeDay.equals("32") || codeDay.equals("33") || codeDay.equals("34"))
                    forecastFiveDay.setIconDrawable(R.drawable.sunny);
                else if (codeDay.equals("36"))
                    forecastFiveDay.setIconDrawable(R.drawable.hot);
                else if (codeDay.equals("37") || codeDay.equals("38") || codeDay.equals("39") || codeDay.equals("45")
                        || codeDay.equals("47"))
                    forecastFiveDay.setIconDrawable(R.drawable.storm);
                else if (codeDay.equals("41") || codeDay.equals("42") || codeDay.equals("43"))
                    forecastFiveDay.setIconDrawable(R.drawable.snowstorm);

                forecastFiveDayList.add(forecastFiveDay);
            }

            fullForecast = new WeatherForecast(forecastCurrent, forecastFiveDayList);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
