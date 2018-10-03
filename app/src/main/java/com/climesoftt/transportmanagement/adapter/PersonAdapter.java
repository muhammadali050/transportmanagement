package com.climesoftt.transportmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.climesoftt.transportmanagement.DriverProfile;
import com.climesoftt.transportmanagement.MechanicProfile;
import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.FetchDataPerson;
import com.climesoftt.transportmanagement.utils.Message;

import java.util.ArrayList;

/**
 * Created by AtoZ on 3/20/2018.
 */
    /*
    This PersonAdapter is use as both Driver and mechanics Adapter.In this way achieve Reusability
 /*    */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.VHolder>{
  // Not using this class Create for Resuability
    public static String REFERENCE_CHILD_NAME;
    private Context context;
    private ArrayList<Person> personsList;
    public static String MECHANIC_ID = "";
    public static String MECHANIC_NAME = "";
    public static String MECHANIC_ADDRESS = "";
    public static String MECHANIC_PHONE = "";
    public PersonAdapter(Context context, ArrayList<Person> arrayList ){
            this.context = context;
            this.personsList = arrayList;
            }

    public PersonAdapter.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
            View view = LayoutInflater.from(context).inflate(R.layout.user_item, viewGroup , false);
            return new PersonAdapter.VHolder(view);
            }

    public int getItemCount(){
            return personsList.size();
            }


    public void onBindViewHolder(PersonAdapter.VHolder vh , int position){
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
                    FetchDataPerson key = new FetchDataPerson(context);
                    REFERENCE_CHILD_NAME = key.CHILD_REFERENCE_KEY;
                    //Log.d("PERSON", REFERENCE_CHILD_NAME);
                   // Message.show(context,REFERENCE_CHILD_NAME);
                /*
                    String pId = "";
                    String pName = "";
                    String pAddress = "";
                    String pPhone = "";

                    // get position of current Row
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Person clickedDataItem = personsList.get(pos);
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
                    */
                   // Message.show(context,DRIVER_ID);
                    if(TextUtils.isEmpty(REFERENCE_CHILD_NAME))
                    {
                       return;
                    }
                    if(REFERENCE_CHILD_NAME.equals("drivers"))
                    {
                        Intent intent = new Intent(context , DriverProfile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                    if(REFERENCE_CHILD_NAME.equals("mechanics"))
                    {
                        Intent intent = new Intent(context , MechanicProfile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }

                }
            });

            imgDriver = (ImageView) view.findViewById(R.id.user_photo);
            txtName = (TextView)view.findViewById(R.id.txt_user_name);
            txtAddress = (TextView)view.findViewById(R.id.txt_user_address);
            txtPhone = view.findViewById(R.id.txt_driver_phone);
        }

    }

    }
