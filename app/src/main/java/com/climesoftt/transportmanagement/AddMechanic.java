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
    private EditText mName, mPhone, mAddress;
    private ImageView imgViewMechanic;
    private Button bt_addMechanic;

    private static final int chooseImageCode = 1;
    private Uri filePath;
    private StorageReference mStorageRef;
    private String imgUri = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mechanic);
        //Get Mechanic Id
        GenerateUniqueNumber.mechanicId();
        dbRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mName = findViewById(R.id.etMName);
        mPhone = findViewById(R.id.etMPhone);
        mAddress = findViewById(R.id.etMAddress);
        imgViewMechanic = findViewById(R.id.imgViewAdd_mechanic_photo);
        bt_addMechanic = findViewById(R.id.btAddMechanic);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {

        }
    }

    public void addMechanic(View view) {

        final String id = GenerateUniqueNumber.mechanicId();
        String name = mName.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String address = mAddress.getText().toString().trim();
        //Validation
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
            Message.show(AddMechanic.this, "Please fill all the fields!");
            return;
        }

        final Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setAccountType("Mechanic");
        person.setPhone(phone);
        person.setAddress(address);
        person.setImage(imgUri);
        final PDialog pd = new PDialog(this).message("Mechanic Registration.");
        try {
            DatabaseReference personRef = dbRef.child("mechanics").child(id);
            personRef.setValue(person);
            Message.show(AddMechanic.this, "Registered successfully.");

            this.finish();
            Intent intent = new Intent(this, AllMechanicsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } catch (Exception e) {
            pd.hide();
            Message.show(AddMechanic.this, "Something went wrong.\n" + e.getMessage());
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
                    final StorageReference imagesRef = mStorageRef.child("images/" + filePath.getLastPathSegment());
                    UploadTask uploadTask = imagesRef.putFile(filePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //pd.hide();
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            imgUri = imagesRef.getDownloadUrl().toString();
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
