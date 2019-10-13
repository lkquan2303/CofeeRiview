package com.example.coffereview.ViewController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.coffereview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class QuenMatKhau extends AppCompatActivity implements View.OnClickListener {
    EditText edquenmk;
    Button btkhoiphuc;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        edquenmk = findViewById(R.id.edquenmk);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = new ProgressBar(this);
        btkhoiphuc = findViewById(R.id.btkhoiphuc);
        btkhoiphuc.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
         switch (id)
         {
             case R.id.btkhoiphuc:
                 String email = edquenmk.getText().toString();
                 boolean checkmail = kiemtraemail(email);
                 if(checkmail)
                 {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(QuenMatKhau.this,"Vui lòng kiểm tra email và làm theo hướng dẫn ",Toast.LENGTH_SHORT);
                             //   Intent intent = new Intent(QuenMatKhau.this, DangNhap.class);
                             //   startActivity(intent);
                            }
                        }
                    });
                 }else {
                     Toast.makeText(this, "Email không hợp lệ",Toast.LENGTH_SHORT);
                 }
                 break;
         }
    }
    private boolean kiemtraemail(String email)
    {
        return   Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
