package com.climesoftt.transportmanagement.adapter;

/**
 * Created by Ali on 3/22/2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.climesoftt.transportmanagement.DriverAndPersonalFaq;
import com.climesoftt.transportmanagement.EditQuestionActivity;
import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.model.Faq;
import com.climesoftt.transportmanagement.utils.DeleteRecord;
import com.climesoftt.transportmanagement.utils.FaqViewManager;

import java.util.ArrayList;

/**
 * Created by Ali on 3/21/2018.
 */
public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.VHolder> {
    private Context context;
    private ArrayList<Faq> arrayList;
    public FaqAdapter(Context context, ArrayList<Faq> arrayList ){
        this.context = context;
        this.arrayList = arrayList;
    }

    public FaqAdapter.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.faq_item, viewGroup , false);
        return new FaqAdapter.VHolder(view);

    }


    public int getItemCount(){
        return arrayList.size();
    }


    public void onBindViewHolder(FaqAdapter.VHolder vh , int position){
        vh.question.setText(arrayList.get(position).getQuestion());
        vh.answer.setText(arrayList.get(position).getAnswer());

    }

    class VHolder extends RecyclerView.ViewHolder{
        private TextView question;
        private TextView answer;
        private LinearLayout layout;

        public VHolder(View view)
        {
            super(view);
            question = (TextView)view.findViewById(R.id.txt_question);
            answer = (TextView)view.findViewById(R.id.txt_answer);
            layout = (LinearLayout) view.findViewById(R.id.llFaq);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    answer.setVisibility(View.VISIBLE);
                    android.view.ViewGroup.LayoutParams params = layout.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layout.setLayoutParams(params);
                    question.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down, 0);

                    if(FaqViewManager.getInstance(context).isClicked()){
                        FaqViewManager.getInstance(context).closePrevious();
                    }
                    FaqViewManager.getInstance(context).setPreviousValues(v);


                    /*if(answer.isShown()){
                        *//*answer.setVisibility(View.INVISIBLE);
                        android.view.ViewGroup.LayoutParams params = layout.getLayoutParams();
                        params.height = question.getHeight()+4; // Add margin Value too
                        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layout.setLayoutParams(params);
                        question.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_up, 0);*//*
                    }
                    else{
                        answer.setVisibility(View.VISIBLE);
                        android.view.ViewGroup.LayoutParams params = layout.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layout.setLayoutParams(params);
                        question.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down, 0);
                    }*/
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                String qId = "";
                String question = "";
                String answer = "";


                @Override
                public boolean onLongClick(View v) {
                    // get position of current Row
                    final int pos = getAdapterPosition();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.create();
                    alertDialogBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // check if item still exists
                            if(pos != RecyclerView.NO_POSITION){
                                Faq clickedDataItem = arrayList.get(pos);
                                qId = clickedDataItem.getId();
                            }
                            DeleteRecord.deleteRecordMethod(context , "Faq" , qId);
                            Intent intent = new Intent(context, DriverAndPersonalFaq.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                    });

                    alertDialogBuilder.setPositiveButton("Edit",new
                            DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // check if item still exists
                                    if(pos != RecyclerView.NO_POSITION){
                                        Faq clickedDataItem = arrayList.get(pos);
                                        qId = clickedDataItem.getId();
                                        question= clickedDataItem.getQuestion();
                                        answer= clickedDataItem.getAnswer();
                                    }
                                    Intent intentSendData = new Intent(context , EditQuestionActivity.class);
                                    intentSendData.putExtra("QID", qId);
                                    intentSendData.putExtra("QUESTION", question);
                                    intentSendData.putExtra("ANSWER", answer);
                                    intentSendData.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(intentSendData);
                                }
                            });

                    alertDialogBuilder.setTitle("Choose Action");
                    alertDialogBuilder.setCancelable(true);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    return false;
                }
            });
        }
    }
}

