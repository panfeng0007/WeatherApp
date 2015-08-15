package com.fengpanhome.weatherapp.view;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fengpanhome.weatherapp.R;

import java.util.ArrayList;

public class ForecastActivity extends FragmentActivity implements
        View.OnClickListener, AddNewCityDialogFragment.AddNewCityDialogListener, ViewPager.OnTouchListener
{

    private ArrayList<ForecastFragment> fragmentList;
    private MainPagerAdapter mainPagerAdapter;
    private ViewPager mainPager;
    private ImageButton addButton;
    private DetailOnPageChangedListener listener;
    public class DetailOnPageChangedListener extends ViewPager.SimpleOnPageChangeListener
    {
        private int currentPage;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            currentPage = position;
        }

        @Override
        public void onPageSelected(int position)
        {
            currentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }

        public final int getCurrentPage()
        {
            return currentPage;
        }

        public void setCurrentPage(int pos)
        {
            currentPage = pos;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        listener = new DetailOnPageChangedListener();

        Bundle args = getIntent().getExtras();
        String location = args.getString("LOCATION");
        String unit = args.getString("UNIT");

        mainPager = (ViewPager)findViewById(R.id.main_pager);
        ForecastFragment forecastFragment = ForecastFragment.newInstance(location, unit);
        DetailOnPageChangedListener listener = new DetailOnPageChangedListener();

        mainPager.addOnPageChangeListener(listener);
        fragmentList = new ArrayList<>();
        fragmentList.add(forecastFragment);
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainPager.setAdapter(mainPagerAdapter);

        addButton = (ImageButton)findViewById(R.id.add_btn);
        addButton.setOnClickListener(this);
        ImageButton removeButton = (ImageButton) findViewById(R.id.remove_btn);
        removeButton.setOnClickListener(this);
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
        addPage(location, unit);
    }

    private void addPage(String location, String unit)
    {
        mainPager.removeAllViews();
        mainPager.addOnPageChangeListener(listener);
        ForecastFragment forecastFragment = ForecastFragment.newInstance(location, unit);
        if (fragmentList == null)
            fragmentList = new ArrayList<>();
        fragmentList.add(forecastFragment);
        mainPagerAdapter.notifyDataSetChanged();
        mainPager.setCurrentItem(fragmentList.size()-1);
    }

    private void removePage()
    {
        mainPager.addOnPageChangeListener(listener);
        if (fragmentList != null)
            fragmentList.remove(listener.getCurrentPage());
        mainPagerAdapter.notifyDataSetChanged();
        if (fragmentList.size() == 0)
            this.finish();
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

    @Override
    public void onDestroy()
    {
        mainPager.removeAllViews();
        mainPager.setAdapter(null);
        mainPager.removeOnPageChangeListener(listener);
        fragmentList.clear();
        super.onDestroy();
    }
}
