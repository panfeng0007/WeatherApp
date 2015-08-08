package com.fengpanhome.weatherapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.fengpanhome.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class ForecastActivity extends FragmentActivity
{
    private String location;
    private String unit;
    private MainPagerAdapter adapter;
    private List<Fragment> fragmentList;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Bundle args = getIntent().getExtras();
        location = args.getString("LOCATION");
        unit = args.getString("UNIT");

        ViewPager pager = (ViewPager)findViewById(R.id.view_pager);

        ForecastFragment forecastFragment1 = ForecastFragment.newInstance(location, unit);
        fragmentList = new ArrayList<>();
        fragmentList.add(forecastFragment1);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);
        pager.setAdapter(adapter);

    }
}
