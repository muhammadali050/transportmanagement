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
import android.widget.Toast;

import com.climesoftt.transportmanagement.adapter.MechanicsAdaptor;
import com.climesoftt.transportmanagement.adapter.RouteAdaptor;

/**
 * Created by Ali on 3/19/2018.
 */

public class AllRoutes extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_routes);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }

        RecyclerView rv = (RecyclerView)findViewById(R.id.rcvRoutes);
        RouteAdaptor adapter = new RouteAdaptor(this);
        rv.setAdapter(adapter) ;
        rv.setLayoutManager(new LinearLayoutManager(this));


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_route, menu);
        return true;
    }

    public void addRoute(MenuItem item){
        Intent intent = new Intent(this, AddRoute.class);
        startActivity(intent);
    }
}
