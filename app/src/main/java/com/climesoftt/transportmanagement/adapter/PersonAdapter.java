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

import java.util.ArrayList;

/**
 * Created by AtoZ on 3/20/2018.
 */
    /*
    This PersonAdapter is use as both Driver and mechanics Adapter.In this way achieve Reusability
     */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.VHolder>{
public static String referenceChildName;
private Context context;
private ArrayList<Person> personsList;
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
                referenceChildName = key.CHILD_REFERENCE_KEY;
                Log.d("KEY",referenceChildName);
                if(TextUtils.isEmpty(referenceChildName))
                {
                   return;
                }
                if(referenceChildName.equals("drivers"))
                {
                    Intent intent = new Intent(context , DriverProfile.class);
                    context.startActivity(intent);
                }
                if(referenceChildName.equals("mechanics"))
                {
                    Intent intent = new Intent(context , MechanicProfile.class);
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
