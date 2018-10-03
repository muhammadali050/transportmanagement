package com.climesoftt.transportmanagement.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import com.climesoftt.transportmanagement.AddMaintenenceActivity;

import java.util.Calendar;

/**
 * Created by AtoZ on 3/25/2018.
 */

public class DateDialogs {
    private String endDate = "";
    private  String startDate = "";
    private Context context;

    private DatePickerDialog datePickerDialogStart;
    private DatePickerDialog datePickerDialogEnd;
    public DateDialogs(Context context)
    {
        this.context = context;
    }


    public void setStartDate() {
        datePickerDialogStart.show();
    }

    public void setEndDate() {
        datePickerDialogEnd.show();
    }

    public String setStartDateDialog(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        // date picker dialog
        datePickerDialogStart = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        startDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                    }
                }, mYear, mMonth, mDay);
        return startDate;
    }



    public String setEndDateDialog(){

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        // date picker dialog
        datePickerDialogEnd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        endDate = dayOfMonth + "/" +(monthOfYear + 1) + "/" + year;
                    }
                }, mYear, mMonth, mDay);
        return endDate;
    }
}
