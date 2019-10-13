package com.example.coffereview.ViewController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.coffereview.Model.Plans;
import com.example.coffereview.Model.contentPlan;
import com.example.coffereview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PlanDetails extends AppCompatActivity {

    Button btsave, bttime,btnhienthikehoach;
    EditText edwork;
    String time;
    TextView txttime;

    ImageButton btstory, btnthongke,btnngay,btnmore;
    // 1 biến lấy ngày ở đây
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);

        btstory = findViewById(R.id.btstory);
        btnthongke = findViewById(R.id.btnthongke);
        btnngay = findViewById(R.id.btnngay);
        btnmore = findViewById(R.id.btnmore);

        btnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanDetails.this, Setting.class);
                startActivity(intent);
            }
        });
        btnngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanDetails.this, NgayThang.class);
                startActivity(intent);
            }
        });
        btstory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanDetails.this, TrangChu.class);
                startActivity(intent);
            }
        });
        btnthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              thongke();
            }
        });
        btnhienthikehoach  = findViewById(R.id.btnhienthikehoach);
        btsave = findViewById(R.id.btsave);
        edwork = findViewById(R.id.edwork);
        bttime = findViewById(R.id.bttime);
        txttime = findViewById(R.id.txttime);
        // lấy ngày người dùng chọn
        btnhienthikehoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanDetails.this, PlanPage.class);
                startActivity(intent);
                finish();
            }
        });
        bttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int gio = calendar.get(Calendar.HOUR_OF_DAY);
                int phut = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(PlanDetails.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss a");
                        time  = (i + ":" + i1);
                        calendar.set(0,0,0,i,i1);
                        txttime.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, gio, phut, true);
                timePickerDialog.show();
            }
        });



        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlans();
                savePlan(time);
                startActivity(new Intent(PlanDetails.this, PlanPage.class));
                finish();
            }
        });
    }

    private void addPlans(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid_user = user.getUid();

        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        dateFormat.setLenient(false);
        Date today = new Date();
        String day = dateFormat.format(today);

        Plans plans = new Plans(uid_user, day, day);

        db.collection("plans").document(day).set(plans);
    }

    private void savePlan(String thoigian){
        String id_content = UUID.randomUUID().toString();
        String work = edwork.getText().toString();
        // lấy thời gian
//        DateFormat dateFormat = new SimpleDateFormat("hhmm");
//        dateFormat.setLenient(false);
        String time = thoigian;
        String status = "Đã hoàn thành hay chưa ?";
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        dateFormat.setLenient(false);
        Date today = new Date();
        String day = dateFormat.format(today);

        contentPlan contentPlan = new contentPlan(id_content, work, time, status, day);

        db.collection("plans").document(day)
                .collection("content").document(id_content).set(contentPlan);

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
                        Intent intent = new Intent(PlanDetails.this, Thongke.class);
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