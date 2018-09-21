package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.climesoftt.transportmanagement.model.*;
import com.climesoftt.transportmanagement.utils.AccountManager;
import com.climesoftt.transportmanagement.utils.GenerateUniqueNumber;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ali on 3/19/2018.
 */

public class AddRoute extends AppCompatActivity{
    private DatabaseReference dbRef;
    private EditText toCity, fromCity, toolPlaza, petrolCost, extraCost, description;

    private ArrayAdapter adapterSpinner;
    private ArrayList<String> arrayList = new ArrayList<>();

    private String USER_TYPE = "";
    private String USER_NAME = "";
    private AccountManager accountManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        accountManager = new AccountManager(this);
        USER_NAME = accountManager.getUserName();
        USER_TYPE = accountManager.getUserAccountType();

        dbRef = FirebaseDatabase.getInstance().getReference("Routes");

        toCity = findViewById(R.id.rToCity);
        fromCity = findViewById(R.id.rFromCity);
        toolPlaza  = findViewById(R.id.rToolPlaza);
        petrolCost   = findViewById(R.id.rPetrol);
        extraCost    = findViewById(R.id.rExtras);
        description = findViewById(R.id.textViewRDescription);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    public void addRoute(View view)
    {
        int getId = GenerateUniqueNumber.randomNum();
        final String rid = Integer.toString(getId).trim();
        //final String rId = GenerateUniqueNumber.routeId();
        String to = toCity.getText().toString();
        String from = fromCity.getText().toString();
        String tool = toolPlaza.getText().toString();
        String petrol = petrolCost.getText().toString();
        String extras = extraCost.getText().toString();
        String rDescription = description.getText().toString();

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if(TextUtils.isEmpty(to) || TextUtils.isEmpty(from) || TextUtils.isEmpty(tool) || TextUtils.isEmpty(petrol) || TextUtils.isEmpty(extras))
        {
            Message.show(AddRoute.this, "Please fill all fields!");
            return;
        }

        final PDialog pd = new PDialog(this).message("Route adding. . . ");
        try
        {
            // String uniqueId = String.valueOf(new Date().getTime());
            Routes route = new Routes();
            route.setId(rid);
            route.setToCity(to);
            route.setFromCity(from);
            route.setTooPlaza(tool);
            route.setPetrolCost(petrol);
            route.setExtras(extras);
            route.setrDate(date);
            route.setDescription(rDescription);

            if(!USER_TYPE.isEmpty())
            {
                if(USER_TYPE.equals("Admin"))
                {
                    dbRef.child(rid).setValue(route);
                    Message.show(AddRoute.this,"Added successfully.");
                    this.finish();
                    Intent intent = new Intent(this, AllRoutes.class);
                    startActivity(intent);
                }
            }
            if(!USER_TYPE.isEmpty())
            {
                if(USER_TYPE.equals("Personal"))
                {
                    DatabaseReference pRef = FirebaseDatabase.getInstance().getReference("Personal");
                    route.setAccountType(USER_TYPE);
                    route.setPersonName(USER_NAME);
                    pRef.child(rid).setValue(route);
                    Message.show(AddRoute.this,"Added successfully.");
                    this.finish();
                    Intent intent = new Intent(this, AllRoutes.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }

        }catch (Exception e)
        {
            pd.hide();
            Message.show(AddRoute.this,"Something went wrong.\n"+e.getMessage());
        }
        pd.hide();
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

    @Override
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(this, AllRoutes.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
