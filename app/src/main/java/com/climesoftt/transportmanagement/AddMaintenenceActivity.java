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
    private DatePickerDialog datePickerDialogStart;
    private DatePickerDialog datePickerDialogEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maintenence);

        startDate = (Button) findViewById(R.id.btnStartDate);
        endDate = (Button) findViewById(R.id.btnEndDate);
        et_desc = findViewById(R.id.etDescription);
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
       /* int getId = GenerateUniqueNumber.uniqueId();
        String id = Integer.toString(getId).trim();*/
        String id = GenerateUniqueNumber.uniqueId();
        String sDate = startDate.getText().toString().trim();
        String eDate = endDate.getText().toString().trim();
        String description = et_desc.getText().toString().trim();
        //Validation
        if(TextUtils.isEmpty(sDate) || TextUtils.isEmpty(eDate) || TextUtils.isEmpty(description))
        {
            Message.show(AddMaintenenceActivity.this , "Please fill all the fields!");
            return;
        }

        final Maintenance maintenance = new Maintenance();
        maintenance.setId(id);
        maintenance.setStartDate(sDate);
        maintenance.setEndDate(eDate);
        maintenance.setDescription(description);
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
}
