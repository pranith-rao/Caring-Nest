package com.example.caringnest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import soup.neumorphism.NeumorphFloatingActionButton;

public class UserAddDetails extends AppCompatActivity {

    EditText u_name, u_phone, u_location;
    Button ubtn_submit;
    NeumorphFloatingActionButton cancelBtn;

    LinearLayout layout_main;

    private Animation animation_fadein;

    String id = "1";

    ProgressDialog pd;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_details);

        Utils.blackIconStatusBar(UserAddDetails.this,R.color.light_background);

        u_name = findViewById(R.id.u_name);
        u_phone = findViewById(R.id.u_phone);
        u_location = findViewById(R.id.u_location);
        ubtn_submit = findViewById(R.id.ubtn_submit);
        cancelBtn =  findViewById(R.id.cancelBtn);

        layout_main = findViewById(R.id.layout_main);

        db = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            animation_fadein = AnimationUtils.loadAnimation(UserAddDetails.this, R.anim.fade_in);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layout_main.setVisibility(View.VISIBLE);
                    layout_main.setAnimation(animation_fadein);
                }
            },100);
        }

        ubtn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = u_name.getText().toString().trim();
                String phone = u_phone.getText().toString().trim();
                String location = u_location.getText().toString().trim();

                if (!name.isEmpty() && !phone.isEmpty() &&  !location.isEmpty() && phone.length()>=10) {
                    updateData(id, name, phone, location);
                    startActivity(new Intent(UserAddDetails.this , UserMain.class));
                    finish();
                }
                else if(name.isEmpty())
                    u_name.setError("Empty fields are not allowed");
                else if(phone.isEmpty())
                    u_phone.setError("Empty fields are not allowed");
                else if(location.isEmpty())
                    u_location.setError("Empty fields are not allowed");
                else if(phone.length()<10)
                    u_phone.setError("Enter valid Phone Number");

            }
        });
    }

    private void updateData(String id, String name, String phone, String location) {

        db.collection("User").document(id)
                .update("name", name, "phone",phone,"location",location)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(UserAddDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}