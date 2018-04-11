package com.climesoftt.transportmanagement.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

    public static void deleteImage(String imageUrl)
    {
        FirebaseStorage mStorageRef = FirebaseStorage.getInstance();
        if(!TextUtils.isEmpty(imageUrl))
        {
            StorageReference imageRef = mStorageRef.getReferenceFromUrl(imageUrl);
            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });
        }
    }
}
