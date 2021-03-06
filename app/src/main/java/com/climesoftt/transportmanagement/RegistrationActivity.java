package com.climesoftt.transportmanagement;

import android.app.ProgressDialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.climesoftt.transportmanagement.utils.GenerateUniqueNumber;
import com.climesoftt.transportmanagement.model.User;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.android.gms.tasks.Continuation;
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

import android.view.MenuItem;

import java.io.IOException;

/**
 * Created by Ali on 3/14/2018.
 */

public class RegistrationActivity extends AppCompatActivity {
    private EditText userName,userEmail,userPassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private ProgressDialog progressDialog;
    private Spinner spAccountType;
    private Button bt_add;

    private static final int chooseImageCode = 1;
    private Uri filePath;
    private StorageReference mStorageRef;
    private ImageView imgViewUser;
    private String imgUrl = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        GenerateUniqueNumber.uniqueId();
        progressDialog = new ProgressDialog(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        imgViewUser = findViewById(R.id.imgViewAdd_photo);
        userName = findViewById(R.id.uName);
        userEmail = findViewById(R.id.uEmail);
        userPassword = findViewById(R.id.uPassword);
        //spAccountType = findViewById(R.id.spinnerAccountType);
        bt_add = findViewById(R.id.bt_add);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();


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

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void userRegister(View view)
    {
        String name = userName.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        //String accountType = spAccountType.getSelectedItem().toString().trim();
        //Call Function for validation performs
        fieldsValidation(name, email, password);

        final User user = new User();
        //final String uniqueId = String.valueOf(new Date().getTime());
        final String id = GenerateUniqueNumber.uniqueId();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAccountType("Personal");
        user.setUserImage(imgUrl);
        //Message.show(this,imgUrl);
        final PDialog pd = new PDialog(this).message("User Registration. . .");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String UId = currentUser.getUid();
                            user.setUId(UId);

                            DatabaseReference uReF = dbRef.child("Users").child(id);
                            uReF.setValue(user);
                            currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Message.show(RegistrationActivity.this,"Verification Email has been sent!" +
                                            "\nPlease Register himself before login!");

                                }
                            });
                            userName.setText("");
                            userEmail.setText("");
                            userPassword.setText("");
                            imgViewUser.setImageResource(R.drawable.add_image_icon);
                            userName.requestFocus();
                            pd.hide();

                            //for logout first user enter his / her credentials
                            FirebaseAuth.getInstance().signOut();

                            RegistrationActivity.this.finish();
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                pd.hide();
                                Message.show(RegistrationActivity.this, "Provided e-mail address is already registered!");
                            }else
                            {
                                pd.hide();
                             Message.show(RegistrationActivity.this, "Registration failed.\n"+task.getException().getMessage());
                            }
                        }

                    }
                });



    }

    private void fieldsValidation(String name, String email, String password)
    {
        //Name Validation
        if(TextUtils.isEmpty(name))
        {
            userName.setError("Enter Name!");
            userName.requestFocus();
            return;
        }
        //Email Validation
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || TextUtils.isEmpty(email))
        {
            userEmail.setError("Enter valid E-mail!");
            userEmail.requestFocus();
            return;
        }
        //Password Validation
        if(TextUtils.isEmpty(password))
        {
            userPassword.setError("Enter Valid Password!\nPassword length atleast 6-digits|characters|symbols");
            userPassword.requestFocus();
            return;
        }
    }

    public void selectImage(View view) {
        bt_add.setVisibility(View.GONE);
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
                imgViewUser.setImageBitmap(bitmap);
                if(filePath==null)
                {
                    bt_add.setVisibility(View.VISIBLE);
                }
                if (filePath != null) {
                    Message.show(this,"Please wait...");
                    final StorageReference imagesRef = mStorageRef.child("images/" + filePath.getLastPathSegment());
                    final UploadTask uploadTask = imagesRef.putFile(filePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //pd.hide();
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            imgUrl = taskSnapshot.getDownloadUrl().toString();

                            bt_add.setVisibility(View.VISIBLE);
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // pd.hide();
                                    // Handle unsuccessful uploads
                                    // ...
                                    Message.show(RegistrationActivity.this, "Failed image uploading..\n" + exception.getMessage());
                                    bt_add.setVisibility(View.VISIBLE);
                                }
                            });
                }
            }
            catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }
}
