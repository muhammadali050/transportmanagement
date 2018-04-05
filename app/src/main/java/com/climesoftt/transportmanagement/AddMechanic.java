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

public class AddMechanic extends AppCompatActivity {
    private DatabaseReference dbRef;
    private EditText mName,mEmail, mPhone, mAddress;
    private ImageView imgViewMechanic;
    private Button bt_addMechanic;
    private FirebaseAuth mAuth;

    private static final int chooseImageCode = 1;
    private Uri filePath;
    private StorageReference mStorageRef;
    private String imgUri = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mechanic);
        dbRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mName = findViewById(R.id.etMName);
        mPhone = findViewById(R.id.etMPhone);
        mEmail = findViewById(R.id.mEmail);
        mAddress = findViewById(R.id.mEmail);
        imgViewMechanic = findViewById(R.id.imgViewAdd_mechanic_photo);
        bt_addMechanic = findViewById(R.id.btAddMechanic);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {

        }
    }

    public void addMechanic(View view) {
       // String uniqueId = String.valueOf(new Date().getTime());
        final String id = GenerateUniqueNumber.uniqueId();
        String name = mName.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String address = mAddress.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = "123456";
        //Validation
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
            Message.show(AddMechanic.this, "Please fill all the fields!");
            return;
        }
        //Email Validation
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || TextUtils.isEmpty(email))
        {
            mEmail.setError("Enter valid E-mail!");
            mEmail.requestFocus();
            return;
        }
        final Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setEmail(email);
        person.setPassword(password);
        person.setAccountType("Mechanic");
        person.setPhone(phone);
        person.setAddress(address);
        person.setImage(imgUri);
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            final PDialog pd = new PDialog(AddMechanic.this).message("Mechanic Registration.");

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                // String UId = currentUser.getUid();
                                // Store data in Users
                                DatabaseReference uReF = dbRef.child("Users").child(id);
                                uReF.setValue(person);

                                //Store Data in drivers
                                DatabaseReference pRef = dbRef.child("mechanics").child(id);
                                pRef.setValue(person);
                                pd.hide();
                                //Message.show(AddMechanic.this, "Registered successfully.");
                                currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Message.show(AddMechanic.this,"Verification Email has been sent!" +
                                                "\nVerify himself before login!");
                                    }
                                });
                                AddMechanic.this.finish();
                                Intent intent = new Intent(AddMechanic.this, AllMechanicsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                if(task.getException() instanceof FirebaseAuthUserCollisionException)
                                {
                                    pd.hide();
                                    Message.show(AddMechanic.this, "Provided e-mail address is already registered!");
                                }else
                                {
                                    pd.hide();
                                    Message.show(AddMechanic.this, "Registration failed.\n"+task.getException().getMessage());
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            Message.show(AddMechanic.this, "Something went wrong.\n" + e.getMessage());
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

    public void selectImage(View view) {
        bt_addMechanic.setVisibility(View.GONE);
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
                imgViewMechanic.setImageBitmap(bitmap);

                /*ImageUpload imageUpload = new ImageUpload();
                imgUri = imageUpload.uploadImage(this,filePath);*/
                if(filePath==null)
                {
                    bt_addMechanic.setVisibility(View.VISIBLE);
                }

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
                            bt_addMechanic.setVisibility(View.VISIBLE);
                            //Message.show(AddMechanic.this,"Image uploaded!Go for Registration...");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // pd.hide();
                                    // Handle unsuccessful uploads
                                    // ...
                                    Message.show(AddMechanic.this, "Failed image uploading..\n" + exception.getMessage());
                                }
                            });
                    //pd.hide();
                }

            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(this, AllMechanicsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
