package com.climesoftt.transportmanagement.adapter;

/**
 * Created by Ali on 3/22/2018.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.climesoftt.transportmanagement.R;

/**
 * Created by Ali on 3/21/2018.
 */
public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.VHolder> {
    private Context context;
    private Cursor cursor;
    private String[] names = {"Noor","Ali" , "Asif","Noor","Ali" , "Asif","Noor","Ali" , "Asif","Noor","Ali" , "Asif"};

    public FaqAdapter(Context context ){
        this.context = context;
        //this.cursor = cursor;
    }

    public FaqAdapter.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.faq_item, viewGroup , false);
        return new FaqAdapter.VHolder(view);
    }


    public int getItemCount(){
        //return cursor.getCount();
        return names.length;
    }


    public void onBindViewHolder(FaqAdapter.VHolder vh , int position){
        //cursor.moveToPosition(position);

        /*vh.txtName.setText(names[position]);
        vh.txtAddress.setText("Street abc, Muhalla xxx, AAAAA");*/

    }

    class VHolder extends RecyclerView.ViewHolder{

        //private ImageView imgDriver;
        private TextView question;
        private TextView answer;
        private LinearLayout layout;

        public VHolder(View view)
        {
            super(view);
           /* imgDriver = (ImageView) view.findViewById(R.id.user_photo);*/
            question = (TextView)view.findViewById(R.id.txt_question);
            answer = (TextView)view.findViewById(R.id.txt_answer);
            layout = (LinearLayout) view.findViewById(R.id.llFaq);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(answer.isShown()){
                        answer.setVisibility(View.INVISIBLE);
                        android.view.ViewGroup.LayoutParams params = layout.getLayoutParams();
                        params.height = question.getHeight()+4;
                        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layout.setLayoutParams(params);
                        question.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_up, 0);
                    }
                    else{
                        answer.setVisibility(View.VISIBLE);
                        android.view.ViewGroup.LayoutParams params = layout.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layout.setLayoutParams(params);
                        question.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down, 0);
                    }
                }
            });
        }




    }
}

