package com.climesoftt.transportmanagement.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.climesoftt.transportmanagement.EditDriver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by AtoZ on 3/30/2018.
 */

public class ImageUpload {

    private StorageReference mStorageRef;
    private ImageView imgViewDriver;
    public String imgUrl = "";

    //Upload Image
    public String uploadImage(final Context context, Uri filePath) {
        mStorageRef = FirebaseStorage.getInstance().getReference();

        if(filePath!=null)
        {
            final StorageReference imagesRef = mStorageRef.child("images/"+filePath.getLastPathSegment());
            Message.show(context,"Please wait...");
            UploadTask uploadTask = imagesRef.putFile(filePath);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                    //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    imgUrl = taskSnapshot.getDownloadUrl().toString();
                    //Message.show(context,"Image uploaded!Go for Registration...");
                }

            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Message.show(context, "Failed image uploading..\n"+exception.getMessage());
                        }
                    });
        }
        return imgUrl;
    }

    /*
    public void imageValidation()
    {
        if(TextUtils.isEmpty(imgUri) || imgUri == null)
        {
            //default image
            imgUri = "https://firebasestorage.googleapis.com/v0/b/transportmanagement-404c2.appspot.com/o/profile_icon.png?alt=media&token=446a67c3-8d8a-4b74-9443-df1ec27e27da";
        }
    }
    */
}
