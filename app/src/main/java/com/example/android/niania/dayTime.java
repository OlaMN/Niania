package com.example.android.niania;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MielcarekA on 1/31/2018.
 */

public class dayTime {

    private static final int DELETE_VALUE = 0;
    /**
     * Time Start - hour
     */
    private int mHourStart;

    /**
     * Time Start - minute
     */
    private int mMinuteStart;

    /**
     * Time End - hour
     */
    private int mHourEnd;

    /**
     * Time End - minute
     */
    private int mMinuteEnd;

    /**
     * Total number of hours
     */
    private double mHoursTotal;

    /**
     * Constructs a new dayTime.
     */
      dayTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd, double hoursTotal) {
          mHourStart = hourStart;
          mMinuteStart = minuteStart;
          mHourEnd = hourEnd;
          mMinuteEnd = minuteEnd;
          mHoursTotal = hoursTotal;
    }

    // Getters and setters

    /**
     * Gets starting hour.
     *
     * @return starting hour.
     */
    int getHourStart() {
        return mHourStart;
    }

    /**
     * Gets starting minute.
     *
     * @return starting minute.
     */
    int getMinuteStart() {
        return mMinuteStart;
    }

    /**
     * Gets starting hour.
     *
     * @return starting hour.
     */
    int getHourEnd() {
        return mHourEnd;
    }

    /**
     * Gets starting minute.
     *
     * @return starting minute.
     */
    int getMinuteEnd() {
        return mMinuteEnd;
    }

    /**
     * Gets total hours number.
     *
     * @return total hours number.
     */
    double getHoursTotal() {
        return mHoursTotal;
    }

    /**
     * Sets starting hour.
     *
     */
    void setHourStart(int hourStart) {
        mHourStart = hourStart;
    }

    /**
     * Sets starting minute.
     *
     */
    void setMinuteStart(int minuteStart) {
        mMinuteStart = minuteStart;
    }

    /**
     * Sets end hour.
     *
     */
    void setHourEnd(int hourEnd) {
        mHourEnd = hourEnd;
    }

    /**
     * Sets starting minute.
     *
     */
    void setMinuteEnd(int minuteEnd) {
        mMinuteEnd = minuteEnd;
    }

    /**
     * Sets total hours number.
     *
     */
 //   void setHoursTotal(double hoursTotal) {
 //       mHoursTotal = hoursTotal;
 //   }

    public void calculateHrs(){
        double startHour = mHourStart;
        double startMinute = mMinuteStart;
        startMinute = startMinute / 60;
        double endHour = mHourEnd;
        double endMinute = mMinuteEnd;
        endMinute = endMinute / 60;
        mHoursTotal = (endHour + endMinute) - (startHour + startMinute);
    }

    public void deleteData(){
        mHourStart = DELETE_VALUE;
        mMinuteStart = DELETE_VALUE;
        mHourEnd = DELETE_VALUE;
        mMinuteEnd = DELETE_VALUE;
        mHoursTotal = DELETE_VALUE;
    }

}





