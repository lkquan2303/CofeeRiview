package com.example.coffereview.ViewController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffereview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostUpdate extends AppCompatActivity {

    EditText textContent;
    Button btnUpdate;
    String pid, pcontent;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_update);

        textContent = findViewById(R.id.textContent);
        btnUpdate = findViewById(R.id.btnUpdate);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            // lấy dữ liệu
            pid = bundle.getString("pid");
            pcontent = bundle.getString("pcontent");
            // hiển thị content
            textContent.setText(pcontent);
        }
        // cập nhật dữ liệu
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                if(bundle != null){
                    // lấy id update, content đã sửa
                    String id = pid;
                    String content = textContent.getText().toString();
                    // update
                    updateData(id, content);
                }
            }
        });
    }
    // hàm updateData
    private void updateData(String idPost, String contentPost){
        db.collection("posts").document(idPost)
                .update("content", contentPost)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PostUpdate.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostUpdate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        // chuyen ve trang hien thi khi update xong
        startActivity(new Intent(PostUpdate.this, postpage.class));
    }
}
