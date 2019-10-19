package com.example.coffereview.ViewController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coffereview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditDetailsPlan extends AppCompatActivity {
    EditText edwork;
    Button btsave,back;
    String id_plan, id_content, work;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details_plan);
        edwork = findViewById(R.id.edwork);
        btsave = findViewById(R.id.btsave);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditDetailsPlan.this, PlanDetailPage.class));
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            work = bundle.getString("work");
            id_content = bundle.getString("id_content");
            id_plan = bundle.getString("id_plan");

            edwork.setText(work);
        }

        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                if(bundle != null){
                    String idplan = id_plan;
                    String idcontent = id_content;

                    String workcontent = edwork.getText().toString();
                    update(idplan, idcontent, workcontent);
                }
            }
        });

    }
    private void update(String id_plan, String id_content, String work_content){
        db.collection("plans").document(id_plan)
                .collection("content").document(id_content)
                .update("work", work_content)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditDetailsPlan.this, "Đã Cập Nhật", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditDetailsPlan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        startActivity(new Intent(EditDetailsPlan.this, PlanPage.class));
    }
}
