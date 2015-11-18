package com.fengpanhome.weatherapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.fengpanhome.weatherapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
        initializeView();
        try
        {
            progressBar.setVisibility(View.VISIBLE);
            final File dir = getFilesDir();
            final File file = new File(dir, "cities.weather");

            if (file.exists())
            {
                FileInputStream inputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Gson gson = new Gson();

                BufferedReader reader = new BufferedReader(inputStreamReader);
                ArrayList<String> locations;
                ArrayList<String> units;

                Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                String outputStr = "";

                String line = "";
                while (line != null)
                {
                    outputStr += line;
                    line = reader.readLine();
                }
                reader.close();
                inputStreamReader.close();
                inputStream.close();

                int sIndex = 0;
                int eIndex = 0;
                int i = 0;
                for (char c : outputStr.toCharArray())
                {
                    if (c == '{')
                        sIndex = i;
                    if (c == '}')
                        eIndex = i;
                    i++;
                }

                String jsonStr = outputStr.substring(sIndex, eIndex + 1);

                JsonObject mapping = gson.fromJson(jsonStr, JsonObject.class);
                locations = gson.fromJson(mapping.get("locations"), listType);
                units = gson.fromJson(mapping.get("units"), listType);

                if (locations == null || units == null || locations.size() == 0 || units.size() == 0)
                {
                    Intent intent = new Intent(this, SearchActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                else
                {
                    Intent intent = new Intent(this, ForecastActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ACTIVITY", "MainActivity");
                    bundle.putStringArrayList("LOCATIONS", locations);
                    bundle.putStringArrayList("UNITS", units);

                    intent.putExtras(bundle);
                    startActivity(intent);
                    this.finish();
                }
            }
            else
            {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                this.finish();
            }
            progressBar.setVisibility(View.GONE);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}
