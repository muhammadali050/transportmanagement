package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.climesoftt.transportmanagement.adapter.FaqAdapter;

public class DriverFaq extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        RecyclerView rv = (RecyclerView)findViewById(R.id.rcvFaq);
        FaqAdapter adapter = new FaqAdapter(this);
        rv.setAdapter(adapter) ;
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_faq, menu);
        return true;
    }

    public void addQuestion(MenuItem item){
        Intent intent = new Intent(this, AddQuestionActivity.class);
        startActivity(intent);


    }


}
