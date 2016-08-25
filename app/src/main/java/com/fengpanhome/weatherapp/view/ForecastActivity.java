package com.fengpanhome.weatherapp.view;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.fengpanhome.weatherapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ForecastActivity extends FragmentActivity implements
        View.OnClickListener, AddNewCityDialogFragment.AddNewCityDialogListener
{

    private String location;
    private String unit;
    private ArrayList<String> locations;
    private ArrayList<String> units;
    private ArrayList<ForecastFragment> fragmentList;
    private MainPagerAdapter mainPagerAdapter;
    private ViewPager mainPager;
    private DetailOnPageChangedListener pageChangeListener;
    private ArrayList<TextView> dots;
    private LinearLayout dotsLayout;
    private ImageButton addButton;
    private ImageButton removeButton;
    private boolean showButtons;

    ProgressBar progressBar;


    private class DetailOnPageChangedListener extends ViewPager.SimpleOnPageChangeListener
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
            highlightDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }

        public final int getCurrentPage()
        {
            return currentPage;
        }

    }

    private String toJsonString()
    {
        String ret = "{";
        ret += "\"locations\": [";
        int i = 0;
        for (ForecastFragment f : fragmentList)
        {
            if (i != fragmentList.size() - 1)
                ret += "\"" + f.getLocation() + "\", ";
            else
                ret += "\"" + f.getLocation() + "\"";
            i++;
        }
        i = 0;
        ret += "],";
        ret += "\"units\": [";
        for (ForecastFragment f : fragmentList)
        {
            if (f.getLocation() != null && f.getUnit() != null)
            {
                if (i != fragmentList.size() - 1)
                    ret += "\"" + f.getUnit() + "\", ";
                else
                    ret += "\"" + f.getUnit() + "\"";
                i++;
            }
        }

        ret += "] }" + "\n";
        return ret;
    }

    private void commitChangesToFile()
    {
        try
        {

            final File dir = getFilesDir();
            final File file = new File(dir, "cities.weather");

            if (fragmentList.size() == 0)
            {
                file.delete();
                return;
            }
            FileOutputStream fileOut = new FileOutputStream(file, false);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            String jsonStr = toJsonString();
            outputStream.write(jsonStr.getBytes());
            outputStream.close();
            fileOut.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private void initializeView()
    {
        pageChangeListener = new DetailOnPageChangedListener();


        Bundle args = getIntent().getExtras();
        String previousActivity = args.getString("ACTIVITY");
        if (previousActivity != null)
        {
            if (previousActivity.equals("SearchActivity"))
            {
                location = args.getString("LOCATION");
                unit = args.getString("UNIT");
                locations = null;
                units = null;
            }
            else if (previousActivity.equals("MainActivity"))
            {
                location = null;
                unit = null;
                locations = args.getStringArrayList("LOCATIONS");
                units = args.getStringArrayList("UNITS");
                if (locations == null)
                    locations = new ArrayList<>();
                if (units == null)
                    units = new ArrayList<>();
            }

        }

        progressBar = (ProgressBar) findViewById(R.id.progress);
        mainPager = (ViewPager)findViewById(R.id.main_pager);
        progressBar.setVisibility(View.VISIBLE);
        fragmentList = new ArrayList<>();
        dots = new ArrayList<>();
        dotsLayout = (LinearLayout)findViewById(R.id.view_pager_dots);
        if (locations != null && units != null)
        {
            for (int i = 0; i < locations.size(); i++)
            {
                String location = locations.get(i);
                String unit = units.get(i);
                ForecastFragment forecastFragment = ForecastFragment.newInstance(location, unit);
                fragmentList.add(forecastFragment);
                addDot();
            }
        }
        if (location != null && unit != null)
        {
            ForecastFragment forecastFragment = ForecastFragment.newInstance(location, unit);
            fragmentList.add(forecastFragment);
            addDot();
        }
        highlightDot(0);
        pageChangeListener = new DetailOnPageChangedListener();
        DepthPageTransformer depthPageTransformer = new DepthPageTransformer();
        mainPager.addOnPageChangeListener(pageChangeListener);
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainPager.setAdapter(mainPagerAdapter);
        mainPager.setPageTransformer(true, depthPageTransformer);
        showButtons = false;
        addButton = (ImageButton) findViewById(R.id.add_btn);
        addButton.setOnClickListener(this);

        removeButton = (ImageButton) findViewById(R.id.remove_btn);
        removeButton.setOnClickListener(this);
        if (showButtons)
        {
            addButton.setVisibility(View.VISIBLE);
            removeButton.setVisibility(View.VISIBLE);
        }
        else
        {
            addButton.setVisibility(View.INVISIBLE);
            removeButton.setVisibility(View.INVISIBLE);
        }


        progressBar.setVisibility(View.GONE);
    }

    private void addDot()
    {
        dotsLayout.removeAllViews();
        TextView dot = new TextView(this);
        dot.setText(Html.fromHtml("&#8226;"));
        dot.setTextSize(40);
        dot.setTextColor(getResources().getColor(android.R.color.darker_gray));

        dots.add(dot);
        for (TextView d: dots)
        {
            dotsLayout.addView(d);
        }
        dots.get(dots.size()-1).setTextColor(getResources().getColor(R.color.LightBlue));
        dots.get(dots.size()-1).setTextSize(40);
    }

    private void removeDot(int pos)
    {
        dotsLayout.removeAllViews();
        dots.remove(pos);
        for (TextView d: dots)
        {
            d.setTextColor(getResources().getColor(android.R.color.darker_gray));
            d.setText(Html.fromHtml("&#8226;"));
            d.setTextSize(40);
            dotsLayout.addView(d);
        }
        if (dots.size() != 0)
        {
            if (dots.size() >= 2)
            {
                if (pos != dots.size())
                {
                    dots.get(pos).setTextColor(getResources().getColor(R.color.LightBlue));
                    dots.get(pos).setTextSize(40);
                }
                else
                {
                    dots.get(pos - 1).setTextColor(getResources().getColor(R.color.LightBlue));
                    dots.get(pos - 1).setTextSize(40);
                }
            }
            else
            {
                dots.get(0).setTextColor(getResources().getColor(R.color.LightBlue));
                dots.get(0).setTextSize(40);
            }
        }

    }

    private void highlightDot(int pos)
    {
        dotsLayout.removeAllViews();

        for (TextView d: dots)
        {
            d.setTextColor(getResources().getColor(android.R.color.darker_gray));
            d.setText(Html.fromHtml("&#8226;"));
            dots.get(pos).setTextSize(40);
            dotsLayout.addView(d);
        }
        dots.get(pos).setTextColor(getResources().getColor(R.color.LightBlue));
        dots.get(pos).setText(Html.fromHtml("&#8226;"));
        dots.get(dots.size()-1).setTextSize(40);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        initializeView();
    }

    private void showDialog()
    {
        FragmentManager fm = getSupportFragmentManager();
        AddNewCityDialogFragment addNewCityDialogFragment = new AddNewCityDialogFragment();
        addNewCityDialogFragment.show(fm, "new_location_dialog");

    }

    @Override
    public void onFinishEditDialog(final String location, final String unit)
    {
        addPage(location, unit);
    }

    private void addPage(final String location, final String unit)
    {
        progressBar.setVisibility(View.VISIBLE);
        ForecastFragment forecastFragment = ForecastFragment.newInstance(location, unit);
        if (fragmentList == null)
            fragmentList = new ArrayList<>();
        fragmentList.add(forecastFragment);
        mainPagerAdapter.notifyDataSetChanged();
        addDot();
        mainPager.setCurrentItem(fragmentList.size() - 1);
        progressBar.setVisibility(View.GONE);
    }

    private void removePage()
    {
        progressBar.setVisibility(View.VISIBLE);
        mainPager.addOnPageChangeListener(pageChangeListener);
        removeDot(pageChangeListener.getCurrentPage());
        if (fragmentList != null)
            fragmentList.remove(pageChangeListener.getCurrentPage());
        mainPagerAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        if (fragmentList.size() == 0)
        {
            commitChangesToFile();
            this.finish();
        }
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
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getActionMasked();
        final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                if (!showButtons)
                {
                    showButtons = true;
                    addButton.setAnimation(fadeIn);
                    removeButton.setAnimation(fadeIn);
                    addButton.setVisibility(View.VISIBLE);
                    removeButton.setVisibility(View.VISIBLE);
                }
                break;
            case MotionEvent.ACTION_UP:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (showButtons)
                        {
                            showButtons = false;
                            addButton.setAnimation(fadeOut);
                            removeButton.setAnimation(fadeOut);
                            addButton.setVisibility(View.INVISIBLE);
                            removeButton.setVisibility(View.INVISIBLE);
                        }
                    }
                }, 4000);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed()
    {
        commitChangesToFile();
        finish();
    }
}
