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
import com.example.coffereview.ViewController.Info;
import com.example.coffereview.Model.InforClass;

import java.util.List;

public class AdapterInfor extends ArrayAdapter<InforClass> {
    Context context;
    int resource;
    List<InforClass> objects;
    TextView ten,cannang,chieucao;
    public AdapterInfor(@NonNull Context context, int resource, @NonNull List<InforClass> objects) {
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
        cannang = view.findViewById(R.id.cannang);
        ten = view.findViewById(R.id.ten);
        chieucao = view.findViewById(R.id.chieucao);
        final InforClass inforClass = objects.get(position);
        ten.setText(inforClass.getName());
        cannang.setText(inforClass.getWeight());
        chieucao.setText(inforClass.getWeight());

        String name = inforClass.getName();
        String weight = inforClass.getWeight();
        String height = inforClass.getHeight();
        Intent intent = new Intent(context, Info.class);
        intent.putExtra("name", name);
        intent.putExtra("height", height);
        intent.putExtra("weight", weight);
        context.startActivity(intent);
        return view;
    }
}
