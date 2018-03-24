package com.climesoftt.transportmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.climesoftt.transportmanagement.model.User;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ali on 3/14/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etl_email,etl_password;
    private String email,password;
    private String loginUserName = "";
    private String loginUserType = "";
    private String loginUserEmail = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }

        mAuth = FirebaseAuth.getInstance();
        etl_email = findViewById(R.id.etUserEmail);
        etl_password = findViewById(R.id.etUserPassword);
    }

    public void onClickLogin(View view){
        Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
/*
        email = etl_email.getText().toString();
        password = etl_password.getText().toString();

        //Email Validation
        if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etl_email.setError("Enter Valid Email!");
            etl_email.requestFocus();
            return;
        }
        //Password Validation
        if(TextUtils.isEmpty(password))
        {
            etl_password.setError("Enter Valid Password!");
            etl_password.requestFocus();
            return;
        }
        final PDialog pd = new PDialog(this).message("Trying to login . . .");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Sign in success, update UI with the signed-in user's information

                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                moveLoginUserHisDashboard();
                                // Check if user's email is verified
                                /*if (user.isEmailVerified()) {
                                    moveLoginUserHisDashboard();

                            }else
                            {
                                pd.hide();
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Message.show(LoginActivity.this, "Email Not Verified!" +
                                                "\nNow again Verification Email has been sent!" +
                                                "\nPlease Verify himself!");
                                    }
                                });
                            }
                            //updateUI(user);
                             pd.hide();
                        } else {
                                pd.hide();
                            // If sign in fails, display a message to the user.
                            Message.show(LoginActivity.this, "Authentication failed.\n"+task.getException().getMessage());
                        }
                        // ...
                    }
                });
        pd.hide();
        */
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
         FirebaseUser user = mAuth.getCurrentUser();


    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    private void moveLoginUserHisDashboard()
    {
        final PDialog pd = new PDialog(this).message("Trying to login . . .");

        try
        {
            loginUserName = "";
            loginUserType = "";
            loginUserEmail = "";
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren())
                    {
                        User user = userSnapshot.getValue(User.class);
                        if(email.equals(user.getEmail()))
                        {
                            //Move to Admin Dashboard
                            if(user.getAccountType().equals("Admin"))
                            {
                                loginUserName = user.getName();
                                loginUserType = user.getAccountType();
                                loginUserEmail = user.getEmail();
                                pd.hide();
                                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("USER_NAME" , loginUserName);
                                intent.putExtra("USER_TYPE" , loginUserType);
                                intent.putExtra("USER_EMAIL" , loginUserEmail);
                                startActivity(intent);
                            }else if(user.getAccountType().equals("Driver"))
                            {
                                loginUserName = user.getName();
                                loginUserType = user.getAccountType();
                                loginUserEmail = user.getEmail();
                                pd.hide();
                                Intent intent = new Intent(LoginActivity.this, DriverDashboard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("USER_NAME" , loginUserName);
                                intent.putExtra("USER_TYPE" , loginUserType);
                                intent.putExtra("USER_EMAIL" , loginUserEmail);
                                startActivity(intent);
                            }else if(user.getAccountType().equals("Personal"))
                            {

                            }else
                            {
                                Message.show(LoginActivity.this, "User does not exist!\nFirst Register himself!\n Regards : Admin \nThanks.");
                            }
                        }
                    }
                    pd.hide();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }catch (Exception e)
        {
            Message.show(LoginActivity.this,"Something went wrong.\n"+e.getMessage());
        }
    }
}
