package com.fengpanhome.weatherapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainPagerAdapter extends FragmentPagerAdapter
{

    private List<Fragment> internalViewList;
    private Map<Integer, String> fragmentTags;
    private FragmentManager fragmentManager;

    public MainPagerAdapter(FragmentManager manager, List<Fragment> list)
    {
        super(manager);
        fragmentTags = new HashMap<>();
        fragmentManager = manager;
        if (list != null)
            internalViewList = list;
        else
            internalViewList = new ArrayList<>();

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
       Object obj = super.instantiateItem(container, position);
        if (obj instanceof ForecastFragment)
        {
           ForecastFragment f = (ForecastFragment) obj;
            String tag = f.getTag();
            fragmentTags.put(position, tag);
        }
        return obj;
    }

    public ForecastFragment getForecastFragment(int id)
    {
        return (ForecastFragment) fragmentManager.findFragmentById(id);
    }
    @Override
    public Fragment getItem(int position)
    {
        return internalViewList.get(position);
    }

    @Override
    public int getCount()
    {
        return internalViewList.size();
    }

    @Override
    public int getItemPosition(Object obj)
    {
        return PagerAdapter.POSITION_NONE;
    }

}
