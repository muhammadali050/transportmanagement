package com.climesoftt.transportmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.DriverProfile;
import com.climesoftt.transportmanagement.model.Person;

import java.util.ArrayList;

/**
 * Created by Ali on 3/14/2018.
 */

        /*
        This Adapter is not use Instead of this use PersonAdapter for Re-usability
        */
public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.VHolder> {
    private Context context;
    private ArrayList<Person> driversList;
    public DriverAdapter(Context context, ArrayList<Person> dList ){
        this.context = context;
        this.driversList = dList;
    }

    public DriverAdapter.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, viewGroup , false);
        return new DriverAdapter.VHolder(view);
    }


    public int getItemCount(){
        return driversList.size();
    }


    public void onBindViewHolder(DriverAdapter.VHolder vh , int position){
        vh.txtName.setText(driversList.get(position).getName());
        vh.txtAddress.setText(driversList.get(position).getAddress());
        vh.txtPhone.setText(driversList.get(position).getPhone());

    }

    class VHolder extends RecyclerView.ViewHolder{

        private ImageView imgDriver;
        private TextView txtName, txtPhone;
        private TextView txtAddress;

        public VHolder(View view)
        {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , DriverProfile.class);
                    context.startActivity(intent);
                }
            });
            imgDriver = (ImageView) view.findViewById(R.id.user_photo);
            txtName = (TextView)view.findViewById(R.id.txt_user_name);
            txtAddress = (TextView)view.findViewById(R.id.txt_user_address);
            txtPhone = view.findViewById(R.id.txt_driver_phone);
        }




    }
}
