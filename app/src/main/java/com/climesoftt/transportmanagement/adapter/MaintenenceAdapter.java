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
import com.climesoftt.transportmanagement.AllMaintenceActivity;
import com.climesoftt.transportmanagement.DriverFaq;
import com.climesoftt.transportmanagement.EditMaintenenceActivity;
import com.climesoftt.transportmanagement.EditQuestionActivity;
import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.ViewMaintenenceActivity;
import com.climesoftt.transportmanagement.model.Faq;
import com.climesoftt.transportmanagement.model.Maintenance;
import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.DeleteRecord;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

/**
 * Created by Ali on 3/21/2018.
 */
public class MaintenenceAdapter extends RecyclerView.Adapter<MaintenenceAdapter.VHolder> {
    private Context context;
    private ArrayList<Maintenance> arrayList;
    public MaintenenceAdapter(Context context, ArrayList<Maintenance> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public MaintenenceAdapter.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.maintenence_item, viewGroup , false);
        return new MaintenenceAdapter.VHolder(view);

    }


    public int getItemCount(){
        return arrayList.size();
    }


    public void onBindViewHolder(MaintenenceAdapter.VHolder vh , int position){
        vh.description.setText(arrayList.get(position).getDescription());
        vh.startDate.setText(arrayList.get(position).getStartDate());
        vh.endDate.setText(arrayList.get(position).getEndDate());
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
                    String mId = "";
                    String mDescription = "";
                    String mStartDate = "";
                    String mEndDate = "";

                    // get position of current Row
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Maintenance clickedDataItem = arrayList.get(pos);
                        mId = clickedDataItem.getId();
                        mDescription = clickedDataItem.getDescription();
                        mStartDate = clickedDataItem.getStartDate();
                        mEndDate = clickedDataItem.getEndDate();
                    }

                    Intent intent = new Intent(context, ViewMaintenenceActivity.class);
                    intent.putExtra("MID", mId);
                    intent.putExtra("DESCRIPTION", mDescription);
                    intent.putExtra("STARTDATE", mStartDate);
                    intent.putExtra("ENDDATE", mEndDate);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                String id = "";
                String description = "";
                String startDate = "";
                String endDate = "";

                @Override
                public boolean onLongClick(View v) {
                    final int pos = getAdapterPosition();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.create();
                    alertDialogBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Delete Code here
                            if(pos != RecyclerView.NO_POSITION){
                                Maintenance clickedDataItem = arrayList.get(pos);
                                id = clickedDataItem.getId();
                                DeleteRecord.deleteRecordMethod(context , "Maintenance" , id);
                                Intent intent = new Intent(context, AllMaintenceActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                            }
                        }
                    });

                    alertDialogBuilder.setPositiveButton("Edit",new
                            DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Go to edit question activity Code
                                    if(pos != RecyclerView.NO_POSITION){
                                        Maintenance clickedDataItem = arrayList.get(pos);
                                        id = clickedDataItem.getId();
                                        description = clickedDataItem.getDescription();
                                        startDate = clickedDataItem.getStartDate();
                                        endDate = clickedDataItem.getEndDate();
                                    }

                                    Intent intent = new Intent(context, EditMaintenenceActivity.class);
                                    intent.putExtra("MID", id);
                                    intent.putExtra("DESCRIPTION", description);
                                    intent.putExtra("STARTDATE", startDate);
                                    intent.putExtra("ENDDATE", endDate);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(intent);


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

