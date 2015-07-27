package com.fengpanhome.weatherapp;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private String[] mDataset;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_forecast, parent, false);
        v.setMinimumWidth(300);
        v.setMinimumHeight(50);
        return new ViewHolder((TextView) v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount()
    {
        return mDataset.length;
    }

    public RecyclerViewAdapter(String[] dataset)
    {
        mDataset = dataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public ViewHolder(TextView v)
        {
            super(v);
            mTextView = v;
        }
    }
}
