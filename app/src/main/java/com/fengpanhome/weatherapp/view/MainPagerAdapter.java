package com.fengpanhome.weatherapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainPagerAdapter extends FragmentStatePagerAdapter
{
    private static final int NUM_PAGES = 5;

    List<Fragment> internalViewList;
    public MainPagerAdapter(FragmentManager manager, List<Fragment> list)
    {
        super(manager);
        if (list != null)
            internalViewList = list;
        else
            internalViewList = new ArrayList<>();

    }

    @Override
    public Fragment getItem(int position)
    {
        return internalViewList.get(position);
    }

    @Override
    public int getCount()
    {
        return NUM_PAGES;
    }

}
