package com.fengpanhome.weatherapp.view;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.fengpanhome.weatherapp.R;


public class AddNewCityDialogFragment extends DialogFragment implements
        TextView.OnEditorActionListener, View.OnClickListener
{
    private AutoCompleteTextView locationIn;
    private String unit;
    private Button fButton;
    private Button cButton;

    public AddNewCityDialogFragment()
    {
    }

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
            case R.id.f_button_dialog:
                GradientDrawable fButtonDrawable;
                GradientDrawable cButtonDrawable;
            {
                unit = "f";
                fButtonDrawable = (GradientDrawable) fButton.getBackground();
                fButtonDrawable.setColor(ContextCompat.getColor(getContext(), R.color.accent_material_dark));

                cButtonDrawable = (GradientDrawable) cButton.getBackground();
                cButtonDrawable.setColor(ContextCompat.getColor(getContext(), R.color.hint_foreground_material_light));

                break;
            }
            case R.id.c_button_dialog:
            {
                unit = "c";
                cButtonDrawable = (GradientDrawable) cButton.getBackground();
                cButtonDrawable.setColor(ContextCompat.getColor(getContext(), R.color.accent_material_dark));

                fButtonDrawable = (GradientDrawable) fButton.getBackground();
                fButtonDrawable.setColor(ContextCompat.getColor(getContext(), R.color.hint_foreground_material_light));
                break;
            }
            default:
                break;
        }

    }

    public interface AddNewCityDialogListener
    {
        void onFinishEditDialog(String location, String unit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        unit = "f";
        View v = inflater.inflate(R.layout.new_location_dialog, container);
        locationIn = (AutoCompleteTextView)v.findViewById(R.id.location_new);
        locationIn.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
        Button addBtn = (Button)v.findViewById(R.id.dialog_add_btn);
        addBtn.setOnClickListener(this);
        Button cancelBtn = (Button)v.findViewById(R.id.dialog_cancel_btn);
        cancelBtn.setOnClickListener(this);
        locationIn.setOnEditorActionListener(this);

        fButton = (Button)v.findViewById(R.id.f_button_dialog);
        fButton.setOnClickListener(this);
        fButton.setText(R.string.fahrenheit);
        fButton.setTextColor(ContextCompat.getColor(getContext(), R.color.White));

        cButton = (Button)v.findViewById(R.id.c_button_dialog);
        cButton.setOnClickListener(this);
        cButton.setTextColor(ContextCompat.getColor(getContext(), R.color.White));
        cButton.setText(R.string.celsius);
        return v;
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

