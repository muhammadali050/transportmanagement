package com.climesoftt.transportmanagement;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.climesoftt.transportmanagement.utils.AccountManager;
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

import kotlin.jvm.internal.PackageReference;

/**
 * Created by Ali on 3/14/2018.
 */

public class LoginActivity extends AppCompatActivity {

    public static FirebaseAuth mAuth;
    private EditText etl_email,etl_password;
    private String email,password;
    private String loginUserName = "";
    private String loginUserType = "";
    private String loginUserEmail = "";
    private String loginUserImage = "";
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


    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public void onClickLogin(View view){
        /*Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/

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
                                pd.hide();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                //moveLoginUserHisDashboard(email);
                                // Check if user's email is verified
                                if (user.isEmailVerified()) {
                                    moveLoginUserHisDashboard(email);

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
                        } else {
                                pd.hide();
                            // If sign in fails, display a message to the user.
                            Message.show(LoginActivity.this, "Authentication failed.\n"+task.getException().getMessage());
                            }
                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        final PDialog pd = new PDialog(this).message("logging . . .");

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null)
        {
            pd.hide();
            return;
        }
        else if(user!=null)
        {
            String email = user.getEmail();
            moveLoginUserHisDashboard(email);
            pd.hide();
        }else
        {
            pd.hide();
           return;

        }
        pd.hide();
    }

    public void onClickRegisterPersonal(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickRegisterDriver(View view) {
        Intent intent = new Intent(this, AddDriver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               this.finish();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void moveLoginUserHisDashboard(final String userEmail)
    {
        final AccountManager accountManager = new AccountManager(this);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        try
        {

            loginUserName = "";
            loginUserType = "";
            loginUserEmail = "";

            final PDialog pd = new PDialog(this).message("Trying to login . . .");
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren())
                    {
                        User user = userSnapshot.getValue(User.class);
                        if(userEmail.equals(user.getEmail()))
                        {
                            loginUserName = user.getName();
                            loginUserType = user.getAccountType();
                            loginUserEmail = user.getEmail();
                            loginUserImage = user.getUserImage();
                            //Saved user credentials in sharedPreference
                            accountManager.saveUserCredentials(loginUserName, loginUserType, loginUserEmail, loginUserImage);
                            //Move to Admin Dashboard
                            if(user.getAccountType().equals("Admin"))
                            {
                                pd.hide();
                                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }else if(user.getAccountType().equals("Driver"))
                            {
                                pd.hide();
                                Intent intent = new Intent(LoginActivity.this, DriverDashboard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }else if(user.getAccountType().equals("Personal"))
                            {
                                pd.hide();
                                Intent intent = new Intent(LoginActivity.this, Personal.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }else
                            {
                                pd.hide();
                                Message.show(LoginActivity.this, "User does not exist!\nFirst Register himself!\n Regards : Admin \nThanks.");
                            }
                        }else
                        {
                            pd.hide();
                        }
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    pd.hide();
                    return;
                }

            });

        }catch (Exception e)
        {
            Message.show(LoginActivity.this,"Something went wrong.\n"+e.getMessage());
        }
    }


}
