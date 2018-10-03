package com.climesoftt.transportmanagement.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.climesoftt.transportmanagement.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteUserAccount {
    public void deleteUser(final Context context)
    {
        new android.support.v7.app.AlertDialog.Builder(context)
                    .setTitle("Delete Account Really?")
                    .setMessage("Permanently Delete Your Account Are You Sure?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                context.startActivity(new Intent(context, MainActivity.class));
                                            }
                                        }
                                    });
                        }
                    }).create().show();


    }
}
