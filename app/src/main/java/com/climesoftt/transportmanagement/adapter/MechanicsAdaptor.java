package com.climesoftt.transportmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.climesoftt.transportmanagement.MechanicProfile;
import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.model.Person;

import java.util.ArrayList;

/**
 * Created by Ali on 3/13/2018.
 */
public class MechanicsAdaptor extends RecyclerView.Adapter<MechanicsAdaptor.VHolder>{

    private Context context;
    private ArrayList<Person> mechanicsList;
    public static String MECHANIC_ID = "";
    public static String MECHANIC_NAME = "";
    public static String MECHANIC_ADDRESS = "";
    public static String MECHANIC_PHONE = "";
    public MechanicsAdaptor(Context context, ArrayList<Person> arrayList ){
        this.context = context;
        this.mechanicsList = arrayList;
    }

    public MechanicsAdaptor.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.mechanic_item, viewGroup , false);
        return new MechanicsAdaptor.VHolder(view);
    }

    public int getItemCount(){
        return mechanicsList.size();
    }


    public void onBindViewHolder(MechanicsAdaptor.VHolder vh , int position){
        vh.txtName.setText(mechanicsList.get(position).getName());
        vh.txtAddress.setText(mechanicsList.get(position).getAddress());
        vh.txtPhone.setText(mechanicsList.get(position).getPhone());

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
                    String pId = "";
                    String pName = "";
                    String pAddress = "";
                    String pPhone = "";

                    // get position of current Row
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Person clickedDataItem = mechanicsList.get(pos);
                        pId = clickedDataItem.getId();
                        pName = clickedDataItem.getName();
                        pAddress = clickedDataItem.getAddress();
                        pPhone = clickedDataItem.getPhone();
                    }
                    //Send Data to MechanicProfile.java activity
                    MECHANIC_ID = pId;
                    MECHANIC_NAME = pName;
                    MECHANIC_ADDRESS = pAddress;
                    MECHANIC_PHONE = pPhone;

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
