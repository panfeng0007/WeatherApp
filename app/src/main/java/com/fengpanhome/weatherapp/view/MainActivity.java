package com.fengpanhome.weatherapp.com.fengpanhome.weatherapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Switch;

import com.fengpanhome.weatherapp.R;

public class MainActivity extends Activity implements OnClickListener, Switch.OnCheckedChangeListener
{
    private String unit = "f";
    private AutoCompleteTextView input;
    private ViewPager swipeView;

    private class MainViewPagerAdapter extends FragmentPagerAdapter
    {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton searchBtn = (ImageButton) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);

        Switch unitToggle = (Switch) findViewById(R.id.unit_toggle);
        unitToggle.setOnCheckedChangeListener(this);
        input = (AutoCompleteTextView) findViewById(R.id.txtinput);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.search_btn:
            {
                Intent intent = new Intent(this, ForecastActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("LOCATION", input.getText().toString());
                bundle.putString("UNIT", unit);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        switch (buttonView.getId())
        {
            case R.id.unit_toggle:
            {
                if (isChecked)
                {
                    unit = "c";
                }
                else
                {
                    unit = "f";
                }
                break;
            }
            default:
                break;
        }
    }
}
