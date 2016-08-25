package com.fengpanhome.weatherapp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import com.fengpanhome.weatherapp.R;


public class SearchActivity extends Activity implements View.OnClickListener, AutoCompleteTextView.OnKeyListener
{

    private AutoCompleteTextView inputTextView;
    private String unit;

    private Button fButton;
    private Button cButton;

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
        fButton.setText(R.string.fahrenheit);
        cButton.setText(R.string.celsius);

        fButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.White));
        cButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.White));

        fButton.setOnClickListener(this);
        cButton.setOnClickListener(this);

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
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() != KeyEvent.ACTION_DOWN)
        {
            Intent intent = new Intent(this, ForecastActivity.class);
            Bundle args = new Bundle();
            args.putString("ACTIVITY", "SearchActivity");
            args.putString("LOCATION", inputTextView.getText().toString());
            args.putString("UNIT", unit);
            intent.putExtras(args);
            startActivity(intent);
            this.finish();
            return true;
        }
        return false;
    }
}
