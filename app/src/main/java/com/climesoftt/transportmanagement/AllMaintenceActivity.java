package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.climesoftt.transportmanagement.adapter.MaintenenceAdapter;

public class AllMaintenceActivity extends AppCompatActivity {

    private MaintenenceAdapter maintenenceAdapter;
    private RecyclerView  rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_maintenece);

        rv = (RecyclerView)findViewById(R.id.rcvMaintenece);
        maintenenceAdapter= new MaintenenceAdapter(this);
        rv.setAdapter(maintenenceAdapter) ;
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_all_maintenence, menu);
        return true;
    }

    public void addMaintenence(MenuItem item){
        Intent intent = new Intent(this, AddMaintenenceActivity.class);
        startActivity(intent);
    }


}
