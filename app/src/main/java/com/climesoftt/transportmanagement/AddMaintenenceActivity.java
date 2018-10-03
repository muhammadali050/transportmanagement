package com.climesoftt.transportmanagement;
import com.climesoftt.transportmanagement.utils.AccountManager;
import  com.climesoftt.transportmanagement.utils.NotificationManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.climesoftt.transportmanagement.model.Maintenance;
import com.climesoftt.transportmanagement.utils.GenerateUniqueNumber;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddMaintenenceActivity extends AppCompatActivity {
    private EditText et_desc;
    private Button startDate;
    private Button endDate;
    private String USER_EMAIL = "";
    private String USER_TYPE = "";
    private AccountManager accountManager;
    private DatePickerDialog datePickerDialogStart;
    private DatePickerDialog datePickerDialogEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maintenence);

        accountManager = new AccountManager(this);
        USER_EMAIL = accountManager.getUserEmail();
        USER_TYPE = accountManager.getUserAccountType();

        //Get maintenance id zero first time when add data into database must call here
        GenerateUniqueNumber.maintenanceId();

        startDate = (Button) findViewById(R.id.btnStartDate);
        endDate = (Button) findViewById(R.id.btnEndDate);
        et_desc = findViewById(R.id.etDescription);
        et_desc.setFocusable(false);
        setStartDateDialog();
        setEndDateDialog();
    }

    public void onClickStartDate(View view) {
        datePickerDialogStart.show();

    }

    public void onClickEndDate(View view) {
        datePickerDialogEnd.show();
    }


    private void setStartDateDialog(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        addNotification(mYear, mMonth, mDay);
        // date picker dialog
        datePickerDialogStart = new DatePickerDialog(AddMaintenenceActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        startDate.setText(dayOfMonth + "/"
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
        datePickerDialogEnd = new DatePickerDialog(AddMaintenenceActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        endDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
    }


    public void addMaintenance(View view) {

        final String id = GenerateUniqueNumber.maintenanceId();
        String sDate = startDate.getText().toString().trim();
        String eDate = endDate.getText().toString().trim();
        String description = et_desc.getText().toString().trim();
        //Validation
        if(TextUtils.isEmpty(sDate) || TextUtils.isEmpty(eDate) || TextUtils.isEmpty(description) || sDate.equals("Start Date")
                || eDate.equals("End date"))
        {
            Message.show(AddMaintenenceActivity.this , "Please fill all the fields!\nMust select Start and End date!");
            return;
        }

        final Maintenance maintenance = new Maintenance();
        maintenance.setId(id);
        maintenance.setStartDate(sDate);
        maintenance.setEndDate(eDate);
        maintenance.setDescription(description);
        maintenance.setEmail(USER_EMAIL);
        maintenance.setUserType(USER_TYPE);
        final PDialog pd = new PDialog(this).message("Adding . . .");
        try
        {
            //String uniqueId = String.valueOf(new Date().getTime());
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Maintenance").child(id);
            reference.setValue(maintenance);
            Message.show(AddMaintenenceActivity.this,"Added successfully.");


            //this.finish();
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

    public void addNotification(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(year,month,day, 8 , 30);
        NotificationManager.setAlarm(this, c);
    }
}
