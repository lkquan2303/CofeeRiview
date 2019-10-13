package com.example.coffereview.ViewController;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.Toast;

import com.example.coffereview.Model.Post;
import com.example.coffereview.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.UUID;

public class RatVui extends AppCompatActivity {
    EditText edcontentratvui;
    Button btsaveratvui;
    FirebaseFirestore db;
    TextClock textClock;
    Dialog dialog;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_vui);
        textClock = findViewById(R.id.textclock);
        String formatdate = "E, d-M-yyyy k:m:sa";
        textClock.setFormat12Hour(formatdate);
        textClock.setFormat24Hour(formatdate);
        edcontentratvui = findViewById(R.id.edcontentratvui);
        btsaveratvui = findViewById(R.id.btsaveratvui);
        db = FirebaseFirestore.getInstance();
        btsaveratvui.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                save();
                Intent intent = new Intent(RatVui.this, TrangChu.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void save()
    {
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        String uid_user = user.getUid();
        String id = UUID.randomUUID().toString();
        String content = edcontentratvui.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-hh:mm:ss a");
        dateFormat.setLenient(false);
        Date date = new Date();
        String daytime = dateFormat.format(date);
        String check[] = daytime.split("-");
        String time = check[1];
        String day = check[0];
        String feeling = "Rất vui";
        Post post = new Post(uid_user,id,content,feeling,time, day);
        db.collection("posts").document(id).set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RatVui.this, "Success",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
