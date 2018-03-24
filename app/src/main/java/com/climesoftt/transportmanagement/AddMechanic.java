package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.climesoftt.transportmanagement.utils.GenerateRandomNumber;
import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ali on 3/19/2018.
 */

public class AddMechanic extends AppCompatActivity {
    private DatabaseReference dbRef;
    private EditText mName, mPhone, mAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mechanic);
        dbRef = FirebaseDatabase.getInstance().getReference();

        mName = findViewById(R.id.etMName);
        mPhone = findViewById(R.id.etMPhone);
        mAddress = findViewById(R.id.rExtraCost);


        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {

        }
    }

    public void addDriver(View view) {
       // String uniqueId = String.valueOf(new Date().getTime());
        int getId = GenerateRandomNumber.randomNum();
        String id = Integer.toString(getId).trim();
        String name = mName.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String address = mAddress.getText().toString().trim();
        //Validation
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
            Message.show(AddMechanic.this, "Please fill all the fields!");
            return;
        }

        final Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setPhone(phone);
        person.setAddress(address);
        final PDialog pd = new PDialog(this).message("Mechanic Registration.");
        try {
            DatabaseReference driverRef = dbRef.child("mechanics").child(id);
            driverRef.setValue(person);
            Message.show(AddMechanic.this, "Registered successfully.");

            this.finish();
            Intent intent = new Intent(this, AllMechanicsActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            pd.hide();
            Message.show(AddMechanic.this, "Something went wrong.\n" + e.getMessage());
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
}
