package com.example.coffereview.ViewController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.coffereview.Adapter.AdapterPlan;
import com.example.coffereview.Model.Plans;
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

public class PlanPage extends AppCompatActivity {
    AdapterPlan adapterPlan;
    List<Plans> plansList;
    ListView listplandata;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_page);

        listplandata = findViewById(R.id.listdataplan);
        plansList = new ArrayList<>();
        adapterPlan = new AdapterPlan(this, R.layout.planlist, plansList);
        listplandata.setAdapter(adapterPlan);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid_user = user.getUid();

        db.collection("plans").whereEqualTo("uid_user", uid_user)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.w("TAG", "error", e);
                            return;
                        }
                        for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                            switch (dc.getType()){
                                case ADDED:
                                    Log.d("TAG", "new" + dc.getDocument().getData());
                                    Plans plans = dc.getDocument().toObject(Plans.class);
                                    plansList.add(plans);
                                    adapterPlan.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                case REMOVED:
                                    plansList.clear();
                                    String uid_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    Query query = db.collection("plans").whereEqualTo("uid_user", uid_user);
                                    ListenerRegistration registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                                        }
                                    });
                                    registration.remove();

                                    db.collection("plans").whereEqualTo("uid_user", uid_user)
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                    if(e != null){
                                                        Log.w("TAG", "error", e);
                                                    }
                                                    for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                                                        if(dc.getType() == DocumentChange.Type.ADDED){
                                                            Plans plans = dc.getDocument().toObject(Plans.class);
                                                            plansList.add(plans);
                                                            adapterPlan.notifyDataSetChanged();
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
