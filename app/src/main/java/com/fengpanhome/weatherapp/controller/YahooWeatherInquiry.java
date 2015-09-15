package com.fengpanhome.weatherapp.controller;

import com.fengpanhome.weatherapp.R;
import com.fengpanhome.weatherapp.model.WeatherForecast;
import com.fengpanhome.weatherapp.model.WeatherForecastCurrent;
import com.fengpanhome.weatherapp.model.WeatherForecastFiveDay;

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

public class YahooWeatherInquiry
{
    private String location = null;
    private String unit = null;
    private WeatherForecast fullForecast = null;

    public YahooWeatherInquiry(String location, String unit)
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
            conn.setReadTimeout(8000);
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

            String windData;
            String windSpeed = wind.getString("speed");
            if (!windSpeed.equals("") && !windSpeed.equals("0"))
            {
                windData = "Wind " + windSpeed + unitSpeed;
            }
            else
            {
                windData = "Wind calm";
            }

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
            switch (code) {
                case "0":
                case "1":
                case "2":
                    forecastCurrent.setIconDrawable(R.drawable.tornado);
                    break;
                case "3":
                case "4":
                    forecastCurrent.setIconDrawable(R.drawable.storm);
                    break;
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "10":
                case "18":
                case "35":
                    forecastCurrent.setIconDrawable(R.drawable.sleet);
                    break;
                case "11":
                case "12":
                case "40":
                    forecastCurrent.setIconDrawable(R.drawable.downpour);
                    break;
                case "13":
                    forecastCurrent.setIconDrawable(R.drawable.lightsnow);
                    break;
                case "14":
                case "15":
                case "16":
                case "46":
                    forecastCurrent.setIconDrawable(R.drawable.snow);
                    break;
                case "17":
                    forecastCurrent.setIconDrawable(R.drawable.hail);
                    break;
                case "20":
                case "21":
                case "22":
                    forecastCurrent.setIconDrawable(R.drawable.fogday);
                    break;
                case "23":
                case "24":
                    forecastCurrent.setIconDrawable(R.drawable.windy);
                    break;
                case "25":
                    forecastCurrent.setIconDrawable(R.drawable.cold);
                    break;
                case "26":
                case "27":
                case "28":
                    forecastCurrent.setIconDrawable(R.drawable.cloudy);
                    break;
                case "29":
                    forecastCurrent.setIconDrawable(R.drawable.partlycloudynight);
                    break;
                case "30":
                case "44":
                    forecastCurrent.setIconDrawable(R.drawable.partlycloudyday);
                    break;
                case "31":
                case "33":
                    forecastCurrent.setIconDrawable(R.drawable.clearnight);
                    break;
                case "32":
                case "34":
                    forecastCurrent.setIconDrawable(R.drawable.sunny);
                    break;
                case "36":
                    forecastCurrent.setIconDrawable(R.drawable.hot);
                    break;
                case "37":
                case "38":
                case "39":
                case "45":
                case "47":
                    forecastCurrent.setIconDrawable(R.drawable.storm);
                    break;
                case "41":
                case "42":
                case "43":
                    forecastCurrent.setIconDrawable(R.drawable.snowstorm);
                    break;
            }

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

                switch (codeSingleDay) {
                    case "0":
                    case "1":
                    case "2":
                        forecastFiveDay.setIconDrawable(R.drawable.tornado);
                        break;
                    case "3":
                    case "4":
                        forecastFiveDay.setIconDrawable(R.drawable.storm);
                        break;
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                    case "10":
                    case "18":
                    case "35":
                        forecastFiveDay.setIconDrawable(R.drawable.sleet);
                        break;
                    case "11":
                    case "12":
                    case "40":
                        forecastFiveDay.setIconDrawable(R.drawable.downpour);
                        break;
                    case "13":
                        forecastFiveDay.setIconDrawable(R.drawable.lightsnow);
                        break;
                    case "14":
                    case "15":
                    case "16":
                    case "46":
                        forecastFiveDay.setIconDrawable(R.drawable.snow);
                        break;
                    case "17":
                        forecastFiveDay.setIconDrawable(R.drawable.hail);
                        break;
                    case "20":
                    case "21":
                    case "22":
                        forecastFiveDay.setIconDrawable(R.drawable.fogday);
                        break;
                    case "23":
                    case "24":
                        forecastFiveDay.setIconDrawable(R.drawable.windy);
                        break;
                    case "25":
                        forecastFiveDay.setIconDrawable(R.drawable.cold);
                        break;
                    case "26":
                    case "27":
                    case "28":
                        forecastFiveDay.setIconDrawable(R.drawable.cloudy);
                        break;
                    case "29":
                        forecastFiveDay.setIconDrawable(R.drawable.partlycloudynight);
                        break;
                    case "30":
                    case "44":
                        forecastFiveDay.setIconDrawable(R.drawable.partlycloudyday);
                        break;
                    case "31":
                    case "33":
                        forecastFiveDay.setIconDrawable(R.drawable.clearnight);
                        break;
                    case "32":
                    case "34":
                        forecastFiveDay.setIconDrawable(R.drawable.sunny);
                        break;
                    case "36":
                        forecastFiveDay.setIconDrawable(R.drawable.hot);
                        break;
                    case "37":
                    case "38":
                    case "39":
                    case "45":
                    case "47":
                        forecastFiveDay.setIconDrawable(R.drawable.storm);
                        break;
                    case "41":
                    case "42":
                    case "43":
                        forecastFiveDay.setIconDrawable(R.drawable.snowstorm);
                        break;
                }
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
