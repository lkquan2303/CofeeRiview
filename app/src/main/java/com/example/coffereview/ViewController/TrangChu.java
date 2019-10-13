package com.example.coffereview.ViewController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.coffereview.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TrangChu extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    GoogleApiClient googleApiClient;
    Button  btnHienThi,btncamxuc;
    FirebaseFirestore db;
    ImageButton btmore,btnthongke,btnngay,btnplan;
    TextView tvhello;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_trang_chu);
            db = FirebaseFirestore.getInstance();
            btncamxuc = findViewById(R.id.btncamxuc);
            btnngay = findViewById(R.id.btnngay);
            tvhello = findViewById(R.id.tvhello);
            btnthongke = findViewById(R.id.btnthongke);
            btnHienThi = findViewById(R.id.btnHienThi);
            btmore = findViewById(R.id.btmore);
            btnplan = findViewById(R.id.btnplan);
            FirebaseUser hello = FirebaseAuth.getInstance().getCurrentUser();
            String hello1 = hello.getEmail().toString();
            tvhello.setText("Xin Chào :  " + hello1);
            btnplan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TrangChu.this, PlanDetails.class);
                    startActivity(intent);
                }
            });
        btnngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu.this, NgayThang.class);
                startActivity(intent);
            }
        });
        btncamxuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu.this, Story.class);
                startActivity(intent);
            }
        });
        btnthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    thongke();
            }
        });
        btmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu.this, Setting.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        // chuyen trang hien thi
        btnHienThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrangChu.this, postpage.class));
            }
        });
    }
    public void thongke(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid_user = user.getUid();
        db.collection("posts")
                .whereEqualTo("uid_user", uid_user)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            return;
                        }
                        List<String> posts = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.get("id") != null) {
                                posts.add(doc.getString("feeling"));
                            }
                        }
                        int n = posts.size(), vui = 0, ratVui = 0, chanNan = 0, bucBoi = 0, binhThuong = 0;
                        for(int i = 0; i < n; i++) {
                            switch (posts.get(i)) {
                                case "Vui":
                                    vui = vui + 1;
                                    break;
                                case "Rất vui":
                                    ratVui = ratVui + 1;
                                    break;
                                case "Chán nản":
                                    chanNan = chanNan + 1;
                                    break;
                                case "Bực bội":
                                    bucBoi = bucBoi + 1;
                                    break;
                                case "Bình thường":
                                    binhThuong = binhThuong + 1;
                                    break;
                            }
                        }
                        Intent intent = new Intent(TrangChu.this,Thongke.class);

                        intent.putExtra("v", vui );
                        intent.putExtra("rv", ratVui);
                        intent.putExtra("cn", chanNan);
                        intent.putExtra("bb", bucBoi);
                        intent.putExtra("bt", binhThuong);
                        startActivity(intent);
                    }

                });
    }
    public void thongkecalen(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid_user = user.getUid();
        db.collection("posts")
                .whereEqualTo("uid_user", uid_user)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            return;
                        }
                        List<String> posts = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.get("id") != null) {
                                posts.add(doc.getString("time"));

                            }
                        }
                        int n = posts.size(), vui = 0, ratVui = 0, chanNan = 0, bucBoi = 0, binhThuong = 0;
                        for(int i = 0; i < n; i++) {
                            switch (posts.get(i)) {
                                case "Vui":
                                    vui = vui + 1;
                                    break;
                                case "Rất vui":
                                    ratVui = ratVui + 1;
                                    break;
                                case "Chán nản":
                                    chanNan = chanNan + 1;
                                    break;
                                case "Bực bội":
                                    bucBoi = bucBoi + 1;
                                    break;
                                case "Bình thường":
                                    binhThuong = binhThuong + 1;
                                    break;
                            }
                        }
                        Intent intent = new Intent(TrangChu.this,NgayThang.class);
                        intent.putExtra("v", vui);
                        intent.putExtra("rv", ratVui);
                        intent.putExtra("cn", chanNan);
                        intent.putExtra("bb", bucBoi);
                        intent.putExtra("bt", binhThuong);
                        startActivity(intent);
                    }

                });
    }
}