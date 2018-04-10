package com.climesoftt.transportmanagement.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.climesoftt.transportmanagement.R;

/**
 * Created by Ali on 4/10/2018.
 */

public class FaqViewManager {
    private Context context;
    private static FaqViewManager faqViewManager =null ;
    private boolean check = false;

    TextView question = null;
    TextView answer = null;
    LinearLayout layout = null;


    private FaqViewManager(Context context){
        this.context = context;
    }

    public static FaqViewManager getInstance(Context context){
        if(faqViewManager == null){
            faqViewManager = new FaqViewManager(context);
            return faqViewManager;
        }
        else{
            return faqViewManager;
        }
    }

    public void closePrevious(){
        answer.setVisibility(View.INVISIBLE);
        android.view.ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = question.getHeight()+4; // Add margin Value too
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layout.setLayoutParams(params);
        question.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_up, 0);
    }

    public void setPreviousValues(View view){
        question = (TextView)view.findViewById(R.id.txt_question);
        answer = (TextView)view.findViewById(R.id.txt_answer);
        layout = (LinearLayout) view.findViewById(R.id.llFaq);
    }

    public boolean isClicked(){
        if(question == null){
            return false;
        }
        else{
            return true;
        }
    }
}
