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
import com.climesoftt.transportmanagement.UserProfile;

import java.util.List;

/**
 * Created by Ali on 3/14/2018.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.VHolder> {
    private Context context;
    private Cursor cursor;
    private String[] names = {"Noor","Ali" , "Asif","Noor","Ali" , "Asif","Noor","Ali" , "Asif","Noor","Ali" , "Asif"};

    public UsersAdapter(Context context ){
        this.context = context;
        //this.cursor = cursor;
    }

    public UsersAdapter.VHolder onCreateViewHolder(ViewGroup viewGroup , int resType){
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, viewGroup , false);
        return new UsersAdapter.VHolder(view);
    }


    public int getItemCount(){
        //return cursor.getCount();
        return names.length;
    }


    public void onBindViewHolder(UsersAdapter.VHolder vh , int position){
        //cursor.moveToPosition(position);

        vh.txtName.setText(names[position]);
        vh.txtAddress.setText("Street abc, Muhalla xxx, AAAAA");

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
                    Intent intent = new Intent(context , UserProfile.class);
                    context.startActivity(intent);
                }
            });
            imgDriver = (ImageView) view.findViewById(R.id.user_photo);
            txtName = (TextView)view.findViewById(R.id.txt_user_name);
            txtAddress = (TextView)view.findViewById(R.id.txt_user_address);
        }




    }
}
