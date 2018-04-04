package com.climesoftt.transportmanagement.utils;

import android.content.Context;
import android.content.Intent;

import com.climesoftt.transportmanagement.AllDriversActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by AtoZ on 3/22/2018.
 */

public class DeleteRecord {


    public static void deleteRecordMethod(Context context, String reference , String id)
    {
        final PDialog pd = new PDialog(context).message("Deleting. . .");
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference(reference).child(id);

        dref.removeValue();
        pd.hide();
        Message.show(context,"Record deleted successfully!");
    }
}
