package com.fengpanhome.weatherapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.fengpanhome.weatherapp.R;



public class MainActivity extends Activity implements View.OnClickListener, Switch.OnCheckedChangeListener
{

    private AutoCompleteTextView input;
    private String unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ImageButton searchBtn = (ImageButton) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);
        Switch unitToggle = (Switch) findViewById(R.id.unit_toggle);
        unitToggle.setOnCheckedChangeListener(this);
        input = (AutoCompleteTextView) findViewById(R.id.txtinput);
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
