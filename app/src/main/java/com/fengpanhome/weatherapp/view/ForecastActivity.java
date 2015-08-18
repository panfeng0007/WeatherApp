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

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private final Handler handler = new Handler();
    private String location;
    private String unit;
    private ArrayList<ForecastFragment> fragmentList;
    private MainPagerAdapter mainPagerAdapter;
    private ViewPager mainPager;
    private DetailOnPageChangedListener pageChangeListener;
    private ArrayList<TextView> dots;
    private LinearLayout dotsLayout;
    private ImageButton addButton;
    private ImageButton removeButton;
    private boolean showButtons;


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

    private void commitChanges()
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream("cities.weather", false);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            for (ForecastFragment f : fragmentList)
            {
                outputStream.writeObject(f);
            }
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
        location = args.getString("LOCATION");
        unit = args.getString("UNIT");

        progressBar = (ProgressBar) findViewById(R.id.progress);

        mainPager = (ViewPager)findViewById(R.id.main_pager);

        progressStatus = 0;
        new Thread(new Runnable() {
            public void run()
            {
                while (progressStatus < 100)
                {
                    progressStatus += 1;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);

                            ForecastFragment forecastFragment = ForecastFragment.newInstance(location, unit);
                            fragmentList = new ArrayList<>();
                            fragmentList.add(forecastFragment);
                            DetailOnPageChangedListener listener = new DetailOnPageChangedListener();
                            mainPager.addOnPageChangeListener(listener);
                            mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);
                            mainPager.setAdapter(mainPagerAdapter);
                        }
                    });
                }
            }
        }).start();
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

        dots = new ArrayList<>();
        dotsLayout = (LinearLayout)findViewById(R.id.view_pager_dots);
        addDot();
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
        ForecastFragment forecastFragment = ForecastFragment.newInstance(location, unit);
        if (fragmentList == null)
            fragmentList = new ArrayList<>();
        fragmentList.add(forecastFragment);
        mainPagerAdapter.notifyDataSetChanged();
        addDot();
        mainPager.setCurrentItem(fragmentList.size() - 1);
    }

    private void removePage()
    {
        mainPager.addOnPageChangeListener(pageChangeListener);
        removeDot(pageChangeListener.getCurrentPage());
        if (fragmentList != null)
            fragmentList.remove(pageChangeListener.getCurrentPage());
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
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getActionMasked();
        final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                showButtons = !showButtons;
                if (showButtons)
                {
                    addButton.setAnimation(fadeIn);
                    removeButton.setAnimation(fadeIn);
                    addButton.setVisibility(View.VISIBLE);
                    removeButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    addButton.setAnimation(fadeOut);
                    removeButton.setAnimation(fadeOut);
                    addButton.setVisibility(View.INVISIBLE);
                    removeButton.setVisibility(View.INVISIBLE);
                }
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onDestroy()
    {
        commitChanges();
        super.onDestroy();
    }
}
