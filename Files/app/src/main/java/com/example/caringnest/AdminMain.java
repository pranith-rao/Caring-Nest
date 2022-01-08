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

public class AdminMain extends AppCompatActivity {
    Button  admin_addDetails, admin_logs;

    NeumorphFloatingActionButton back_admin;

    List<Model_admin> modelAdminList = new ArrayList<>();
    RecyclerView mRecyclerView;
    private Animation animation_fadein;

    RecyclerView.LayoutManager layoutManager;

    FirebaseFirestore db;

    CustomAdapterAdmin adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Utils.blackIconStatusBar(AdminMain.this,R.color.light_background);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
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

        admin_addDetails=findViewById(R.id.admin_addDetails);
        admin_addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMain.this, AdminAddDetails.class));
            }
        });

        admin_logs = findViewById(R.id.admin_logs);
        admin_logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminMain.this,AdminLogs.class);
                startActivity(intent);
            }
        });

        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            animation_fadein = AnimationUtils.loadAnimation(AdminMain.this, R.anim.fade_in);

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

        db.collection("Ambulance")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelAdminList.clear();
                        pd.dismiss();

                        for (DocumentSnapshot doc: task.getResult()){
                            Model_admin modelAdmin = new Model_admin(doc.getString("id"),
                                    doc.getString("name"),
                                    doc.getString("phone"),
                                    doc.getString("location"),
                                    doc.getString("email"));
                            modelAdminList.add(modelAdmin);
                        }
                        adapter = new CustomAdapterAdmin(AdminMain.this, modelAdminList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminMain.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                });
    }

    public void deleteData(int index){
        pd.setTitle("Deleting Data...");
        pd.show();

        db.collection("Ambulance").document(modelAdminList.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        Toast.makeText(AdminMain.this, "Deleted...", Toast.LENGTH_SHORT).show();
                        showData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AdminMain.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}