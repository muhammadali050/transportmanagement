package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.climesoftt.transportmanagement.adapter.RouteAdaptor;
import com.climesoftt.transportmanagement.model.Routes;
import com.climesoftt.transportmanagement.model.User;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Ali on 3/19/2018.
 */

public class AllRoutes extends AppCompatActivity {
    private ArrayList<Routes> arrayList = new ArrayList<>();
    private RecyclerView rvRoutes;
    private RouteAdaptor adapter;
    private DatabaseReference dbref;
    private DatabaseReference adminRef;
    private String userTypeAdmin = "";
    private String userTypeDriver = "";
    private String driverName = "";
    private String adminEmail = "";
    //public static String CURRENT_CHILD_KEY;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_routes);

        userTypeDriver = DriverDashboard.USER_TYPE;
        driverName = DriverDashboard.USERNAME;

        userTypeAdmin = AdminDashboardActivity.USER_TYPE;
        adminEmail = AdminDashboardActivity.USER_EMAIL;

       // Message.show(this, userTypeDriver +" ," +driverName+ " --- "+userTypeAdmin +" , "+adminEmail);
        rvRoutes = (RecyclerView)findViewById(R.id.rcvRoutes);
        adapter = new RouteAdaptor(this , arrayList);
        rvRoutes.setAdapter(adapter) ;
        rvRoutes.setLayoutManager(new LinearLayoutManager(this));

        //call function for fetch data from firebase
        fetchDataFromFirebase();

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    //Fetch data
    private void fetchDataFromFirebase()
    {
        dbref = FirebaseDatabase.getInstance().getReference("Routes");
        final PDialog pd = new PDialog(AllRoutes.this).message("Loading. . .");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot routesSnapshot : dataSnapshot.getChildren()) {
                    Routes data = routesSnapshot.getValue(Routes.class);
                    if (userTypeAdmin.equals("Admin")) {
                        arrayList.add(data);
                        adminRef = FirebaseDatabase.getInstance().getReference().child("Users");

                    } else if (userTypeDriver.equals("Driver")) {
                        if (driverName.equals(data.getDriver())) {
                            arrayList.add(data);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                pd.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_route, menu);
        return true;
    }

    public void addRoute(MenuItem item){
        //this.finish();
        Intent intent = new Intent(this, AddRoute.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                //Intent intent = new Intent(this, AdminDashboardActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
