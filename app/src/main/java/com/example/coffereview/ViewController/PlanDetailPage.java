package com.example.coffereview.ViewController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.coffereview.Adapter.AdapterDetailsPlan;
import com.example.coffereview.Model.contentPlan;
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

public class PlanDetailPage extends AppCompatActivity {

    AdapterDetailsPlan adapterDetailsPlan;
    List<contentPlan> contentPlanList;
    ListView listdetailplan;
    String pid;
    Button back;
    RadioButton rdhoanthanh,rdchuahoanthanh;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail_page);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlanDetailPage.this, PlanPage.class));
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            pid = bundle.getString("pid");
        }

        listdetailplan = findViewById(R.id.listcontentplan);
        contentPlanList = new ArrayList<>();
        adapterDetailsPlan = new AdapterDetailsPlan(this, R.layout.listdetailsplan, contentPlanList);
        listdetailplan.setAdapter(adapterDetailsPlan);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("plans").document(pid)
                .collection("content")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.w("TAG", "error", e);
                        }
                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                            switch (dc.getType()){
                                case ADDED:
                                    contentPlan contentPlan = dc.getDocument().toObject(contentPlan.class);
                                    contentPlanList.add(contentPlan);
                                    adapterDetailsPlan.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                case REMOVED:
                                    contentPlanList.clear();
                                    String id = pid;
                                    Query query = db.collection("plans").document(id)
                                            .collection("content");
                                    ListenerRegistration registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                                        }
                                    });
                                    registration.remove();

                                    db.collection("plans").document(id)
                                            .collection("content")
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                    if(e != null){
                                                        Log.w("TAG", "error", e);
                                                    }
                                                    for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                                                        if(dc.getType() == DocumentChange.Type.ADDED){
                                                            contentPlan contentPlan = dc.getDocument().toObject(contentPlan.class);
                                                            contentPlanList.add(contentPlan);
                                                            adapterDetailsPlan.notifyDataSetChanged();
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
