package com.fengpanhome.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String unit = null;
    private WeatherForecast fullForecast = null;

    public YahooWeather(String location, String unit)
    {
        this.location = location;
        this.unit = unit;
    }

    public WeatherForecast getFullForecast() throws IOException, JSONException
    {
        generateForecast();
        if (fullForecast == null)
            return null;
        return fullForecast;
    }

    private void generateForecast() throws IOException, JSONException {
        final String baseUrl = "https://query.yahooapis.com/v1/public/yql?q=";
        String queryStr = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"" + location + "\") and u=\"" + unit + "\"";
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
            String forecastRaw = buffer.toString();

            JSONObject mainJson = new JSONObject(forecastRaw);
            JSONObject query = mainJson.getJSONObject("query");
            if (query.getString("count").equals("0"))
                return;
            JSONObject results = query.getJSONObject("results");
            JSONObject channel = results.getJSONObject("channel");
            JSONObject wind = channel.getJSONObject("wind");

            JSONObject units = channel.getJSONObject("units");

            String unitTemp = units.getString("temperature");
            String unitSpeed = units.getString("speed");

            String windDirection = wind.getString("direction");
            String windSpeed = wind.getString("speed");
            String windData = "Wind: " + windDirection + " at " + windSpeed + unitSpeed;

            JSONObject location = channel.getJSONObject("location");
            String city = location.getString("city") + ", " + location.getString("region") + " " + location.getString("country");
            JSONObject item = channel.getJSONObject("item");
            JSONObject condition = item.getJSONObject("condition");
            JSONObject atmosphere = channel.getJSONObject("atmosphere");
            JSONObject astronomy = channel.getJSONObject("astronomy");

            String sunrise = "  " + astronomy.getString("sunrise");
            String sunset = "  " + astronomy.getString("sunset");
            String humidity = "Humidity: " + atmosphere.getString("humidity") + "%";
            String temp = condition.getString("temp");
            String skyCondition = condition.getString("text");
            String code = condition.getString("code");

            WeatherForecastCurrent forecastCurrent = new WeatherForecastCurrent();
            forecastCurrent.setCityName(city);
            forecastCurrent.setSkyCondition(skyCondition);
            forecastCurrent.setHumidity(humidity);
            forecastCurrent.setTemperature(temp + " " + (char) 0x00b0 + unitTemp);
            forecastCurrent.setWind(windData);
            forecastCurrent.setSunriseTime(sunrise);
            forecastCurrent.setSunsetTime(sunset);
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
            else if (code.equals("26") || code.equals("27") || code.equals("28"))
                forecastCurrent.setIconDrawable(R.drawable.cloudy);
            else if (code.equals("29"))
                forecastCurrent.setIconDrawable(R.drawable.partlycloudynight);
            else if (code.equals("30") || code.equals("44"))
                forecastCurrent.setIconDrawable(R.drawable.partlycloudyday);
            else if (code.equals("31") || code.equals("33"))
                forecastCurrent.setIconDrawable(R.drawable.clearnight);
            else if (code.equals("32") || code.equals("33") || code.equals("34"))
                forecastCurrent.setIconDrawable(R.drawable.sunny);
            else if (code.equals("36"))
                forecastCurrent.setIconDrawable(R.drawable.hot);
            else if (code.equals("37") || code.equals("38") || code.equals("39") || code.equals("45")
                    || code.equals("47"))
                forecastCurrent.setIconDrawable(R.drawable.storm);
            else if (code.equals("41") || code.equals("42") || code.equals("43"))
                forecastCurrent.setIconDrawable(R.drawable.snowstorm);

            JSONArray forecast = item.getJSONArray("forecast");
            ArrayList<WeatherForecastFiveDay> forecastFiveDayList = new ArrayList<>();
            for (int i = 0; i < 5; i++)
            {
                WeatherForecastFiveDay forecastFiveDay = new WeatherForecastFiveDay();
                JSONObject forecastDay = forecast.getJSONObject(i);
                String codeSingleDay = forecastDay.getString("code");
                forecastFiveDay.setDayOfWeek(forecastDay.getString("day").toUpperCase());
                forecastFiveDay.setTempMax(forecastDay.getString("high") + (char) 0x00B0);
                forecastFiveDay.setTempMin(forecastDay.getString("low") + (char) 0x00B0);

                if (codeSingleDay.equals("0") || codeSingleDay.equals("1") || codeSingleDay.equals("2"))
                    forecastFiveDay.setIconDrawable(R.drawable.tornado);
                else if (codeSingleDay.equals("3") || codeSingleDay.equals("4"))

                    forecastFiveDay.setIconDrawable(R.drawable.storm);
                else if (codeSingleDay.equals("5") || codeSingleDay.equals("6") || codeSingleDay.equals("7") || codeSingleDay.equals("8") ||
                        codeSingleDay.equals("9") || codeSingleDay.equals("10") || codeSingleDay.equals("18") || codeSingleDay.equals("35"))

                    forecastFiveDay.setIconDrawable(R.drawable.sleet);
                else if (codeSingleDay.equals("11") || codeSingleDay.equals("12") || codeSingleDay.equals("40"))

                    forecastFiveDay.setIconDrawable(R.drawable.downpour);
                else if (codeSingleDay.equals("13"))

                    forecastFiveDay.setIconDrawable(R.drawable.lightsnow);
                else if (codeSingleDay.equals("14") || codeSingleDay.equals("15") || codeSingleDay.equals("16") || codeSingleDay.equals("46"))

                    forecastFiveDay.setIconDrawable(R.drawable.snow);
                else if (codeSingleDay.equals("17"))

                    forecastFiveDay.setIconDrawable(R.drawable.hail);
                else if (codeSingleDay.equals("20") || codeSingleDay.equals("21") || codeSingleDay.equals("22"))

                    forecastFiveDay.setIconDrawable(R.drawable.fogday);
                else if (codeSingleDay.equals("23") || codeSingleDay.equals("24"))

                    forecastFiveDay.setIconDrawable(R.drawable.windy);
                else if (codeSingleDay.equals("25"))

                    forecastFiveDay.setIconDrawable(R.drawable.cold);
                else if (codeSingleDay.equals("26") || codeSingleDay.equals("27") || codeSingleDay.equals("28"))

                    forecastFiveDay.setIconDrawable(R.drawable.cloudy);
                else if (codeSingleDay.equals("29"))

                    forecastFiveDay.setIconDrawable(R.drawable.partlycloudynight);
                else if(codeSingleDay.equals("30") || codeSingleDay.equals("44"))

                    forecastFiveDay.setIconDrawable(R.drawable.partlycloudyday);
                else if (codeSingleDay.equals("31") || codeSingleDay.equals("33"))

                    forecastFiveDay.setIconDrawable(R.drawable.clearnight);
                else if (codeSingleDay.equals("32") || codeSingleDay.equals("33") || codeSingleDay.equals("34"))

                    forecastFiveDay.setIconDrawable(R.drawable.sunny);
                else if (codeSingleDay.equals("36"))

                    forecastFiveDay.setIconDrawable(R.drawable.hot);
                else if (codeSingleDay.equals("37") || codeSingleDay.equals("38") || codeSingleDay.equals("39") || codeSingleDay.equals("45")
                        || codeSingleDay.equals("47"))

                    forecastFiveDay.setIconDrawable(R.drawable.storm);
                else if (codeSingleDay.equals("41") || codeSingleDay.equals("42") || codeSingleDay.equals("43"))

                    forecastFiveDay.setIconDrawable(R.drawable.snowstorm);
                forecastFiveDayList.add(forecastFiveDay);
            }
            fullForecast = new WeatherForecast(
                    forecastCurrent, forecastFiveDayList);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
