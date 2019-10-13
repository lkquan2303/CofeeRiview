package com.example.coffereview.ViewController;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.coffereview.R;

public class MainActivity extends AppCompatActivity {
    TextView txtver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtver = findViewById(R.id.txtversion);

        // Lấy ra Version của phiên bản ứng dụng

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
           txtver.setText(getString(R.string.Version)+ " " +  packageInfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Code chuyển page

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, DangNhap.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}
