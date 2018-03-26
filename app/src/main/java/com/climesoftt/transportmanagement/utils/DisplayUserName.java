package com.climesoftt.transportmanagement.utils;

import android.content.Context;

import com.climesoftt.transportmanagement.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by AtoZ on 3/24/2018.
 */

public class DisplayUserName {
    private static String userName = "";
    public static String displayUserName(Context context, final String userEmail)
    {
        try
        {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren())
                    {
                        User user = userSnapshot.getValue(User.class);
                        if(userEmail.equals(user.getEmail()))
                        {
                            userName = user.getName();
                        }
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }catch (Exception e)
        {
            Message.show(context,"Something went wrong.\n"+e.getMessage());
        }
        return userName;
    }
}
