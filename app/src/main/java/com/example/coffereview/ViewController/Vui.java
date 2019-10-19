package com.example.coffereview.ViewController;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffereview.Model.Post;
import com.example.coffereview.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.UUID;

public class Vui extends AppCompatActivity {
    EditText edcontent;
    Button btsave,back;
    FirebaseFirestore db;
    TextView tvcount1;
    int i ;
    TextClock textClock1;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vui);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Vui.this, Story.class));
            }
        });
        textClock1 = findViewById(R.id.textclock);
        String formatdate = "E, d-M-yyyy k:m:sa";
        textClock1.setFormat12Hour(formatdate);
        textClock1.setFormat24Hour(formatdate);
        edcontent = findViewById(R.id.edcontentvui);
        btsave = findViewById(R.id.btsavevui);
        db = FirebaseFirestore.getInstance();
        btsave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                i ++;
                save();
                Intent intent = new Intent(Vui.this, TrangChu.class);
                startActivity(intent);
                finish();
//                String s = String.valueOf(i);
//                tvcount1.setText(s);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void save()
    {

        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        String uid_user = user.getUid();
        String id = UUID.randomUUID().toString();
        String content = edcontent.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-hh:mm:ss a");
        dateFormat.setLenient(false);
        Date date = new Date();
        String daytime = dateFormat.format(date);
        String check[] = daytime.split("-");
        String time = check[1];
        String day = check[0];
        String feeling = "Vui";
        Post post = new Post(uid_user,id,content,feeling,time, day);
        db.collection("posts").document(id).set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Vui.this, "Thêm thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
