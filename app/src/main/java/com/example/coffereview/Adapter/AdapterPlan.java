package com.example.coffereview.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coffereview.R;
import com.example.coffereview.ViewController.PlanDetailPage;
import com.example.coffereview.Model.Plans;

import java.util.List;

public class AdapterPlan extends ArrayAdapter<Plans> {
    Context context;
    int resource;
    List<Plans>objects;
    TextView txtday;

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
        final Plans plans = objects.get(position);
        txtday.setText(plans.getDay());

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