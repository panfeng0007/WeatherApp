package com.fengpanhome.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class ForecastActivity extends Activity
{

    private RecyclerView mRecylerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mRecylerView = (RecyclerView)findViewById(R.id.rview);
        mRecylerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(mLayoutManager);

        String[] m = new String[] {"1","2","3","4"};
        mAdapter = new RecyclerViewAdapter(m);
        mRecylerView.setAdapter(mAdapter);
    }

}
