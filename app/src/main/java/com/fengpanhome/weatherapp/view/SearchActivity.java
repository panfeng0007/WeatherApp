package com.fengpanhome.weatherapp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.fengpanhome.weatherapp.R;


public class SearchActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AutoCompleteTextView.OnKeyListener
{

    private AutoCompleteTextView inputTextView;
    private String unit;

    private Button fButton;
    private Button cButton;

    private CheckBox queryMetar;
    private CheckBox queryTaf;
    private CheckBox queryPirep;
    private CheckBox queryAreaForecast;

    private boolean pilotMode;
    private boolean isCheckingMetar;
    private boolean isCheckingTaf;
    private boolean isCheckingPirep;
    private boolean isCheckingAreaForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeView();
        unit = "f";
    }

    private void initializeView()
    {
        ImageButton searchBtn = (ImageButton) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);
        inputTextView = (AutoCompleteTextView)findViewById(R.id.location_input);
        inputTextView.setOnKeyListener(this);

        fButton = (Button)findViewById(R.id.f_button);
        cButton = (Button)findViewById(R.id.c_button);
        fButton.setText((char) 0x00b0 + "F");
        cButton.setText((char) 0x00b0 + "C");

        Switch pilotModeToggle = (Switch)findViewById(R.id.is_pilot_toggle);
        pilotModeToggle.setOnCheckedChangeListener(this);

        fButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.White));
        cButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.White));

        fButton.setOnClickListener(this);
        cButton.setOnClickListener(this);

        queryMetar = (CheckBox)findViewById(R.id.query_metar);
        queryMetar.setOnCheckedChangeListener(this);
        queryTaf = (CheckBox)findViewById(R.id.query_taf);
        queryTaf.setOnCheckedChangeListener(this);
        queryPirep = (CheckBox)findViewById(R.id.query_pirep);
        queryPirep.setOnCheckedChangeListener(this);
        queryAreaForecast = (CheckBox)findViewById(R.id.query_area_forecast);
        queryAreaForecast.setOnCheckedChangeListener(this);

        pilotMode = false;
        isCheckingMetar = false;
        isCheckingTaf = false;
        isCheckingPirep = false;
        isCheckingAreaForecast = false;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.search_btn:
            {
                Intent intent = new Intent(this, ForecastActivity.class);
                Bundle args = new Bundle();
                args.putString("ACTIVITY", "SearchActivity");
                args.putString("LOCATION", inputTextView.getText().toString());
                args.putString("UNIT", unit);
                args.putBoolean("PILOT_MODE", pilotMode);
                args.putBoolean("CHECKING_METAR", isCheckingMetar);
                args.putBoolean("CHECKING_TAF", isCheckingTaf);
                args.putBoolean("CHECKING_PIREP", isCheckingPirep);
                args.putBoolean("CHECKING_AREA_FORECAST", isCheckingAreaForecast);
                intent.putExtras(args);
                startActivity(intent);
                this.finish();
                break;
            }
            case R.id.f_button:
                GradientDrawable fButtonDrawable;
                GradientDrawable cButtonDrawable;
            {
                unit = "f";

                fButtonDrawable = (GradientDrawable) fButton.getBackground();
                fButtonDrawable.setColor(ContextCompat.getColor(v.getContext(), R.color.accent_material_dark));

                cButtonDrawable = (GradientDrawable) cButton.getBackground();
                cButtonDrawable.setColor(ContextCompat.getColor(v.getContext(), R.color.hint_foreground_material_light));

                break;
            }
            case R.id.c_button:
            {
                unit = "c";

                cButtonDrawable = (GradientDrawable) cButton.getBackground();
                cButtonDrawable.setColor(ContextCompat.getColor(v.getContext(), R.color.accent_material_dark));

                fButtonDrawable = (GradientDrawable) fButton.getBackground();
                fButtonDrawable.setColor(ContextCompat.getColor(v.getContext(), R.color.hint_foreground_material_light));
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

            case R.id.is_pilot_toggle:
            {
                final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
                final Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                if (isChecked)
                {
                    pilotMode = true;

                    queryMetar.setVisibility(View.VISIBLE);
                    queryMetar.setAnimation(fadeIn);

                    queryTaf.setVisibility(View.VISIBLE);
                    queryTaf.setAnimation(fadeIn);

                    queryPirep.setVisibility(View.VISIBLE);
                    queryPirep.setAnimation(fadeIn);

                    queryAreaForecast.setVisibility(View.VISIBLE);
                    queryAreaForecast.setAnimation(fadeIn);

                    inputTextView.setHint(R.string.hint_airport_code);

                }
                else
                {
                    pilotMode = false;

                    queryMetar.setVisibility(View.INVISIBLE);
                    queryMetar.setAnimation(fadeOut);

                    queryTaf.setVisibility(View.INVISIBLE);
                    queryTaf.setAnimation(fadeOut);

                    queryPirep.setVisibility(View.INVISIBLE);
                    queryPirep.setAnimation(fadeOut);

                    queryAreaForecast.setVisibility(View.INVISIBLE);
                    queryAreaForecast.setAnimation(fadeOut);

                    inputTextView.setHint(R.string.hint_location);
                    isCheckingMetar = false;
                    isCheckingTaf = false;
                    isCheckingPirep = false;
                    isCheckingAreaForecast = false;
                }
                break;
            }
            case R.id.query_metar:
            {
                isCheckingMetar = isChecked;
                break;
            }
            case R.id.query_taf:
            {
                isCheckingTaf = isChecked;
                break;
            }
            case R.id.query_pirep:
            {
                isCheckingPirep = isChecked;
                break;
            }
            case R.id.query_area_forecast:
            {
                isCheckingAreaForecast = isChecked;
                break;
            }
            default:
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() != KeyEvent.ACTION_DOWN)
        {
            Intent intent = new Intent(this, ForecastActivity.class);
            Bundle args = new Bundle();
            args.putString("ACTIVITY", "SearchActivity");
            args.putString("LOCATION", inputTextView.getText().toString());
            args.putString("UNIT", unit);
            args.putBoolean("CHECKING_METAR", isCheckingMetar);
            args.putBoolean("CHECKING_TAF", isCheckingTaf);
            args.putBoolean("CHECKING_PIREP", isCheckingPirep);
            args.putBoolean("CHECKING_AREA_FORECAST", isCheckingAreaForecast);
            intent.putExtras(args);
            startActivity(intent);
            this.finish();
            return true;
        }
        return false;
    }
}
