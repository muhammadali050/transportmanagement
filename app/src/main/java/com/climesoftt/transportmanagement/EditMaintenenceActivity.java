package com.climesoftt.transportmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.climesoftt.transportmanagement.model.Faq;
import com.climesoftt.transportmanagement.model.Maintenance;
import com.climesoftt.transportmanagement.utils.DateDialogs;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditMaintenenceActivity extends AppCompatActivity {
    private EditText et_description;
    private Button btStartDate;
    private Button btEndDate;
    private String mId, startDate, endDate, mDescription;

    private DatePickerDialog datePickerDialogStart;
    private DatePickerDialog datePickerDialogEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_maintenence);

        btStartDate = (Button) findViewById(R.id.btnStartDate);
        btEndDate = (Button) findViewById(R.id.btnEndDate);
        et_description = findViewById(R.id.etDescription);

        Intent intent = getIntent();
        mId = intent.getStringExtra("MID");
        mDescription = intent.getStringExtra("DESCRIPTION");
        startDate = intent.getStringExtra("STARTDATE");
        endDate = intent.getStringExtra("ENDDATE");

        btStartDate.setText(startDate);
        btEndDate.setText(endDate);
        et_description.setText(mDescription);

        setStartDateDialog();
        setEndDateDialog();

    }

    public void updateMaintenance(View view) {
        String startDate = btStartDate.getText().toString().trim();
        String endDate = btEndDate.getText().toString().trim();
        String description = et_description.getText().toString();

        Message.show(this, endDate);

        //Validation
        if(TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate) || TextUtils.isEmpty(description))
        {
            Message.show(this , "Make sure fill all the fields!");
            return;
        }

        final Maintenance maintenance = new Maintenance();
        maintenance.setId(mId);
        maintenance.setStartDate(startDate);
        maintenance.setEndDate(endDate);
        maintenance.setDescription(description);

        final PDialog pd = new PDialog(this).message("Updating. . .");
        try
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Maintenance").child(mId);
            ref.setValue(maintenance);
            Message.show(this,"Updated successfully!");
            Intent intent = new Intent(this, AllMaintenceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }catch (Exception e)
        {
            pd.hide();
            Message.show(this,"Something went wrong.\n"+e.getMessage());
        }
        pd.hide();
    }

    private void setStartDateDialog(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        // date picker dialog
        datePickerDialogStart = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        btStartDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
    }



    private void setEndDateDialog(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        // date picker dialog
        datePickerDialogEnd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        btEndDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
    }

    public void onClickEndDate(View view) {
        datePickerDialogEnd.show();
    }

    public void onClickStartDate(View view) {
        datePickerDialogStart.show();
    }
}
