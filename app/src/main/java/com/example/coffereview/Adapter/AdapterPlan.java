package com.example.coffereview.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coffereview.R;
import com.example.coffereview.ViewController.PlanDetailPage;
import com.example.coffereview.Model.Plans;
import com.example.coffereview.ViewController.PlanDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterPlan extends ArrayAdapter<Plans> {
    Context context;
    int resource;
    List<Plans>objects;
    TextView txtday;
    Button back;

    public AdapterPlan(@NonNull Context context, int resource, @NonNull List<Plans> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        txtday = view.findViewById(R.id.txtday);
 //       back = view.findViewById(R.id.back);
        final Plans plans = objects.get(position);
        Calendar ca =  Calendar.getInstance();
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent  = new Intent(context, PlanDetails.class);
//                context.startActivity(intent);
//            }
//        });
        String nowday = ca.get(Calendar.DAY_OF_MONTH) +"/"+ (ca.get(Calendar.MONTH)+1)+"/" +ca.get(Calendar.YEAR);
        txtday.setText( "Ngày " +ca.get(Calendar.DAY_OF_MONTH) + " Tháng " + (ca.get(Calendar.MONTH)+1) +" Năm " + ca.get(Calendar.YEAR) ) ;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid_user = plans.getUid_user();
                String id_plan = plans.getId_plan();
                String day = plans.getDay();

                Intent intent = new Intent(context, PlanDetailPage.class);
                intent.putExtra("pid", id_plan);

                context.startActivity(intent);
            }
        });

        return view;
    }
}