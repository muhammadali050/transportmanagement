package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.climesoftt.transportmanagement.model.Faq;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditQuestionActivity extends AppCompatActivity {

    private EditText etQuestion, etAnswer;
    private String qId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        etQuestion = findViewById(R.id.faqEditQuestion);
        etAnswer = findViewById(R.id.faqEditAnswer);

        String question = "";
        String answer = "";
        Intent intent = getIntent();
        if(intent!=null)
        {
            qId = intent.getStringExtra("QID");
            question = intent.getStringExtra("QUESTION");
            answer = intent.getStringExtra("ANSWER");
        }
        etQuestion.setText(question);
        etAnswer.setText(answer);
    }


    public void updateQuestion(View view) {
        String question = etQuestion.getText().toString().trim();
        String answer = etAnswer.getText().toString().trim();
        //Validation
        if(TextUtils.isEmpty(question) || TextUtils.isEmpty(answer))
        {
            Message.show(this , "Please fill all the fields!");
            return;
        }

        final Faq faq = new Faq();
        faq.setId(qId);
        faq.setQuestion(question);
        faq.setAnswer(answer);

        final PDialog pd = new PDialog(this).message("Updating. . .");
        try
        {
            //String uniqueId = String.valueOf(new Date().getTime());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Faq").child(qId);
            ref.setValue(faq);
            Message.show(this,"Updated successfully!");
            Intent intent = new Intent(this, DriverAndPersonalFaq.class);
            startActivity(intent);
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
