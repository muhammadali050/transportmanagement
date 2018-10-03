package com.climesoftt.transportmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.DriverProfile;
import com.climesoftt.transportmanagement.model.Person;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ali on 3/14/2018.
 */

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.VHolder> {
    private Context context;
    private ArrayList<Person> driversList;
    public static String DRIVER_ID = "";
    public static String DRIVER_NAME = "";
    public static String DRIVER_ADDRESS = "";
    public static String DRIVER_PHONE = "";
    public static String DRIVER_IMAGE = "";
    public static String DRIVER_EMAIL = "";
    public DriverAdapter(Context context, ArrayList<Person> dList ){
        this.context = context;
        this.driversList = dList;
    }

    public VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, viewGroup , false);
        return new VHolder(view);
    }


    public int getItemCount(){
        return driversList.size();
    }


    public void onBindViewHolder(VHolder vh , int position){

        Person currentPosition = driversList.get(position);
        vh.txtName.setText(driversList.get(position).getName());
        vh.txtAddress.setText(driversList.get(position).getAddress());
        vh.txtPhone.setText(driversList.get(position).getPhone());
        String imageUrl = currentPosition.getImage();
        if(imageUrl != null && !TextUtils.isEmpty(imageUrl))
        {
            Picasso.with(context)
                    .load(driversList.get(position).getImage())
                    .placeholder(R.drawable.profile_icon)
                    .fit()
                    .centerCrop()
                    .into(vh.imgDriver);
        }else
        {
            Picasso.with(context)
                    .load(R.drawable.profile_icon)
                    .placeholder(R.drawable.profile_icon)
                    .fit()
                    .centerCrop()
                    .into(vh.imgDriver);
        }


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
                    String pId = "";
                    String pName = "";
                    String pAddress = "";
                    String pPhone = "";
                    String pImage = "";
                    String pEmail = "";
                    // get position of current Row
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Person clickedDataItem = driversList.get(pos);
                        pId = clickedDataItem.getId();
                        pName = clickedDataItem.getName();
                        pAddress = clickedDataItem.getAddress();
                        pPhone = clickedDataItem.getPhone();
                        pImage = clickedDataItem.getImage();
                        pEmail = clickedDataItem.getEmail();
                    }
                    //Send Data to MechanicProfile.java activity
                    DRIVER_ID = pId;
                    DRIVER_NAME = pName;
                    DRIVER_ADDRESS = pAddress;
                    DRIVER_PHONE = pPhone;
                    DRIVER_IMAGE = pImage;
                    DRIVER_EMAIL = pEmail;
                    Intent intent = new Intent(context , DriverProfile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
