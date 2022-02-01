package com.example.android.niania;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SetTimeFragment.OnTimeChangedListener{


    static int HOUR_PRICE = 13;
    static int DAYS_NMB = 7;
    static int  MONDAY = 1, TUESDAY = 2, WEDNESDAY = 3, THURSDAY = 4, FRIDAY = 5,
                SATURDAY = 6, SUNDAY = 0;
    int hour;
    int minute;
    int modifiedDay = -1;
    int modifiedFromTo = -1;
    double totalHrs = 0;
    double totalAmount = 0;
    TextView tvTime;
    TextView tvDescriptionTotalHours;
    TextView tvDescriptionTotalAmount;
    TextView tvTotalHours;
    TextView tvTotalAmount;
    Button btnGetTime;
    Button btnSetTime;
    Button btnTotal;
    Button btnSave;
    Button btnDelete;
    Button btnCancel;

    String currentTime = "";
    DialogFragment mDialogFragment;

    ArrayList<dayTime> dayTime = new ArrayList<dayTime>();
    ArrayList<TextView> tvTimeFrom = new ArrayList<TextView>();
    ArrayList<TextView> tvTimeTo = new ArrayList<TextView>();
    ArrayList<TextView> tvHrsCount = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find all text views
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvDescriptionTotalHours = (TextView) findViewById(R.id.tv_total_hours);
        tvDescriptionTotalAmount = (TextView) findViewById(R.id.tv_total_amount);
        tvTotalHours = (TextView) findViewById(R.id.tv_hours);
        tvTotalAmount = (TextView) findViewById(R.id.tv_amount);

        // Find all buttons
        btnGetTime = (Button) findViewById(R.id.btn_get_date);
        btnSetTime = (Button) findViewById(R.id.btn_set_date);
        btnTotal = (Button) findViewById(R.id.btn_total);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        // Hide text views and buttons
        btnSetTime.setVisibility(View.GONE);
        tvTime.setVisibility(View.GONE);
        tvDescriptionTotalHours.setVisibility(View.GONE);
        tvDescriptionTotalAmount.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);

        // NIEDZIELA views
        tvTimeFrom.add((TextView) findViewById(R.id.tv_sun_time_from));
        tvTimeTo.add((TextView) findViewById(R.id.tv_sun_time_to));
        tvHrsCount.add((TextView) findViewById(R.id.tv_sun_hrs));
        // Monday views
        tvTimeFrom.add((TextView) findViewById(R.id.tv_mon_time_from));
        tvTimeTo.add((TextView) findViewById(R.id.tv_mon_time_to));
        tvHrsCount.add((TextView) findViewById(R.id.tv_mon_hrs));
        // Tuesday views
        tvTimeFrom.add((TextView) findViewById(R.id.tv_tue_time_from));
        tvTimeTo.add((TextView) findViewById(R.id.tv_tue_time_to));
        tvHrsCount.add((TextView) findViewById(R.id.tv_tue_hrs));
        // Wednesday views
        tvTimeFrom.add((TextView) findViewById(R.id.tv_wed_time_from));
        tvTimeTo.add((TextView) findViewById(R.id.tv_wed_time_to));
        tvHrsCount.add((TextView) findViewById(R.id.tv_wed_hrs));
        // Thursday views
        tvTimeFrom.add((TextView) findViewById(R.id.tv_thu_time_from));
        tvTimeTo.add((TextView) findViewById(R.id.tv_thu_time_to));
        tvHrsCount.add((TextView) findViewById(R.id.tv_thu_hrs));
        // Friday views
        tvTimeFrom.add((TextView) findViewById(R.id.tv_fri_time_from));
        tvTimeTo.add((TextView) findViewById(R.id.tv_fri_time_to));
        tvHrsCount.add((TextView) findViewById(R.id.tv_fri_hrs));
        // SOBOTA views
        tvTimeFrom.add((TextView) findViewById(R.id.tv_sat_time_from));
        tvTimeTo.add((TextView) findViewById(R.id.tv_sat_time_to));
        tvHrsCount.add((TextView) findViewById(R.id.tv_sat_hrs));


        // Initialize dayTime array
        for (int i = 0; i < DAYS_NMB; i++){
            dayTime.add(new dayTime(0,0,0,0, 0));
        }
        // Update displayed time
        displayTime();

        // btnGetTime click listener
        btnGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTime = getCurrentTime();
                tvTime.setText(currentTime);
                tvTime.setVisibility(View.VISIBLE);
                btnSetTime.setVisibility(View.VISIBLE);
                btnGetTime.setVisibility(View.GONE);
            }
        });

        // btnSetTime click listener
        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(hour, minute);
                displayTime();
                currentTime = "";
                tvTime.setVisibility(View.GONE);
                btnSetTime.setVisibility(View.GONE);
                btnGetTime.setVisibility(View.VISIBLE);
            }
        });

        // Calculate total number of hours and amount
        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalHrs = calculateTotalHrs();

                // Check hour price in settings

                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //int defaultVal = Integer((getString(R.string.settings_price_default)))
                String priceString = sharedPrefs.getString(
                        getString(R.string.settings_price_key), getString(R.string.settings_price_default));
                        //String.parseInt((getString(R.string.settings_price_default))));
                int price = Integer.parseInt(priceString);

                //totalAmount = HOUR_PRICE * totalHrs;
                totalAmount = price * totalHrs;


                tvDescriptionTotalHours.setVisibility(View.VISIBLE);
                tvDescriptionTotalAmount.setVisibility(View.VISIBLE);
                tvTotalHours.setVisibility(View.VISIBLE);
                tvTotalAmount.setVisibility(View.VISIBLE);
                tvTotalHours.setText(String.valueOf(totalHrs));
                tvTotalAmount.setText(String.valueOf(totalAmount));

                tvTime.setVisibility(View.GONE);
                btnSetTime.setVisibility(View.GONE);
                btnGetTime.setVisibility(View.GONE);

                btnTotal.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
            }
        });

        // Button cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalHrs = 0;
                totalAmount = 0;
                tvDescriptionTotalHours.setVisibility(View.GONE);
                tvDescriptionTotalAmount.setVisibility(View.GONE);
                tvTotalHours.setVisibility(View.GONE);
                tvTotalAmount.setVisibility(View.GONE);
                btnGetTime.setVisibility(View.VISIBLE);
                btnTotal.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
            }
        });

        // Button delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete stored data
                deleteData();
                // Update displayed time
                displayTime();
                // Show/hide views
                btnGetTime.setVisibility(View.VISIBLE);
                tvDescriptionTotalHours.setVisibility(View.GONE);
                tvDescriptionTotalAmount.setVisibility(View.GONE);
                tvTotalHours.setVisibility(View.GONE);
                tvTotalAmount.setVisibility(View.GONE);
                btnTotal.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);

            }
        });

        // Time click listeners
        for (int i = 0; i < DAYS_NMB; i++) {
            tvTimeFrom.get(i).setOnClickListener(this);
            tvTimeTo.get(i).setOnClickListener(this);
        }

    }

    @Override
    // Options menu initialization
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    // Find selected item id from options menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v){
        int hour;
        int minute;
        switch(v.getId()) {
            case R.id.tv_mon_time_from: {
                hour = dayTime.get(MONDAY).getHourStart();
                minute = dayTime.get(MONDAY).getMinuteStart();
                modifiedDay = MONDAY;
                modifiedFromTo = 0;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_tue_time_from:{
                hour = dayTime.get(TUESDAY).getHourStart();
                minute = dayTime.get(TUESDAY).getMinuteStart();
                modifiedDay = TUESDAY;
                modifiedFromTo = 0;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_wed_time_from:{
                hour = dayTime.get(WEDNESDAY).getHourStart();
                minute = dayTime.get(WEDNESDAY).getMinuteStart();
                modifiedDay = WEDNESDAY;
                modifiedFromTo = 0;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_thu_time_from:{
                hour = dayTime.get(THURSDAY).getHourStart();
                minute = dayTime.get(THURSDAY).getMinuteStart();
                modifiedDay = THURSDAY;
                modifiedFromTo = 0;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_fri_time_from:{
                hour = dayTime.get(FRIDAY).getHourStart();
                minute = dayTime.get(FRIDAY).getMinuteStart();
                modifiedDay = FRIDAY;
                modifiedFromTo = 0;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_sat_time_from:{
                hour = dayTime.get(SATURDAY).getHourStart();
                minute = dayTime.get(SATURDAY).getMinuteStart();
                modifiedDay = SATURDAY;
                modifiedFromTo = 0;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_sun_time_from:{
                hour = dayTime.get(SUNDAY).getHourStart();
                minute = dayTime.get(SUNDAY).getMinuteStart();
                modifiedDay = SUNDAY;
                modifiedFromTo = 0;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_mon_time_to: {
                hour = dayTime.get(MONDAY).getHourEnd();
                minute = dayTime.get(MONDAY).getMinuteEnd();
                modifiedDay = MONDAY;
                modifiedFromTo = 1;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_tue_time_to:{
                hour = dayTime.get(TUESDAY).getHourEnd();
                minute = dayTime.get(TUESDAY).getMinuteEnd();
                modifiedDay = TUESDAY;
                modifiedFromTo = 1;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_wed_time_to:{
                hour = dayTime.get(WEDNESDAY).getHourEnd();
                minute = dayTime.get(WEDNESDAY).getMinuteEnd();
                modifiedDay = WEDNESDAY;
                modifiedFromTo = 1;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_thu_time_to:{
                hour = dayTime.get(THURSDAY).getHourEnd();
                minute = dayTime.get(THURSDAY).getMinuteEnd();
                modifiedDay = THURSDAY;
                modifiedFromTo = 1;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_fri_time_to:{
                hour = dayTime.get(FRIDAY).getHourEnd();
                minute = dayTime.get(FRIDAY).getMinuteEnd();
                modifiedDay = FRIDAY;
                modifiedFromTo = 1;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_sat_time_to:{
                hour = dayTime.get(SATURDAY).getHourEnd();
                minute = dayTime.get(SATURDAY).getMinuteEnd();
                modifiedDay = SATURDAY;
                modifiedFromTo = 1;
                editTime(hour,minute);
                break;
            }
            case R.id.tv_sun_time_to:{
                hour = dayTime.get(SUNDAY).getHourEnd();
                minute = dayTime.get(SUNDAY).getMinuteEnd();
                modifiedDay = SUNDAY;
                modifiedFromTo = 1;
                editTime(hour,minute);
                break;
            }
        }
    }

    String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        String currentTime;
        String minuteString = "";
        if (minute <= 15) {
            minute = 15;
            minuteString = "15";
        } else if (minute > 15 && minute <= 30) {
            minute = 30;
            minuteString = "30";
        } else if (minute > 30 && minute <= 45){
            minute = 45;
            minuteString = "45";
        } else if (minute > 45){
            minute = 0;
            hour = hour + 1;
            minuteString = "00";
        }
        currentTime = hour + ":"+ minuteString;
        return currentTime;
    }

    void setTime(int currentHour, int currentMinute){
        int day = getDay();
        int index = day - 1;
        dayTime.get(index).setHourStart(8);
        dayTime.get(index).setMinuteStart(0);
        dayTime.get(index).setHourEnd(currentHour);
        dayTime.get(index).setMinuteEnd(currentMinute);
        dayTime.get(index).calculateHrs();
    }

    int getDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }

    void displayTime(){
        for (int i = 0; i < DAYS_NMB; i ++){
            String MinuteStart = Integer.toString(dayTime.get(i).getMinuteStart());
            String MinuteEnd = Integer.toString(dayTime.get(i).getMinuteEnd());
            if (dayTime.get(i).getMinuteStart() < 9)
                MinuteStart = "0" + MinuteStart;
            if (dayTime.get(i).getMinuteEnd() < 9)
                MinuteEnd = "0" + MinuteEnd;

            tvTimeFrom.get(i).setText(dayTime.get(i).getHourStart() + ":" + MinuteStart);
            tvTimeTo.get(i).setText(dayTime.get(i).getHourEnd() + ":" + MinuteEnd);
            tvHrsCount.get(i).setText(String.valueOf(dayTime.get(i).getHoursTotal()));
        }
    }


    void editTime(int hour, int minute){
        Bundle bundle = new Bundle();
        bundle.putInt("Hour", hour);
        bundle.putInt("Minute", minute);

        mDialogFragment = new SetTimeFragment();
        mDialogFragment.setArguments(bundle);
        mDialogFragment.show(getFragmentManager(), "Change time");
    }


    public double calculateTotalHrs(){
        double totalHrs = 0;
        for (int i = 0; i < DAYS_NMB; i++){
            totalHrs = totalHrs + dayTime.get(i).getHoursTotal();
        }
        return totalHrs;
    }

    public void OnTimeChanged(int hour, int minute){
        if (modifiedDay != -1 && modifiedFromTo != -1) {
            // Save modified time
            // Starting time modification
            if (modifiedFromTo == 0) {
                if (hour < dayTime.get(modifiedDay).getHourEnd() ||
                    hour == dayTime.get(modifiedDay).getHourEnd() && minute <  dayTime.get(modifiedDay).getMinuteEnd() ||
                    dayTime.get(modifiedDay).getHourEnd() == 0 && dayTime.get(modifiedDay).getMinuteEnd() == 0) {
                    // Minutes rounding - floor
                    if (minute < 15) {
                        minute = 0;
                    } else if (minute > 15 && minute < 30) {
                        minute = 15;
                    } else if (minute > 30 && minute < 45){
                        minute = 30;
                    } else if (minute > 45){
                        minute = 45;
                    }
                    // Save updated time
                    dayTime.get(modifiedDay).setHourStart(hour);
                    dayTime.get(modifiedDay).setMinuteStart(minute);
                    dayTime.get(modifiedDay).calculateHrs();
                    //calculateHrs(modifiedDay);
                    // Reset flags
                    modifiedDay = -1;
                    modifiedFromTo = -1;
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong time was selected",Toast.LENGTH_SHORT).show();
                }
            // End time modification
            } else if (modifiedFromTo == 1) {
                if (hour > dayTime.get(modifiedDay).getHourStart() ||
                    hour == dayTime.get(modifiedDay).getHourStart() && minute > dayTime.get(modifiedDay).getMinuteStart()) {
                    // Minutes rounding - top
                    if (minute > 0 && minute < 15) {
                        minute = 15;
                    } else if (minute > 15 && minute < 30) {
                        minute = 30;
                    } else if (minute > 30 && minute < 45){
                        minute = 45;
                    } else if (minute > 45){
                        minute = 0;
                        hour = hour + 1;
                    }
                    // Save updated time
                    dayTime.get(modifiedDay).setHourEnd(hour);
                    dayTime.get(modifiedDay).setMinuteEnd(minute);
                    if (dayTime.get(modifiedDay).getHourStart() == 0 && dayTime.get(modifiedDay).getMinuteStart() == 0){
                        dayTime.get(modifiedDay).setHourStart(8);
                        dayTime.get(modifiedDay).setMinuteStart(0);
                    }
                    dayTime.get(modifiedDay).calculateHrs();
                    //calculateHrs(modifiedDay);
                    // Reset flags
                    modifiedDay = -1;
                    modifiedFromTo = -1;
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong time was selected",Toast.LENGTH_SHORT).show();
                }
            }
            // Update displayed time
            displayTime();
        }
    }

    // Delete stored times
    public void deleteData(){
        for (int i = 0; i < DAYS_NMB; i ++){
            dayTime.get(i).deleteData();
        }
    }

}
