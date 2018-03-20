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

import com.climesoftt.transportmanagement.MechanicProfile;
import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.DriverProfile;
import com.climesoftt.transportmanagement.model.Person;

import java.util.ArrayList;

/**
 * Created by Ali on 3/13/2018.
 */

     /*
        This Adapter is not use Instead of this use PersonAdapter for Re-usability
        */

public class MechanicsAdaptor extends RecyclerView.Adapter<MechanicsAdaptor.VHolder>{

    private Context context;
    private ArrayList<Person> personsList;
    public MechanicsAdaptor(Context context, ArrayList<Person> arrayList ){
        this.context = context;
        this.personsList = arrayList;
    }

    public MechanicsAdaptor.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.mechanic_item, viewGroup , false);
        return new MechanicsAdaptor.VHolder(view);
    }

    public int getItemCount(){
        return personsList.size();
    }


    public void onBindViewHolder(MechanicsAdaptor.VHolder vh , int position){
        vh.txtName.setText(personsList.get(position).getName());
        vh.txtAddress.setText(personsList.get(position).getAddress());
        vh.txtPhone.setText(personsList.get(position).getPhone());

    }

    class VHolder extends RecyclerView.ViewHolder{

        private ImageView imgDriver;
        private TextView txtName,txtPhone;
        private TextView txtAddress;

        public VHolder(View view)
        {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , MechanicProfile.class);
                    context.startActivity(intent);
                }
            });

            imgDriver = (ImageView) view.findViewById(R.id.img_mechanic_photo);
            txtName = (TextView)view.findViewById(R.id.txt_mechanic_name);
            txtAddress = (TextView)view.findViewById(R.id.txt_mechanic_address);
            txtPhone = view.findViewById(R.id.txt_mechanic_phone);
        }


    }


}
