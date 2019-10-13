package com.example.coffereview.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coffereview.R;
import com.example.coffereview.ViewController.EditDetailsPlan;
import com.example.coffereview.ViewController.PlanDetailPage;
import com.example.coffereview.Model.contentPlan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdapterDetailsPlan extends ArrayAdapter<contentPlan> {
    Context context;
    int resource;
    List<contentPlan>objects;
    TextView txttime, txtwork, txtstatus;
    public AdapterDetailsPlan(@NonNull Context context, int resource, @NonNull List<contentPlan> objects) {
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
        txtwork = view.findViewById(R.id.txtwork);
        txttime = view.findViewById(R.id.txttime);
        txtstatus = view.findViewById(R.id.txtstatus);

        final contentPlan contentPlan = objects.get(position);

        txtwork.setText(contentPlan.getWork());
        txttime.setText(contentPlan.getTime());
        txtstatus.setText(contentPlan.getStatus());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String[] options = {"XÓA", "SỬA"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            String[] op = {"CÓ", "KHÔNG"};
                            builder1.setItems(op, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(i == 0){
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        String id_content = contentPlan.getId_content();
                                        String id = contentPlan.getId();
                                        db.collection("plans").document(id)
                                                .collection("content").document(id_content)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });
                                    }
                                    if(i == 1){
                                        context.startActivity(new Intent(context, PlanDetailPage.class));
                                    }
                                }
                            }).create().show();
                        }
                        if(i == 1){
                            String id_content = contentPlan.getId_content();
                            String work = contentPlan.getWork();
                            String time = contentPlan.getTime();
                            String status = contentPlan.getStatus();
                            String id = contentPlan.getId();

                            Intent intent = new Intent(context, EditDetailsPlan.class);
                            intent.putExtra("id_content", id_content);
                            intent.putExtra("work", work);
                            intent.putExtra("time", time);
                            intent.putExtra("status", status);
                            intent.putExtra("id", id);

                            context.startActivity(intent);
                        }
                    }
                }).create().show();
            }
        });

        return view;
    }
}
