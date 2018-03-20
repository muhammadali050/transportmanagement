package com.climesoftt.transportmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.Route;
import com.climesoftt.transportmanagement.model.Routes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ali on 3/19/2018.
 */

public class RouteAdaptor extends RecyclerView.Adapter<RouteAdaptor.VHolder> {
    private Context context;
    private ArrayList<Routes> arrayList = new ArrayList<>();

    public RouteAdaptor(Context context , ArrayList<Routes> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public RouteAdaptor.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.rout_item, viewGroup , false);
        return new RouteAdaptor.VHolder(view);
    }

    public int getItemCount(){
        return arrayList.size();
    }

    public void onBindViewHolder(RouteAdaptor.VHolder vh , int position){
        vh.txtFrom.setText(arrayList.get(position).getFromCity());
        vh.txtDestination.setText(arrayList.get(position).getToCity());
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        vh.txtDate.setText(date);
    }

    class VHolder extends RecyclerView.ViewHolder{
        private TextView txtFrom;
        private TextView txtDate;
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
            txtDestination = (TextView) view.findViewById(R.id.txt_destination);
            txtFrom = (TextView)view.findViewById(R.id.txt_from_name);
            txtDate = view.findViewById(R.id.txt_date);

        }
    }
}
