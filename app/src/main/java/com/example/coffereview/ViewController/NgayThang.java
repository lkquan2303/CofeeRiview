package com.example.coffereview.ViewController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.coffereview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NgayThang extends AppCompatActivity {
    private  DatePicker datePicker;
    FirebaseFirestore db;
    ImageButton btstory, btnthongke,btnplan, btnmore;
    EditText edtest;
    TextView tvratvui, tvvui, tvbucboi, tvchannan,tvbinhthuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        db = FirebaseFirestore.getInstance();
        btstory = findViewById(R.id.btstory);
        btnmore = findViewById(R.id.btnmore);
        btnthongke = findViewById(R.id.btnthongke);
        btnplan = findViewById(R.id.btnplan);
        tvbinhthuong = findViewById(R.id.tvbinhthuong);
        tvbucboi = findViewById(R.id.tvbucboi);
        tvchannan = findViewById(R.id.tvchannan);
        tvratvui = findViewById(R.id.tvratvui);
        tvvui = findViewById(R.id.tvvui);
        datePicker = findViewById(R.id.datepicker);
        initdate();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid_user = user.getUid();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String defaul = simpleDateFormat.format(date);
        db.collection("posts")
                .whereEqualTo("uid_user", uid_user)
                .whereEqualTo("day", defaul)
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
                        if(posts.size() != 0) {
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
                                Log.d("RATVUI", "RATVUI  = " + ratVui);
                                tvbinhthuong.setText(binhThuong + "");
                                tvbucboi.setText(bucBoi + "");
                                tvchannan.setText(chanNan + "");
                                tvratvui.setText(ratVui + "");
                                tvvui.setText(vui + "");

                            }
                        } else
                        {
                            tvbinhthuong.setText( "0");
                            tvbucboi.setText( "0");
                            tvchannan.setText("0");
                            tvratvui.setText( "0");
                            tvvui.setText( "0");
                        }
                    }

                });
        btnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NgayThang.this, Setting.class);
                startActivity(intent);
            }
        });
        btnplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NgayThang.this, PlanDetails.class);
                startActivity(intent);
            }
        });

        btstory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NgayThang.this, TrangChu.class);
                startActivity(intent);
            }
        });
        btnthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               thongke();
            }
        });
    }
    private void initdate()
    {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year,month,day);
                Date date = new Date();
                String strDateFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                String ngay = sdf.format(date);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid_user = user.getUid();
                String testngay;
                if(day < 10 && month <9 )
                {
                    testngay = "0"+day+"/0"+(month+1)+"/"+year;
                }else if( month<9 )
                {
                    testngay = day+"/0"+(month+1)+"/"+year;
                }  else if( day<10)
                {
                    testngay = "0"+day+"/"+(month+1)+"/"+year;
                }
                else {
                    testngay = day + "/" + (month+1) + "/" + year;
                }

                db.collection("posts")
                        .whereEqualTo("uid_user", uid_user)
                        .whereEqualTo("day", testngay)
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
                                if(posts.size() != 0) {
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
                                        Log.d("RATVUI", "RATVUI  = " + ratVui);
                                        tvbinhthuong.setText(binhThuong + "");
                                        tvbucboi.setText(bucBoi + "");
                                        tvchannan.setText(chanNan + "");
                                        tvratvui.setText(ratVui + "");
                                        tvvui.setText(vui + "");

                                    }
                                } else
                                {
                                    tvbinhthuong.setText( "0");
                                    tvbucboi.setText( "0");
                                    tvchannan.setText("0");
                                    tvratvui.setText( "0");
                                    tvvui.setText( "0");
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
                        Intent intent = new Intent(NgayThang.this, Thongke.class);
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
