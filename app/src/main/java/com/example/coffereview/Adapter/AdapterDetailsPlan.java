package com.example.coffereview.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coffereview.R;
import com.example.coffereview.ViewController.EditDetailsPlan;
import com.example.coffereview.ViewController.Info;
import com.example.coffereview.ViewController.PlanDetailPage;
import com.example.coffereview.Model.contentPlan;
import com.example.coffereview.ViewController.PlanDetails;
import com.example.coffereview.ViewController.PlanPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdapterDetailsPlan extends ArrayAdapter<contentPlan> {
    Context context;
    int resource;
    RadioButton rdhoanthanh, rdchuahoanthanh;
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
        final contentPlan contentPlan = objects.get(position);
        txtwork.setText(contentPlan.getWork());
        txttime.setText(contentPlan.getTime());
        rdhoanthanh = view.findViewById(R.id.rdhoanthanh);
        rdchuahoanthanh = view.findViewById(R.id.rdchuahoanthanh);
        String status = contentPlan.getStatus();
        String id_content = contentPlan.getId_content();
        String id_plan = contentPlan.getId_plan();

        if(status.equals("Chưa hoàn thành?") )
        {
            rdchuahoanthanh.setChecked(true);
            rdhoanthanh.setChecked(false);
        }else
        {
            rdchuahoanthanh.setChecked(false);
            rdhoanthanh.setChecked(true);
        }
        rdhoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdhoanthanh.setChecked(true);
                rdchuahoanthanh.setChecked(false);
                String id_content = contentPlan.getId_content();
                String id_plan = contentPlan.getId_plan();
                update(id_plan, id_content, "Đã hoàn thành");
            }
        });
        rdchuahoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdchuahoanthanh.setChecked(true);
                rdhoanthanh.setChecked(false);
                String id_content = contentPlan.getId_content();
                String id_plan = contentPlan.getId_plan();
                update(id_plan, id_content, "Chưa hoàn thành?");
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String[] options = {"Xóa", "Sửa"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            String[] op = {"Chắc Chắn Xóa", "Hủy Bỏ"};
                            builder1.setItems(op, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, final int i) {
                                    if(i == 0){
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        String id_content = contentPlan.getId_content();
                                        String id_plan = contentPlan.getId_plan();
                                        db.collection("plans").document(id_plan)
                                                .collection("content").document(id_content)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(context, "Xóa kế hoạch thành công", Toast.LENGTH_SHORT).show();
//                                                        Intent intent = new Intent(context,PlanDetails.class );
//                                                        context.startActivity(intent);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });
                                    }
                                    
                                }
                            }).create().show();
                        }else
                        if(i == 1){
                            String id_content = contentPlan.getId_content();
                            String work = contentPlan.getWork();
                            String time = contentPlan.getTime();
                            String status = contentPlan.getStatus();
                            String id_plan = contentPlan.getId_plan();

                            Log.d("check", "Check = " + id_plan + "  " + id_content);
                            Intent intent = new Intent(context, EditDetailsPlan.class);
                            intent.putExtra("id_content", id_content);
                            intent.putExtra("work", work);
                            intent.putExtra("time", time);
                            intent.putExtra("status", status);
                            intent.putExtra("id", id_plan);
                            context.startActivity(intent);
                        }
                    }
                }).create().show();
            }
        });

        return view;
    }
    private void update(String id_plan, String id_content, String status){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("plans").document(id_plan)
                .collection("content").document(id_content)
                .update("status", status)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }


}
