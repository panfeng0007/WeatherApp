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

import org.json.JSONException;

import java.io.IOException;


public class MainActivity extends Activity implements OnClickListener
{
    public final static String EXTRA_MESSAGE = "com.fengpanhome.weatherapp.MESSAGE";

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
                AutoCompleteTextView input = (AutoCompleteTextView) findViewById(R.id.txtinput);
                Intent intent = new Intent(this, ForecastActivity.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, input.getText().toString());
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}
