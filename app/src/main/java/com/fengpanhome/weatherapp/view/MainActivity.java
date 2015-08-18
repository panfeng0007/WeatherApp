package com.fengpanhome.weatherapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.widget.ProgressBar;

import com.fengpanhome.weatherapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends Activity
{

    private ProgressBar progressBar;

    private void initializeView()
    {
        progressBar = (ProgressBar)findViewById(R.id.progress_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            ArrayList<ForecastFragment> fragmentList = new ArrayList<>();
            initializeView();
            Intent intent;
            if (new File("cities.weather").exists())
            {
                FileInputStream fileIn = new FileInputStream("cities.weather");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                while (in.readObject() != null) {
                    ForecastFragment f = (ForecastFragment) in.readObject();
                    if (f != null)
                        fragmentList.add(f);
                }
                if (fragmentList.size() == 0) {
                    intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                else
                {
                    intent = new Intent(MainActivity.this, ForecastActivity.class);
                    Bundle bundle = new Bundle();
                    ArrayList<String> locations = new ArrayList<>();
                    ArrayList<String> units = new ArrayList<>();

                    for (ForecastFragment f : fragmentList)
                    {
                        String location = f.getLocation();
                        locations.add(location);
                        String unit = f.getLocation();
                        units.add(unit);
                    }

                    bundle.putStringArrayList("locations", locations);
                    bundle.putStringArrayList("units", units);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    this.finish();
                }
            }
            else
            {
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
