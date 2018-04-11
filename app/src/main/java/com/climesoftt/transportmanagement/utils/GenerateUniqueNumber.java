package com.climesoftt.transportmanagement.utils;

import android.text.TextUtils;

import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * Created by AtoZ on 3/21/2018.
 */

public class GenerateUniqueNumber {

    private static String id = "";
    private static String mId = "";
    public static String uniqueId() {
        // Read from the database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    User data = userSnapshot.getValue(User.class);
                    id = data.getId();
                }

                if(TextUtils.isEmpty(id) || id == null || id.equals(""))
                {
                    id = "1";
                }
                else
                {
                    int uid = Integer.parseInt(id);
                    uid = uid+1;
                    id = Integer.toString(uid);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        return id;
    }

    public static int randomNum() {
        Random r = new Random( System.currentTimeMillis() );
        int firstNum = 1000 + r.nextInt(2000);
        int secondNum = 100 + r.nextInt(500) ;
        int thirdNum =  501 + r.nextInt(999);
        int fourthNum =  1 + r.nextInt(98);
        int randomNum = firstNum+secondNum+thirdNum+fourthNum;

        return randomNum;
    }

    public static String mechanicId()
    {
        // Read from the database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("mechanics");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    Person data = userSnapshot.getValue(Person.class);
                    mId = data.getId();
                }

                if(TextUtils.isEmpty(mId) || mId == null || mId.equals(""))
                {
                    mId = "1";
                }
                else
                {
                    int uid = Integer.parseInt(mId);
                    uid = uid+1;
                    mId = Integer.toString(uid);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        return mId;
    }
}
