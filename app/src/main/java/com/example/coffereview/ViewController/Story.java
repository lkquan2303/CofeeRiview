package com.example.coffereview.ViewController;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.coffereview.R;

public class Story extends AppCompatActivity  {
    ImageButton imgratvui, imgvuivui, imgbinhthuong, imgbucboi, imgchannan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        imgratvui = findViewById(R.id.imgratvui);
        imgvuivui = findViewById(R.id.imgvuivui);
        imgbinhthuong = findViewById(R.id.imgbinhthuong);
        imgbucboi = findViewById(R.id.imgbucboi);
        imgchannan = findViewById(R.id.imgchannan);

        imgratvui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Story.this, RatVui.class);
                startActivity(intent);
            }
        });
        imgvuivui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Story.this, Vui.class);
                startActivity(intent);
            }
        });
        imgbinhthuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Story.this, BinhThuong.class);
                startActivity(intent);
            }
        });
        imgbucboi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Story.this, BucBoi.class);
                startActivity(intent);
            }
        });
        imgchannan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Story.this, ChanNan.class);
                startActivity(intent);
            }
        });

    }
}
