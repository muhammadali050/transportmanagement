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
import com.climesoftt.transportmanagement.model.Faq;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DriverFaq extends AppCompatActivity {

    private RecyclerView rvFaq;
    private DatabaseReference faqRef;
    private FaqAdapter faqAdapter;
    private ArrayList<Faq> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);


        rvFaq = findViewById(R.id.rcvFaq);
        faqAdapter = new FaqAdapter(this , arrayList);
        rvFaq.setAdapter(faqAdapter);
        rvFaq.setLayoutManager(new LinearLayoutManager(this));
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase()
    {
        faqRef = FirebaseDatabase.getInstance().getReference("Faq");
        final PDialog pd = new PDialog(this).message("Loading. . .");
        faqRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot driverSnapshot : dataSnapshot.getChildren())
                {
                    Faq pData = driverSnapshot.getValue(Faq.class);
                    arrayList.add(pData);
                }
                faqAdapter.notifyDataSetChanged();
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
        inflater.inflate(R.menu.menu_add_faq, menu);
        return true;
    }

    public void addQuestion(MenuItem item){
        Intent intent = new Intent(this, AddQuestionActivity.class);
        startActivity(intent);
    }


}
