package com.example.android.niania;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class SetTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    OnTimeChangedListener mCallback;

    public interface OnTimeChangedListener {
        public void OnTimeChanged(int hour, int minute);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnTimeChangedListener)activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement OnTimePickedListener.");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mCallback = (OnTimeChangedListener)getActivity();
        int storedHour = getArguments().getInt("Hour");
        int storedMinute = getArguments().getInt("Minute");

        //final Calendar calendar = Calendar.getInstance();
        //hour = calendar.get(Calendar.HOUR_OF_DAY);
        //minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog mTimePickerDialog = new TimePickerDialog(getActivity(), this, storedHour, storedMinute, true);

        return mTimePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        if (mCallback != null) {
            mCallback.OnTimeChanged(hour, minute);

        }
    }

}

