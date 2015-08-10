package com.fengpanhome.weatherapp.view;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fengpanhome.weatherapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForecastActivity extends FragmentActivity implements
        View.OnClickListener, AddNewCityDialogFragment.AddNewCityDialogListener, ViewPager.OnTouchListener
{

    private ArrayList<Fragment> fragmentList;
    private MainPagerAdapter mainPagerAdapter;
    private Map<Integer, String> fragmentTags;
    private ViewPager mainPager;
    private ImageButton addButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        fragmentTags = new HashMap<>();

        addButton = (ImageButton)findViewById(R.id.add_btn);
        //addButton.setVisibility(View.INVISIBLE);
        addButton.setOnClickListener(this);
        ImageButton removeButton = (ImageButton) findViewById(R.id.remove_btn);
        //removeButton.setVisibility(View.INVISIBLE);
        removeButton.setOnClickListener(this);

        Bundle args = getIntent().getExtras();
        String location = args.getString("LOCATION");
        String unit = args.getString("UNIT");

        mainPager = (ViewPager)findViewById(R.id.main_pager);

        ForecastFragment forecastFragment = ForecastFragment.newInstance(location, unit);

        if (fragmentList == null)
            fragmentList = new ArrayList<>();
        fragmentList.add(forecastFragment);
        fragmentTags.put(fragmentList.size()-1, forecastFragment.getTag());
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainPager.setAdapter(mainPagerAdapter);

    }

    private void showDialog()
    {
        FragmentManager fm = getSupportFragmentManager();
        AddNewCityDialogFragment addNewCityDialogFragment = new AddNewCityDialogFragment();
        addNewCityDialogFragment.show(fm, "new_location_dialog");

    }

    @Override
    public void onFinishEditDialog(String location, String unit)
    {
        ForecastFragment forecastFragment = ForecastFragment.newInstance(location, unit);
        if (fragmentList == null)
            fragmentList = new ArrayList<>();
        fragmentList.add(forecastFragment);
        fragmentTags.put(fragmentList.size() - 1, forecastFragment.getTag());
        mainPager.removeAllViews();
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainPagerAdapter.notifyDataSetChanged();
        mainPager.setAdapter(mainPagerAdapter);
        mainPager.setCurrentItem(fragmentList.indexOf(forecastFragment));
    }

    private void removePage()
    {
        int id = mainPager.getCurrentItem();
        ForecastFragment fragmentToRemove = mainPagerAdapter.getForecastFragment(id);
        fragmentList.remove(fragmentToRemove);
        mainPager.removeAllViews();
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainPagerAdapter.notifyDataSetChanged();
        mainPager.setAdapter(mainPagerAdapter);
        mainPager.setCurrentItem(fragmentList.size() - 1);
        Toast.makeText(this, "City removed", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.add_btn:
            {
                showDialog();
                break;
            }
            case R.id.remove_btn:
            {
                removePage();
                break;
            }
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            addButton.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Touched!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            addButton.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Not Touched!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
