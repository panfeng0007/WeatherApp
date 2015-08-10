package com.fengpanhome.weatherapp.view;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.fengpanhome.weatherapp.R;


public class AddNewCityDialogFragment extends DialogFragment implements
        TextView.OnEditorActionListener, Switch.OnCheckedChangeListener, View.OnClickListener
{
    private AutoCompleteTextView locationIn;
    private String unit;

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.dialog_add_btn:
            {
                AddNewCityDialogListener activity = (AddNewCityDialogListener) getActivity();
                activity.onFinishEditDialog(locationIn.getText().toString(), unit);
                AddNewCityDialogFragment.this.getDialog().cancel();
                return;
            }
            case R.id.dialog_cancel_btn:
            {
                AddNewCityDialogFragment.this.getDialog().cancel();
                return;
            }
            default:break;

        }

    }

    public interface AddNewCityDialogListener
    {
        void onFinishEditDialog(String location, String unit);
    }
    public AddNewCityDialogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.new_location_dialog, container);
        locationIn = (AutoCompleteTextView)v.findViewById(R.id.location_new);
        Switch unitToggle = (Switch) v.findViewById(R.id.unit_toggle_new);
        locationIn.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
        Button addBtn = (Button)v.findViewById(R.id.dialog_add_btn);
        addBtn.setOnClickListener(this);
        Button cancelBtn = (Button)v.findViewById(R.id.dialog_cancel_btn);
        cancelBtn.setOnClickListener(this);
        locationIn.setOnEditorActionListener(this);
        unitToggle.setOnCheckedChangeListener(this);
        return v;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        switch (buttonView.getId())
        {
            case R.id.unit_toggle_new:
            {
                if (isChecked)
                    unit = "c";
                else
                    unit = "f";
            }
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if (EditorInfo.IME_ACTION_DONE == actionId)
        {
            AddNewCityDialogListener activity = (AddNewCityDialogListener) getActivity();
            activity.onFinishEditDialog(locationIn.getText().toString(), unit);
            this.dismiss();
            return true;
        }
        return false;
    }
}

