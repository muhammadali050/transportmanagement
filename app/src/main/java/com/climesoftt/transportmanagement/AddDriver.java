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
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.climesoftt.transportmanagement.utils.GenerateUniqueNumber;
import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
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

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private EditText dName,dMail, dPassword, dPhone, dAddress;
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

        GenerateUniqueNumber.uniqueId();

        dbRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        dName = findViewById(R.id.etDName);
        dPhone = findViewById(R.id.etDPhone);
        dMail = findViewById(R.id.etDEmail);
        dPassword = findViewById(R.id.etDPass);
        dAddress = findViewById(R.id.mEmail);
        imgViewDriver = findViewById(R.id.imgViewAdd_driver);
        bt_add = findViewById(R.id.btAddDriver);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {

        }
    }

    public void addDriver(View view) {

        final String id = GenerateUniqueNumber.uniqueId();
        String name = dName.getText().toString().trim();
        String phone = dPhone.getText().toString().trim();
        String address = dAddress.getText().toString().trim();
        String email = dMail.getText().toString().trim();
        String password = dPassword.getText().toString().trim();
        //Validation
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)
                || TextUtils.isEmpty(password)) {
            Message.show(AddDriver.this, "Please fill all the fields!");
            return;
        }
        //Email Validation
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || TextUtils.isEmpty(email))
        {
            dMail.setError("Enter valid E-mail!");
            dMail.requestFocus();
            return;
        }
        final Person driver = new Person();
        driver.setId(id);
        driver.setEmail(email);
        driver.setPassword(password);
        driver.setName(name);
        driver.setPhone(phone);
        driver.setAccountType("Driver");
        driver.setAddress(address);
        driver.setImage(imgUriDriver);
        final PDialog pd = new PDialog(this).message("Driver Registration. . .");
         mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // String UId = currentUser.getUid();
                            // Store data in Users
                            DatabaseReference uReF = dbRef.child("Users").child(id);
                            uReF.setValue(driver);

                            //Store Data in drivers
                            DatabaseReference driverRef = dbRef.child("drivers").child(id);
                            driverRef.setValue(driver);

                           // Message.show(AddDriver.this, "Registered successfully.");
                            currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Message.show(AddDriver.this,"Verification Email has been sent!" +
                                            "\nVerify himself before login!");
                                    pd.hide();
                                    AddDriver.this.finish();
                                    Intent intent = new Intent(AddDriver.this, DriverDashboard.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                pd.hide();
                                Message.show(AddDriver.this, "Provided e-mail address is already registered!");
                            }else
                            {
                                pd.hide();
                                Message.show(AddDriver.this, "Registration failed.\n"+task.getException().getMessage());
                            }
                        }
                    }
                });

        /*
        //Message.show(this, imgUriDriver);
        final PDialog pd = new PDialog(this).message("Person Registration.");
        try {
            DatabaseReference myRef = dbRef.child("Users").child(id);

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
        } */
       // pd.hide();
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
        bt_add.setVisibility(View.GONE);
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
                if(filePath==null)
                {
                    bt_add.setVisibility(View.VISIBLE);
                }
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
                }
            } catch (IOException e) {
                //e.printStackTrace();
            }

        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}