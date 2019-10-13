package com.example.coffereview.ViewController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffereview.Adapter.AdapterPost;
import com.example.coffereview.Model.Post;
import com.example.coffereview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class postpage extends AppCompatActivity {
    AdapterPost adapterPost;
    List<Post> postList;
    ListView listdata;
    Button btback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpage);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        listdata  = findViewById(R.id.listdata);
        btback = findViewById(R.id.btback);
        postList = new ArrayList<>();
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(postpage.this, TrangChu.class);
                startActivity(intent);
            }
        });
        adapterPost = new AdapterPost(this, R.layout.customlist, postList);
        listdata.setAdapter(adapterPost);
        // lấy uid của người dùng đang đăng nhập
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid_user = user.getUid();

        // lấy dữ liệu firebase
        db.collection("posts")
                .whereEqualTo("uid_user", uid_user)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.w("TAG", "error", e);
                            return;
                        }
                        for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                            switch (dc.getType()){
                                // sự kiện khi thêm mới dữ liệu
                                case ADDED:
                                    // lấy dữ liệu document lưu vào post
                                    Post post = dc.getDocument().toObject(Post.class);
                                    postList.add(post);
                                    adapterPost.notifyDataSetChanged();
                                    break;
                                // sự kiện khi thay đổi hoặc xoa dữ liệu
                                case MODIFIED:
                                case REMOVED:
                                    // xoa dữ liệu trong
                                    postList.clear();
                                    // dừng sự kiện addSnapshotListener
                                    String uid_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    Query query = db.collection("posts").whereEqualTo("uid_user", uid_user);
                                    ListenerRegistration registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                                        }
                                    });
                                    registration.remove();
                                    // đăng ký lại sự kiện addSnap mới
                                    db.collection("posts").whereEqualTo("uid_user", uid_user)
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                    if(e != null){
                                                        Log.w("TAG", "error", e);
                                                    }
                                                    for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                                                        if(dc.getType() == DocumentChange.Type.ADDED){
                                                            Post post = dc.getDocument().toObject(Post.class);
                                                            postList.add(post);
                                                            adapterPost.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            });
                                    break;
                            }
                        }
                    }
                });
    }
}
