package com.climesoftt.transportmanagement;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.ImageUpload;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by Ali on 3/19/2018.
 */

public class EditMechanic extends AppCompatActivity {
    private TextView tv_mName, tv_mPhone, tv_mAddress;
    private String mId, mName, mPhone, mAddress, mImage;
    private Button bt_update;
    private static final int chooseImageCode = 1;
    private Uri filePath;
    private StorageReference mStorageRef;
    private ImageView imgViewPerson;
    private String imgUri = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mechanic);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        tv_mName = findViewById(R.id.mUName);
        tv_mPhone = findViewById(R.id.mUPhone);
        tv_mAddress = findViewById(R.id.mUAddress);
        imgViewPerson = findViewById(R.id.mUImg);
        bt_update = findViewById(R.id.mUBtnUpdate);
        Intent intent = getIntent();
        if(intent!= null)
        {
            mId = intent.getStringExtra("MC_ID_KEY");
            mName = intent.getStringExtra("MC_NAME_KEY");
            mPhone = intent.getStringExtra("MC_PHONE_KEY");
            mAddress = intent.getStringExtra("MC_ADDRESS_KEY");
            mImage = intent.getStringExtra("MC_IMAGE_KEY");
        }

        tv_mName.setText(mName);
        tv_mPhone.setText(mPhone);
        tv_mAddress.setText(mAddress);
        if(mImage != null && !TextUtils.isEmpty(mImage))
        {
            Picasso.with(this).load(mImage).placeholder(R.drawable.profile_icon).fit().centerCrop().into(imgViewPerson);
        }
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateMechanicRecord(View view) {
        String uName = "";
        String uPhone = "";
        String uAddress = "";

        uName = tv_mName.getText().toString().trim();
        uPhone = tv_mPhone.getText().toString().trim();
        uAddress = tv_mAddress.getText().toString().trim();

        //Validation
        if(TextUtils.isEmpty(uName) || TextUtils.isEmpty(uPhone) || TextUtils.isEmpty(uAddress))
        {
            Message.show(this , "Please fill all the fields!");
            return;
        }

        if(TextUtils.isEmpty(imgUri) || imgUri == null)
        {
            imgUri = mImage;
        }

        final Person mechanics = new Person();
        mechanics.setId(mId);
        mechanics.setName(uName);
        mechanics.setPhone(uPhone);
        mechanics.setAddress(uAddress);
        mechanics.setImage(imgUri);
        final PDialog pd = new PDialog(this).message("Updating record. . .");
        try
        {
            //String uniqueId = String.valueOf(new Date().getTime());
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("mechanics").child(mId);
            dbRef.setValue(mechanics);
            Message.show(this,"Record updated successfully.");
            this.finish();
            Intent int_newActivity = new Intent(this, AllMechanicsActivity.class);
            startActivity(int_newActivity);

        }catch (Exception e)
        {
            pd.hide();
            Message.show(this,"Something went wrong.\n"+e.getMessage());
        }
        pd.hide();
    }

    public void selectImage(View view) {
        bt_update.setVisibility(View.GONE);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // 'i' of "image/*"  must be small letter instead of Image otherwise it cannot pick image
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent , "Select Image") , chooseImageCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == chooseImageCode && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgViewPerson.setImageBitmap(bitmap);

                /*
                ImageUpload imageUpload = new ImageUpload();
                imgUri = imageUpload.uploadImage(this,filePath);
                */
                //final PDialog pd = new PDialog(this).message("Image is uploading. . .");
                if (filePath != null) {
                    Message.show(this,"Please wait...");
                    StorageReference imagesRef = mStorageRef.child("images/" + filePath.getLastPathSegment());
                    UploadTask uploadTask = imagesRef.putFile(filePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //pd.hide();
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            imgUri = taskSnapshot.getDownloadUrl().toString();
                            bt_update.setVisibility(View.VISIBLE);
                            //Message.show(EditMechanic.this,"Image updated!Go for updation...");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // pd.hide();
                                    // Handle unsuccessful uploads
                                    // ...
                                    Message.show(EditMechanic.this, "Failed to image updateing..\n" + exception.getMessage());
                                }
                            });
                    //pd.hide();
                }
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }
}
