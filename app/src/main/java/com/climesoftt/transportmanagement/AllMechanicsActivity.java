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
import android.view.View;
import android.widget.Toast;

import com.climesoftt.transportmanagement.adapter.MechanicsAdaptor;

/**
 * Created by Ali on 3/14/2018.
 */

public class AllMechanicsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_mechanics);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }

        RecyclerView  rv = (RecyclerView)findViewById(R.id.rcvMechanics);
        MechanicsAdaptor adapter = new MechanicsAdaptor(this);
        rv.setAdapter(adapter) ;
        rv.setLayoutManager(new LinearLayoutManager(this));


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_user, menu);
        return true;
    }

    public void addUser(MenuItem item){
        Intent intent = new Intent(this, AddMechanic.class);
        startActivity(intent);
    }
}
