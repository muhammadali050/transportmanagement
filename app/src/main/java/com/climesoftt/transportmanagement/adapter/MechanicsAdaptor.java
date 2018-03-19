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

/**
 * Created by Ali on 3/13/2018.
 */

public class MechanicsAdaptor extends RecyclerView.Adapter<MechanicsAdaptor.VHolder>{

    private Context context;
    private Cursor cursor;
    private String[] names = {"Noor","Ali" , "Asif","Noor","Ali" , "Asif","Noor","Ali" , "Asif","Noor","Ali" , "Asif"};

    public MechanicsAdaptor(Context context ){
        this.context = context;
        //this.cursor = cursor;
    }

    public MechanicsAdaptor.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.mechanic_item, viewGroup , false);

        return new VHolder(view);
    }


    public int getItemCount(){
        //return cursor.getCount();
        return names.length;
    }


    public void onBindViewHolder(VHolder vh , int position){
        //cursor.moveToPosition(position);

        vh.txtName.setText(names[position]);
        vh.txtAddress.setText("Street abc, Muhalla xxx, AAAAA");
        //vh.imgDriver.setImageResource(R.drawable.mechanic_icon);

        /*vh.txtNum.setText(cursor.getString(0));
        vh.txtMess.setText(cursor.getString(1));*/

    }

    class VHolder extends RecyclerView.ViewHolder{

        private ImageView imgDriver;
        private TextView txtName;
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

            //imgDriver = (ImageView) view.findViewById(R.id.img_mechanic_photo);
            txtName = (TextView)view.findViewById(R.id.txt_mechanic_name);
            txtAddress = (TextView)view.findViewById(R.id.txt_mechanic_address);
        }


    }


}
