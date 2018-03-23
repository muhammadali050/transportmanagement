package com.climesoftt.transportmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.climesoftt.transportmanagement.adapter.MaintenenceAdapter;

public class AllMainteneceActivity extends AppCompatActivity {

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
}
