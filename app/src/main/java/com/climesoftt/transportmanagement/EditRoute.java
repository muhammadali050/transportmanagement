package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.climesoftt.transportmanagement.model.Routes;
import com.climesoftt.transportmanagement.utils.AccountManager;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditRoute extends AppCompatActivity {
    private EditText toCity, fromCity, toolPlaza, petrolCost, extraCost, description;
    private String RouteID = "";
    private String RTo = "";
    private String RFrom = "";
    private String RTool = "";
    private String RPetrol = "";
    private String RExtras = "";
    private String RDescription = "";
    private String RDriverEmail = "";

    private String USER_TYPE = "";
    private String USER_EMAIL = "";
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);

        accountManager = new AccountManager(this);
        USER_TYPE = accountManager.getUserAccountType();
        USER_EMAIL = accountManager.getUserEmail();

        toCity = findViewById(R.id.rToCity);
        fromCity = findViewById(R.id.rFromCity);
        toolPlaza  = findViewById(R.id.rToolPlaza);
        petrolCost   = findViewById(R.id.rPetrol);
        extraCost    = findViewById(R.id.rExtras);
        description = findViewById(R.id.rEDescription);

        Intent intent = getIntent();
        if(intent!= null)
        {
            RouteID = intent.getStringExtra("RID");
            RTo = intent.getStringExtra("RTO");
            RFrom = intent.getStringExtra("RFROM");
            RTool = intent.getStringExtra("RTOOL");
            RPetrol = intent.getStringExtra("RPETROL");
            RExtras = intent.getStringExtra("REXTRAS");
            RDescription = intent.getStringExtra("RDESCRIPTION");
            RDriverEmail = intent.getStringExtra("R_DRIVER_EMAIL");
        }

        toCity.setText(RTo);
        fromCity.setText(RFrom);
        toolPlaza.setText(RTool);
        petrolCost.setText(RPetrol);
        extraCost.setText(RExtras);
        description.setText(RDescription);
    }

    public void updateRoute(View view)
    {
        String to = toCity.getText().toString();
        String from = fromCity.getText().toString();
        String tool = toolPlaza.getText().toString();
        String petrol = petrolCost.getText().toString();
        String extras = extraCost.getText().toString();
        String desc = description.getText().toString();
        if(TextUtils.isEmpty(to) || TextUtils.isEmpty(from) || TextUtils.isEmpty(tool) || TextUtils.isEmpty(petrol) || TextUtils.isEmpty(extras))
        {
            Message.show(this, "Please fill all fields!");
            return;
        }

        Routes route = new Routes();
        route.setId(RouteID);
        route.setToCity(to);
        route.setFromCity(from);
        route.setTooPlaza(tool);
        route.setPetrolCost(petrol);
        route.setExtras(extras);
        route.setDescription(desc);

        final PDialog pd = new PDialog(this).message("Route updating. . . ");
        try {
            //DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Routes").child(RouteID);
           // dbRef.setValue(route);

            if(!USER_TYPE.isEmpty())
            {
                if(USER_TYPE.equals("Admin"))
                {
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Routes").child(RouteID);
                    route.setEmail(RDriverEmail);
                    dbRef.setValue(route);
                }
            }

            if(!USER_TYPE.isEmpty())
            {
                if(USER_TYPE.equals("Personal"))
                {
                    DatabaseReference pRef = FirebaseDatabase.getInstance().getReference("Personal").child(RouteID);
                    route.setAccountType(USER_TYPE);
                    route.setEmail(USER_EMAIL);
                    pRef.setValue(route);
                }
            }

            Message.show(this,"Record updated successfully.");
            this.finish();
            Intent int_newActivity = new Intent(this, AllRoutes.class);
            int_newActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(int_newActivity);
        }catch (Exception e)
        {
            pd.hide();
            Message.show(this,"Something went wrong.\n"+e.getMessage());
        }
        pd.hide();

    }
}
