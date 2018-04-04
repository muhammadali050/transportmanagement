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
import com.climesoftt.transportmanagement.utils.AccountManager;
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
    private String USER_TYPE = "";
    private String USER_NAME = "";
    private String USER_EMAIL = "";
    private AccountManager accountManager;
    //public static String CURRENT_CHILD_KEY;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_routes);

        rvRoutes = (RecyclerView)findViewById(R.id.rcvRoutes);
        adapter = new RouteAdaptor(this , arrayList);
        rvRoutes.setAdapter(adapter) ;
        rvRoutes.setLayoutManager(new LinearLayoutManager(this));

        //call function for fetch data from firebase
        accountManager = new AccountManager(this);
        USER_NAME = accountManager.getUserName();
        USER_TYPE = accountManager.getUserAccountType();
        Message.show(this,USER_TYPE);
        //call function for fetch data from firebase
        if(USER_TYPE.equals("Driver"))
        {
            fetchDataFromFirebase("Routes");
        }
        else if(USER_TYPE.equals("Admin"))
        {
            fetchDataFromFirebase("Routes");
        }
        else if(USER_TYPE.equals("Personal"))
        {
            fetchDataFromFirebase("Personal");
        }
        else {
            return;
        }
        //fetchDataFromFirebase("Personal");

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){
        }
    }

    //Fetch data
    private void fetchDataFromFirebase(String reference)
    {
        final PDialog pd = new PDialog(AllRoutes.this).message("Loading. . .");
        dbref = FirebaseDatabase.getInstance().getReference(reference);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot routesSnapshot : dataSnapshot.getChildren()) {
                    Routes data = routesSnapshot.getValue(Routes.class);
                    if(USER_TYPE.equals("Admin"))
                    {
                        arrayList.add(data);
                    }
                    else if(USER_TYPE.equals("Driver"))
                    {
                        if(USER_NAME.equals(data.getDriver()))
                        {
                            arrayList.add(data);
                        }else {
                            pd.hide();
                            return;
                        }
                    }
                    else if(USER_TYPE.equals("Personal"))
                    {
                        if(USER_NAME.equals(data.getPersonName()))
                        {
                            arrayList.add(data);
                        }else {
                            pd.hide();
                            return;
                        }
                    } else {
                        pd.hide();
                        return;
                    }
                }
                adapter.notifyDataSetChanged();
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
        inflater.inflate(R.menu.menu_add_route, menu);
        MenuItem item = menu.findItem(R.id.add_route);
        if(USER_TYPE.equals("Personal") || USER_TYPE.equals("Admin"))
        {
            item.setVisible(true);
        }else
        {
            item.setVisible(false);
        }
        return true;
    }

    public void addRoute(MenuItem item){
        this.finish();
        Intent intent = new Intent(this, AddRoute.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                rvRoutes.notifyAll();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
