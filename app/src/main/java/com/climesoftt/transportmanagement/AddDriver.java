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
import android.widget.EditText;
import android.widget.ImageView;

import com.climesoftt.transportmanagement.utils.GenerateUniqueNumber;
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

import java.io.IOException;

/**
 * Created by Ali on 3/19/2018.
 */

public class AddDriver extends AppCompatActivity {

    private DatabaseReference dbRef;
    private EditText dName, dPhone, dAddress;
    private static final int chooseImageCode = 1;
    private Uri filePath;
    private StorageReference mStorageRef;
    private ImageView imgViewDriver;
    private String imgUriDriver = "";
    private Button bt_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        dbRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        dName = findViewById(R.id.etDName);
        dPhone = findViewById(R.id.etDPhone);
        dAddress = findViewById(R.id.rExtraCost);
        imgViewDriver = findViewById(R.id.imgViewAdd_driver);
        bt_add = findViewById(R.id.btAddDriver);
        bt_add.setVisibility(View.GONE);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {

        }
    }

    public void addDriver(View view) {

        int getId = GenerateUniqueNumber.randomNum();
        String id = Integer.toString(getId).trim();
        String name = dName.getText().toString().trim();
        String phone = dPhone.getText().toString().trim();
        String address = dAddress.getText().toString().trim();
        //Validation
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
            Message.show(AddDriver.this, "Please fill all the fields!");
            return;
        }

        final Person driver = new Person();
        driver.setId(id);
        driver.setName(name);
        driver.setPhone(phone);
        driver.setAddress(address);
        driver.setImage(imgUriDriver);

        Message.show(this, imgUriDriver);
        final PDialog pd = new PDialog(this).message("Person Registration.");
        try {
            //String uniqueId = String.valueOf(new Date().getTime());
            DatabaseReference driverRef = dbRef.child("drivers").child(id);
            driverRef.setValue(driver);
            Message.show(AddDriver.this, "Registered successfully.");
            this.finish();
            Intent intent = new Intent(this, AllDriversActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } catch (Exception e) {
            pd.hide();
            Message.show(AddDriver.this, "Something went wrong.\n" + e.getMessage());
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

    public void selectImage(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // 'i' of "image/*"  must be small letter instead of Image otherwise it cannot pick image
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), chooseImageCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == chooseImageCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgViewDriver.setImageBitmap(bitmap);

                /*ImageUpload imageUpload = new ImageUpload();
                imgUriDriver = imageUpload.uploadImage(this, filePath);*/

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
                            imgUriDriver = taskSnapshot.getDownloadUrl().toString();
                            bt_add.setVisibility(View.VISIBLE);
                            //Message.show(AddDriver.this,"Image uploaded!Go for Registration...");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // pd.hide();
                                    // Handle unsuccessful uploads
                                    // ...
                                    Message.show(AddDriver.this, "Failed image uploading..\n" + exception.getMessage());
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