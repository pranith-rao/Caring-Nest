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

import java.util.ArrayList;
import java.util.List;

import soup.neumorphism.NeumorphFloatingActionButton;

import static androidx.core.content.ContextCompat.startActivity;

public class UserMain extends AppCompatActivity {

    NeumorphFloatingActionButton back_user;

    List<Model_user> modelUserList = new ArrayList<>();
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager layoutManager;
    private Animation animation_fadein;

    FirebaseFirestore db;
    CustomAdapterUser adapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        Utils.blackIconStatusBar(UserMain.this,R.color.light_background);

        mRecyclerView = findViewById(R.id.u_recycler_view);

        back_user = findViewById(R.id.back_user);
        back_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });

        db = FirebaseFirestore.getInstance();

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            animation_fadein = AnimationUtils.loadAnimation(UserMain.this, R.anim.fade_in);

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
        pd.setTitle("Loading Data...");
        pd.show();

        db.collection("Ambulance")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelUserList.clear();
                        pd.dismiss();

                        for (DocumentSnapshot doc: task.getResult()){
                            Model_user modelUser = new Model_user(doc.getString("id"),
                                    doc.getString("name"),
                                    doc.getString("location"),
                                    doc.getString("phone"));
                            modelUserList.add(modelUser);
                        }
                        adapter = new CustomAdapterUser(UserMain.this, modelUserList);

                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();

                        Toast.makeText(UserMain.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                });
    }

    public void click(String id){
        String str = id;

        Intent intent = new Intent(getApplicationContext(), ConfirmBooking.class);
        intent.putExtra("message", str);
        startActivity(intent);
    }
}