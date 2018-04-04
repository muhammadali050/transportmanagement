package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.climesoftt.transportmanagement.model.Faq;
import com.climesoftt.transportmanagement.utils.GenerateUniqueNumber;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText etQuestn, etAns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        etQuestn = findViewById(R.id.etQuestion);
        etAns = findViewById(R.id.etAnswer);
    }

    public void addQuestion(View view)
    {
        addQuestion();
    }

    public void addQuestion()
    {
        int getId = GenerateUniqueNumber.randomNum();
        String id = Integer.toString(getId).trim();
        String question = etQuestn.getText().toString().trim();
        String answer = etAns.getText().toString().trim();
        //Validation
        if(TextUtils.isEmpty(question) || TextUtils.isEmpty(answer))
        {
            Message.show(this , "Please fill all the fields!");
            return;
        }

        final Faq faq = new Faq();
        faq.setId(id);
        faq.setQuestion(question);
        faq.setAnswer(answer);

        final PDialog pd = new PDialog(this).message("Adding. . .");
        try
        {
            //String uniqueId = String.valueOf(new Date().getTime());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Faq").child(id);
            ref.setValue(faq);
            Message.show(this,"Submitted successfully!");
            this.finish();
            Intent int_newActivity = new Intent(this, DriverAndPersonalFaq.class);
            startActivity(int_newActivity);
        }catch (Exception e)
        {
            pd.hide();
            Message.show(this,"Something went wrong.\n"+e.getMessage());
        }
        pd.hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent int_newActivity = new Intent(this, DriverAndPersonalFaq.class);
        startActivity(int_newActivity);
    }
}
