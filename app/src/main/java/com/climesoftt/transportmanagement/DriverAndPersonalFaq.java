package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.climesoftt.transportmanagement.adapter.FaqAdapter;
import com.climesoftt.transportmanagement.model.Faq;
import com.climesoftt.transportmanagement.utils.AccountManager;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.MoveUserToDashboard;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DriverAndPersonalFaq extends AppCompatActivity {

    private AccountManager accountManager;
    private RecyclerView rvFaq;
    private DatabaseReference faqRef;
    public static FaqAdapter faqAdapter;
    private ArrayList<Faq> arrayList = new ArrayList<>();
    private String USER_TYPE = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        accountManager = new AccountManager(this);
        USER_TYPE = accountManager.getUserAccountType();
        //Message.show(this, USER_TYPE);
        rvFaq = findViewById(R.id.rcvFaq);
        faqAdapter = new FaqAdapter(this , arrayList);
        rvFaq.setAdapter(faqAdapter);
        rvFaq.setLayoutManager(new LinearLayoutManager(this));
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase()
    {
        faqRef = FirebaseDatabase.getInstance().getReference().child("Faq");
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
        //this.menu = menu;
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_add_faq, menu);
            MenuItem faqIcon = menu.findItem(R.id.ic_add_faq);
            if(USER_TYPE.equals("Admin"))
            {
                faqIcon.setVisible(true);
            }else
            {
                faqIcon.setVisible(false);
            }
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void addQuestion(MenuItem item){
        Intent intent = new Intent(this, AddQuestionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
