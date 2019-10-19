package com.example.coffereview.ViewController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.coffereview.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Setting extends AppCompatActivity {
    Button btdoimk, btcapnhat, btlogout;
    ImageButton btstory, btnthongke, btnngay, btnplan;
    FirebaseAuth myau;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();
        btstory = findViewById(R.id.btstory);
        btnthongke = findViewById(R.id.btnthongke);
        btnngay = findViewById(R.id.btnngay);
        btnplan = findViewById(R.id.btnplan);
        btnplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, PlanDetails.class);
                startActivity(intent);
            }
        });
        btnngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, NgayThang.class);
                startActivity(intent);
            }
        });
        btstory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, TrangChu.class);
                startActivity(intent);
            }
        });
        btnthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thongke();
            }
        });
        myau = FirebaseAuth.getInstance();
        btdoimk = findViewById(R.id.btdoimk);
        btlogout = findViewById(R.id.btlogout);
        btcapnhat = findViewById(R.id.btcapnhat);
        btcapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(Setting.this, DangNhap.class);
                startActivity(intent);
                finish();
            }
        });

        btdoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser tk = FirebaseAuth.getInstance().getCurrentUser();
                String emaimk = tk.getEmail().toString();
                myau.sendPasswordResetEmail(emaimk).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Setting.this, "Đã gửi email reset password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void thongke() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid_user = user.getUid();
        db.collection("posts")
                .whereEqualTo("uid_user", uid_user)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        List<String> posts = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.get("id") != null) {
                                posts.add(doc.getString("feeling"));

                            }
                        }
                        int n = posts.size(), vui = 0, ratVui = 0, chanNan = 0, bucBoi = 0, binhThuong = 0;
                        for (int i = 0; i < n; i++) {
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
                        Intent intent = new Intent(Setting.this, Thongke.class);
                        intent.putExtra("v", vui);
                        intent.putExtra("rv", ratVui);
                        intent.putExtra("cn", chanNan);
                        intent.putExtra("bb", bucBoi);
                        intent.putExtra("bt", binhThuong);
                        startActivity(intent);
                    }

                });

    }
    private void check()
    {

        progressDialog.setMessage("Đang xử lý");
        progressDialog.show();
        FirebaseUser uid_user = FirebaseAuth.getInstance().getCurrentUser();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docRef = db.collection("informations").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String pchieucao = (String) document.getData().get("height");
                        String pcannang = (String) document.getData().get("weight");
                        String pten = (String) document.getData().get("name");
                        Intent intent = new Intent(Setting.this, Info.class);
                        intent.putExtra("weight", pcannang);
                        intent.putExtra("height", pchieucao);
                        intent.putExtra("name", pten);
                        startActivity(intent);
                        progressDialog.dismiss();
                    } else {
                        Log.d("Tag", "No such document");
                    }
                } else {
                    Log.d("Tag", "get failed with ", task.getException());
                    Toast.makeText(Setting.this, "fail",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
