package com.climesoftt.transportmanagement.utils;

import com.climesoftt.transportmanagement.model.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by AtoZ on 3/21/2018.
 */

public class DriversList {

    public static void getDriversList(final ArrayList<String> arrayList)
    {
        DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference("drivers");
        driverRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot driverSnapshot : dataSnapshot.getChildren())
                {
                    Person data = driverSnapshot.getValue(Person.class);
                    String driver = data.getName();
                    arrayList.add(driver);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
