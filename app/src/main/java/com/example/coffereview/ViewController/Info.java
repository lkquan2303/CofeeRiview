package com.example.coffereview.ViewController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Set;

public class Info extends AppCompatActivity {
    EditText ten, cannang, chieucao;
    Button btupdate;
    TextView tvfail;
    FirebaseFirestore db;
    TextView tvname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        tvfail = findViewById(R.id.fail);
        ten = findViewById(R.id.ten);
        cannang = findViewById(R.id.cannang);
        chieucao = findViewById(R.id.chieucao);
        btupdate = findViewById(R.id.btupdate);
        Intent intent = getIntent();
        final String vchieucao = intent.getStringExtra("height");
        final String vcannang = intent.getStringExtra("weight");
        final String vten = intent.getStringExtra("name");
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

                    if(name.trim().equals("")|| height.trim().equals("") || weight.trim().equals("")  )
                    {
                        tvfail.setText("Chiều cao, tên hoặc cân nặng của bạn không hợp lệ");
                    }else {
                        int cn = Integer.parseInt(weight);
                        int cc = Integer.parseInt(height);
                        if(cc <50 || cc >300 || cn >300 || cn < 1 )
                        {
                            tvfail.setText("Chiều cao hoặc cân nặng của bạn không hợp lệ");
                        } else {
                            String dc = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            db.collection("informations").document(dc).update("name", name).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Info.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                            db.collection("informations").document(dc).update("weight", weight).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Info.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                            db.collection("informations").document(dc).update("height", height).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Info.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                }
                            });

                            db.collection("informations").document(dc).get();
                            startActivity(new Intent(Info.this, Setting.class));
                        }
                    }


  //                if(vten.equals("2"))
//                {
//                    tvfail.setText("Chiều cao của bạn chỉ được từ 50 -> 300 cm");
//                }
//                if(name.equals("")|| height.equals("") || weight.equals("")  || cc <50 || cc >300 || cn >300 || cn < 1)
////                if(cc <50 || cc >300)
////                {
////                    tvfail.setText("Chiều cao của bạn chỉ được từ 50 -> 300 cm");
////                }
////                else if(cn >300 || cn < 1)
////                {
////                    tvfail.setText("Cân nặng của bạn chỉ được từ 1 -> 300 kg");
////                } else {
//
//                {
//                 //   tvfail.setText("Chiều cao hoặc cân nặng của bạn không hợp lệ");
//                } else
//                {
//
            }
        });

    }
}
