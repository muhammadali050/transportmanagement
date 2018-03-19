package com.climesoftt.transportmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.Route;

/**
 * Created by Ali on 3/19/2018.
 */

public class RouteAdaptor extends RecyclerView.Adapter<RouteAdaptor.VHolder> {
    private Context context;
    private Cursor cursor;
    private String[] names = {"Noor", "Ali" ,"Asif", "Noor", "Ali", "Asif", "Noor", "Ali" , "Asif", "Noor", "Ali" , "Asif"};

    public RouteAdaptor(Context context ){
        this.context = context;
        //this.cursor = cursor;
    }

    public RouteAdaptor.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.rout_item, viewGroup , false);
        return new RouteAdaptor.VHolder(view);
    }

    public int getItemCount(){
        //return cursor.getCount();
        return names.length;
    }

    public void onBindViewHolder(RouteAdaptor.VHolder vh , int position){
        //cursor.moveToPosition(position);
        vh.txtDestination.setText("SIALKOT");
        vh.txtFrom.setText("Gujranwala");
    }

    class VHolder extends RecyclerView.ViewHolder{

        private TextView txtFrom;
        private TextView txtDestination;

        public VHolder(View view)
        {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , Route.class);
                    context.startActivity(intent);
                }
            });
            txtDestination = (TextView) view.findViewById(R.id.txt_from_name);
            txtFrom = (TextView)view.findViewById(R.id.txt_destination);
        }
    }
}
