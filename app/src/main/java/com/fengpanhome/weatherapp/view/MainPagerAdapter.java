package com.fengpanhome.weatherapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;


public class MainPagerAdapter extends FragmentStatePagerAdapter
{

    private ArrayList<ForecastFragment> fragments;
    public MainPagerAdapter(FragmentManager manager, ArrayList<ForecastFragment> ilist)
    {
        super(manager);
        fragments = ilist;
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return fragments.size();
    }


    @Override
    public int getItemPosition(Object obj)
    {
        return FragmentStatePagerAdapter.POSITION_NONE;
    }
}
