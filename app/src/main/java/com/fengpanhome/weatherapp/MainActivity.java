package com.fengpanhome.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import java.io.IOException;


public class MainActivity extends Activity implements OnClickListener
{

    private static final String TAG = MainActivity.class.getSimpleName();
    private YahooWeather mYahooWeather = new YahooWeather();
    public final static String EXTRA_MESSAGE = "com.fengpanhome.weatherapp.MESSAGE";

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
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
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
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.search_btn:
            {
                EditText input = (EditText)findViewById(R.id.txtinput);
                new AsyncOps().execute(input.getText().toString());
                break;
            }
        }
    }
}
