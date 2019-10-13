package com.example.coffereview.ViewController;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffereview.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostDelete extends AppCompatActivity {

    String pid;
    ProgressDialog pro;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_delete);
        pro = new ProgressDialog(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"Chắc Chắn Xóa", "Hủy Bỏ"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // chọn CÓ
                if(i == 0){
                    pro.setMessage("Đang xử lý, chờ một xíu nha");
                    pro.show();
                    Bundle bundle = getIntent().getExtras();
                    if(bundle != null){
                        // lay id can xoa
                        pid = bundle.getString("pid");
                        String id = pid;
                        // xoa post
                        deletePost(id);
                    }
                }
                // chọn KHÔNG
                if(i == 1){
                    pro.setMessage("Đang xử lý, chờ một xíu nha");
                    pro.show();
                    startActivity(new Intent(PostDelete.this, postpage.class));
                }
            }
        }).create().show();
    }
    // ham deletePost
    private void deletePost(String idPost){
        db.collection("posts").document(idPost)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PostDelete.this, "Đã xóa!!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PostDelete.this, postpage.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "error", e);
                    }
                });
    }
}
