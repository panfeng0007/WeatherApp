package com.fengpanhome.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import java.io.IOException;


public class MainActivity extends Activity implements OnClickListener
{
    private YahooWeather mYahooWeather;
    public final static String EXTRA_MESSAGE = "com.fengpanhome.weatherapp.MESSAGE";

    public MainActivity()
    {
        mYahooWeather = new YahooWeather();
    }

    private class AsyncOps extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                return mYahooWeather.getForecast(params[0]);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            Intent intent = new Intent(MainActivity.this, DebugActivity.class);
            intent.putExtra(EXTRA_MESSAGE, result);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton searchBtn = (ImageButton) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);
        Button debugBtn = (Button) findViewById(R.id.debug_btn);
        debugBtn.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.debug_btn:
            {
                AutoCompleteTextView input = (AutoCompleteTextView)findViewById(R.id.txtinput);
                new AsyncOps().execute(input.getText().toString());
                break;
            }
            case R.id.search_btn:
            {
                Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}
