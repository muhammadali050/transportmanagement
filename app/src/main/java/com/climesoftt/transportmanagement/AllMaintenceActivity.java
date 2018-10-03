package com.climesoftt.transportmanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.climesoftt.transportmanagement.adapter.MaintenenceAdapter;
import com.climesoftt.transportmanagement.model.Maintenance;
import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.AccountManager;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllMaintenceActivity extends AppCompatActivity {

    private ArrayList<Maintenance> arrayList = new ArrayList<>();
    private MaintenenceAdapter maintenenceAdapter;
    private RecyclerView  rv;
    private DatabaseReference mref;
    private String USER_EMAIL = "";
    private String USER_TYPE = "";
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_maintenece);

        accountManager = new AccountManager(this);
        USER_TYPE = accountManager.getUserAccountType();
        USER_EMAIL = accountManager.getUserEmail();

        rv = (RecyclerView)findViewById(R.id.rcvMaintenece);
        maintenenceAdapter= new MaintenenceAdapter(this,arrayList);
        rv.setAdapter(maintenenceAdapter) ;
        rv.setLayoutManager(new LinearLayoutManager(this));
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase()
    {
        mref = FirebaseDatabase.getInstance().getReference("Maintenance");
        final PDialog pd = new PDialog(this).message("Loading. . .");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot mSnapshot : dataSnapshot.getChildren())
                {
                    Maintenance mData = mSnapshot.getValue(Maintenance.class);
                    if(USER_TYPE.equals("Admin"))
                    {
                        if(mData.getUserType().equals("Driver") || mData.getUserType().equals("Admin"))
                        {
                            arrayList.add(mData);
                        }
                    }
                    if(mData.getUserType().equals("Driver") && USER_TYPE.equals("Driver"))
                    {
                        if(USER_EMAIL.equals(mData.getEmail()))
                        {
                            arrayList.add(mData);
                        }
                    }

                    if(USER_TYPE.equals("Personal"))
                    {
                        if(mData.getUserType().equals("Personal") && USER_EMAIL.equals(mData.getEmail()))
                        {
                            arrayList.add(mData);
                        }
                    }
                }
                maintenenceAdapter.notifyDataSetChanged();
                pd.hide();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.hide();
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_all_maintenence, menu);
        return true;
    }

    public void addMaintenence(MenuItem item){
        Intent intent = new Intent(this, AddMaintenenceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
