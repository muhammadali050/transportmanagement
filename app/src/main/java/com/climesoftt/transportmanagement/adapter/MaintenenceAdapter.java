package com.climesoftt.transportmanagement.adapter;

/**
 * Created by Ali on 3/22/2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.climesoftt.transportmanagement.AllDriversActivity;
import com.climesoftt.transportmanagement.DriverFaq;
import com.climesoftt.transportmanagement.EditQuestionActivity;
import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.ViewMaintenenceActivity;
import com.climesoftt.transportmanagement.model.Faq;
import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.DeleteRecord;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;

import java.util.ArrayList;

/**
 * Created by Ali on 3/21/2018.
 */
public class MaintenenceAdapter extends RecyclerView.Adapter<MaintenenceAdapter.VHolder> {
    private Context context;
    private String[] demoString = new String[]{"aa" , "ff", "fff", "mmm", "nnn","mm","mm","nn","mm","mm",};
    public MaintenenceAdapter(Context context){
        this.context = context;
    }

    public MaintenenceAdapter.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.maintenence_item, viewGroup , false);
        return new MaintenenceAdapter.VHolder(view);

    }


    public int getItemCount(){
        return demoString.length;
    }


    public void onBindViewHolder(MaintenenceAdapter.VHolder vh , int position){
        /*vh.description.setText("My description is here....");
        vh.startDate.setText("11/11/11");*/
    }

    class VHolder extends RecyclerView.ViewHolder{
        private TextView description;
        private TextView startDate;
        private TextView endDate;


        public VHolder(View view)
        {
            super(view);
            description = (TextView)view.findViewById(R.id.tvMDescription);
            startDate = (TextView)view.findViewById(R.id.tvMStartDate);
            endDate = (TextView)view.findViewById(R.id.tvMEndDate);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // View Question Activity Code here
                    Intent intent = new Intent(context, ViewMaintenenceActivity.class);
                    context.startActivity(intent);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.create();
                    alertDialogBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Delete Code here

                        }
                    });

                    alertDialogBuilder.setPositiveButton("Edit",new
                            DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Go to edit question activity Code

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

