package com.climesoftt.transportmanagement.utils;

import android.content.Context;
import com.climesoftt.transportmanagement.adapter.PersonAdapter;
import com.climesoftt.transportmanagement.model.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by AtoZ on 3/20/2018.
 */

public class FetchDataPerson{

    private Context context;
    public FetchDataPerson(Context context)
    {
        this.context = context;
    }
    public static String CHILD_REFERENCE_KEY;
    public void fetchDataFromFirebase(final String childReference, final ArrayList<Person> arrayList, final PersonAdapter adapter)
    {
        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference(childReference);
        final PDialog pd = new PDialog(context).message("Loading. . .");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot driverSnapshot : dataSnapshot.getChildren())
                {
                    Person pData = driverSnapshot.getValue(Person.class);
                    arrayList.add(pData);
                }
                adapter.notifyDataSetChanged();
                pd.hide();
                // Call Function For sending child name
                CHILD_REFERENCE_KEY = childReference;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

}
