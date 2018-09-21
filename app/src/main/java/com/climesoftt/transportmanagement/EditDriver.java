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

public class EditDriver extends AppCompatActivity {
    private TextView tv_dName, tv_dPhone, tv_dAddress;
    private String driverId, dName, dPhone, dAddress, dImage;
    private static final int chooseImageCode = 1;
    private Uri filePath;
    private StorageReference mStorageRef;
    private ImageView imgViewDriver;
    private String imgUri = "";
    private Button bt_update;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_driver);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        tv_dName = findViewById(R.id.dUName);
        tv_dPhone = findViewById(R.id.dUPhone);
        tv_dAddress = findViewById(R.id.dUAddress);
        imgViewDriver = findViewById(R.id.dUimg);
        bt_update = findViewById(R.id.dUBtnUpdate);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            driverId = bundle.getString("DR_ID_KEY");
            dName = bundle.getString("DR_NAME_KEY");
            dPhone = bundle.getString("DR_PHONE_KEY");
            dAddress = bundle.getString("DR_ADDRESS_KEY");
            dImage = bundle.getString("DR_IMAGE_KEY");
        }
        tv_dName.setText(dName);
        tv_dPhone.setText(dPhone);
        tv_dAddress.setText(dAddress);
        if(dImage != null && !TextUtils.isEmpty(dImage))
        {
            Picasso.with(this).load(dImage).placeholder(R.drawable.profile_icon).fit().centerCrop().into(imgViewDriver);
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
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void driverRecordUpdate(View view)
    {
     String uName = "";
     String uPhone = "";
     String uAddress = "";

     uName = tv_dName.getText().toString().trim();
     uPhone = tv_dPhone.getText().toString().trim();
     uAddress = tv_dAddress.getText().toString().trim();

        //Validation
        if(TextUtils.isEmpty(uName) || TextUtils.isEmpty(uPhone) || TextUtils.isEmpty(uAddress))
        {
            Message.show(this , "Please fill all the fields!");
            return;
        }
        if(TextUtils.isEmpty(imgUri) || imgUri == null)
        {
            imgUri = dImage;
        }

        final Person driver = new Person();
        driver.setId(driverId);
        driver.setName(uName);
        driver.setPhone(uPhone);
        driver.setAddress(uAddress);
        driver.setImage(imgUri);
        final PDialog pd = new PDialog(this).message("Updating record. . .");
        try
        {
            //String uniqueId = String.valueOf(new Date().getTime());
            DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference().child("drivers").child(driverId);
            driverRef.setValue(driver);
            Message.show(this,"Record updated successfully.");
            driverId = "";

            this.finish();
            Intent int_newActivity = new Intent(this, AllDriversActivity.class);
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
                imgViewDriver.setImageBitmap(bitmap);

                /*
                ImageUpload imageUpload = new ImageUpload();
                imgUri = imageUpload.uploadImage(this,filePath);
                */
                //final PDialog pd = new PDialog(this).message("Image is uploading. . .");
                if (filePath != null) {
                    Message.show(this,"Please wait ...");
                    final StorageReference imagesRef = mStorageRef.child("images/" + filePath.getLastPathSegment());
                    UploadTask uploadTask = imagesRef.putFile(filePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //pd.hide();
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            imgUri = imagesRef.getDownloadUrl().toString();
                            bt_update.setVisibility(View.VISIBLE);
                            //Message.show(EditDriver.this,"Image updated!Go for updation...");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // pd.hide();
                                    // Handle unsuccessful uploads
                                    // ...
                                    Message.show(EditDriver.this, "Failed to image updating..\n" + exception.getMessage());
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
