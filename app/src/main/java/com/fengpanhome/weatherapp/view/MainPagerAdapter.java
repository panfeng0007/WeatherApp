package com.fengpanhome.weatherapp.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

public class MainPagerAdapter extends FragmentStatePagerAdapter
{

    private List<Fragment> screens;
    public MainPagerAdapter(FragmentManager manager, List<Fragment> screens)
    {
        super(manager);
        this.screens = screens;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position)
    {
        return screens.get(position);
    }

    @Override
    public int getCount()
    {
        return screens.size();
    }

    @Override
    public int getItemPosition(Object obj)
    {
        return PagerAdapter.POSITION_NONE;
    }

}
