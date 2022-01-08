package com.example.caringnest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import soup.neumorphism.NeumorphFloatingActionButton;

public class AdminLogs extends AppCompatActivity {

    NeumorphFloatingActionButton back_admin;

    List<Model_logs> modelAdminLogs = new ArrayList<>();
    RecyclerView mRecyclerView;

    private Animation animation_fadein;

    RecyclerView.LayoutManager layoutManager;

    FirebaseFirestore db;

    CustomAdapterLogs adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_logs);

        Utils.blackIconStatusBar(AdminLogs.this,R.color.light_background);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
        db = FirebaseFirestore.getInstance();

        back_admin=findViewById(R.id.back_admin);
        back_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            animation_fadein = AnimationUtils.loadAnimation(AdminLogs.this, R.anim.fade_in);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setAnimation(animation_fadein);
                }
            },500);
        }
        showData();
    }

    public void refreshData(){
        showData();
    }

    public void showData() {
        db.collection("Logs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelAdminLogs.clear();
                        pd.dismiss();

                        for (DocumentSnapshot doc: task.getResult()){
                            Model_logs modelLogs = new Model_logs(doc.getString("id"),
                                    doc.getString("p_name"),
                                    doc.getString("p_phone"),
                                    doc.getString("p_location"),
                                    doc.getString("a_name"),
                                    doc.getString("a_phone"),
                                    doc.getString("a_location"),
                                    doc.getString("a_email"),
                                    doc.getString("time"));
                            modelAdminLogs.add(modelLogs);
                        }
                        adapter = new CustomAdapterLogs(AdminLogs.this, modelAdminLogs);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AdminLogs.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                });
    }

    public void deleteData(int index){
        pd.setTitle("Deleting Data...");
        pd.show();

        db.collection("Logs").document(modelAdminLogs.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        Toast.makeText(AdminLogs.this, "Deleted...", Toast.LENGTH_SHORT).show();
                        showData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AdminLogs.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}