package com.fengpanhome.weatherapp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import com.fengpanhome.weatherapp.R;


public class SearchActivity extends Activity implements View.OnClickListener
{

    private AutoCompleteTextView input;
    private String unit;

    private Button fButton;
    private Button cButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeView();

    }

    private void initializeView()
    {
        ImageButton searchBtn = (ImageButton) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);
        input = (AutoCompleteTextView) findViewById(R.id.location_input);
        fButton = (Button)findViewById(R.id.f_button);
        cButton = (Button)findViewById(R.id.c_button);
        fButton.setText((char) 0x00b0 + "F");
        cButton.setText((char) 0x00b0 + "C");

        fButton.setTextColor(getResources().getColor(R.color.White));
        cButton.setTextColor(getResources().getColor(R.color.White));

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
                args.putString("LOCATION", input.getText().toString());
                args.putString("UNIT", unit);
                intent.putExtras(args);
                startActivity(intent);
                break;
            }
            case R.id.f_button:
                GradientDrawable fButtonDrawable;
                GradientDrawable cButtonDrawable;
            {
                unit = "f";

                fButtonDrawable = (GradientDrawable) fButton.getBackground();
                fButtonDrawable.setColor(getResources().getColor(R.color.accent_material_dark));

                cButtonDrawable = (GradientDrawable) cButton.getBackground();
                cButtonDrawable.setColor(getResources().getColor(R.color.hint_foreground_material_light));

                break;
            }
            case R.id.c_button:
            {
                unit = "c";

                cButtonDrawable = (GradientDrawable) cButton.getBackground();
                cButtonDrawable.setColor(getResources().getColor(R.color.accent_material_dark));

                fButtonDrawable = (GradientDrawable) fButton.getBackground();
                fButtonDrawable.setColor(getResources().getColor(R.color.hint_foreground_material_light));
                break;
            }
            default:
                break;
        }
    }
}
