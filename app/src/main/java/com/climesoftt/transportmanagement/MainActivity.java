package com.climesoftt.transportmanagement;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().hide();
        }
        catch (Exception e){

        }

        boolean alarm = (PendingIntent.getBroadcast(this, 0, new Intent("ALARM"), PendingIntent.FLAG_NO_CREATE) == null);

        if(alarm){
            Intent itAlarm = new Intent("ALARM");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,itAlarm,0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 3);
            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60000, pendingIntent);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
       /* final PDialog pd = new PDialog(this).message("logging . . .");
        LoginActivity login = new LoginActivity();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if(user != null)
        {
            String email = user.getEmail();
            login.moveLoginUserHisDashboard(email);
            pd.hide();
        }else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        pd.hide();*/
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onClickLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, AddDriver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void onClickRegisterPersonal(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
