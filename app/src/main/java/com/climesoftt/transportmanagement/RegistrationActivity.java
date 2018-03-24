package com.climesoftt.transportmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.climesoftt.transportmanagement.utils.GenerateRandomNumber;
import com.climesoftt.transportmanagement.model.User;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.view.MenuItem;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progressDialog = new ProgressDialog(this);

        userName = findViewById(R.id.uName);
        userEmail = findViewById(R.id.uEmail);
        userPassword = findViewById(R.id.uPassword);
        spAccountType = findViewById(R.id.spinnerAccountType);

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
                this.finish();;
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
        String accountType = spAccountType.getSelectedItem().toString().trim();
        //Email Validation
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            userEmail.setError("Enter valid E-mail!");
            userEmail.requestFocus();
            return;
        }
        //All Fields must be fill
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || accountType.isEmpty())
        {
            Message.show(RegistrationActivity.this , "Please fill all fields.");
            return;
        }
        final User user = new User();
        //final String uniqueId = String.valueOf(new Date().getTime());
        int getId = GenerateRandomNumber.randomNum();
        final String id = Integer.toString(getId);
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAccountType(accountType);

        final PDialog pd = new PDialog(this).message("User Registration. . .");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            DatabaseReference uReF = dbRef.child("Users").child(id);
                            uReF.setValue(user);

                            userName.setText("");
                            userEmail.setText("");
                            userPassword.setText("");
                            pd.hide();

                        } else {
                            // If sign in fails, display a message to the user.
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Message.show(RegistrationActivity.this, "Provided e-mail address is already registered!");
                            }else
                            {
                             Message.show(RegistrationActivity.this, "Registration failed.\n"+task.getException().getMessage());
                            }
                        }

                        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Message.show(RegistrationActivity.this,"Verification Email has been sent!" +
                                        "\nPlease Register himself before login!");
                            }
                        });
                    }
                });

    }
}
