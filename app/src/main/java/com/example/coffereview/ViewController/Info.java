package com.example.coffereview.ViewController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffereview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Info extends AppCompatActivity {
    EditText ten, cannang, chieucao;
    Button btupdate;
    FirebaseFirestore db;
    TextView tvname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ten = findViewById(R.id.ten);
        cannang = findViewById(R.id.cannang);
        chieucao = findViewById(R.id.chieucao);
        btupdate = findViewById(R.id.btupdate);
        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        String vchieucao = intent.getStringExtra("chieucao");
        String vcannang = intent.getStringExtra("cannang");
        String vten = intent.getStringExtra("ten");
        ten.setText(vten);
        cannang.setText(vcannang);
        chieucao.setText(vchieucao);
        btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseFirestore.getInstance();
                String name = ten.getText().toString();
                String weight = cannang.getText().toString();
                String height = chieucao.getText().toString();

                String dc = FirebaseAuth.getInstance().getCurrentUser().getUid();
                db.collection("infomation").document(dc).update("ten",name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Info.this, "Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    }
                });
                db.collection("infomation").document(dc).update("cannag",weight).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Info.this, "Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    }
                });
                db.collection("infomation").document(dc).update("chieucao",height).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Info.this, "Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(Info.this, TrangChu.class);
                startActivity(intent);
                db.collection("infomation").document(dc).get();
            }
        });

    }
}
