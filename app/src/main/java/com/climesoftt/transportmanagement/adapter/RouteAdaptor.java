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
import android.widget.Toast;

import com.climesoftt.transportmanagement.AllRoutes;
import com.climesoftt.transportmanagement.R;
import com.climesoftt.transportmanagement.Route;
import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.model.Routes;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ali on 3/19/2018.
 */

public class RouteAdaptor extends RecyclerView.Adapter<RouteAdaptor.VHolder> {
    private Context context;
    private ArrayList<Routes> arrayList;
    private RouteAdaptor adapter;
    private DatabaseReference dbref;

    public RouteAdaptor(Context context , ArrayList<Routes> rList){
        this.context = context;
        this.arrayList = rList;
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
        vh.txtDate.setText(arrayList.get(position).getrDate());
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
                    String rId = "";
                    String rTo = "";
                    String rFrom = "";
                    String toolPlaza = "";
                    String petrol = "";
                    String extraCost = "";
                    String description = "";
                    String driverEmail = "";

                    // get position of current Row
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Routes clickedDataItem = arrayList.get(pos);
                        rId = clickedDataItem.getId();
                        rTo = clickedDataItem.getToCity();
                        rFrom = clickedDataItem.getFromCity();
                        toolPlaza = clickedDataItem.getTooPlaza();
                        petrol = clickedDataItem.getPetrolCost();
                        extraCost = clickedDataItem.getExtras();
                        description = clickedDataItem.getDescription();
                        driverEmail = clickedDataItem.getEmail();

                        /*
                        //Get Total routes of specific driver
                         final int[] routeCounter = {0};
                        dbref = FirebaseDatabase.getInstance().getReference("Routes");
                        final String finalDriverEmail = driverEmail;
                        dbref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for(DataSnapshot routesSnapshot : dataSnapshot.getChildren()) {
                                    Routes data = routesSnapshot.getValue(Routes.class);
                                    if(finalDriverEmail.equals(data.getEmail()))
                                    {
                                        routeCounter[0]++;
                                    }
                                }

                                if(routeCounter[0] !=0)
                                {
                                    Toast.makeText(context, "\nTotal No. of Routes : "+Integer.toString(routeCounter[0])+"\n",Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        */
                    }
                    Intent intent = new Intent(context , Route.class);
                    intent.putExtra("RID" , rId);
                    intent.putExtra("RTO", rTo);
                    intent.putExtra("RFROM",rFrom);
                    intent.putExtra("TOOLPLAZA" , toolPlaza);
                    intent.putExtra("PETROL", petrol);
                    intent.putExtra("EXTRACOST",extraCost);
                    intent.putExtra("RDESCRIPTION", description);
                    intent.putExtra("RDRIVER_EMAIL", driverEmail);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            });
            txtDestination = (TextView) view.findViewById(R.id.txt_destination);
            txtFrom = (TextView)view.findViewById(R.id.txt_from_name);
            txtDate = view.findViewById(R.id.txt_date);
        }

    }
}
