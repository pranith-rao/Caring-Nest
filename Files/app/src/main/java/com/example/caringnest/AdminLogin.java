package com.example.caringnest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import soup.neumorphism.NeumorphFloatingActionButton;

public class AdminLogin extends AppCompatActivity {

    NeumorphFloatingActionButton homeBtn;
    private Animation animation_fadein;
    LinearLayout layout_main;
    private EditText mEmail , mPass;
    private Button signInBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        Utils.blackIconStatusBar(AdminLogin.this,R.color.light_background);

        mEmail = findViewById(R.id.et_adminEmail);
        mPass = findViewById(R.id.et_adminPass);
        signInBtn = findViewById(R.id.btn_adminLogin);
        homeBtn = findViewById(R.id.btn_home);
        layout_main = findViewById(R.id.layout_main);

        mAuth = FirebaseAuth.getInstance();
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            animation_fadein = AnimationUtils.loadAnimation(AdminLogin.this, R.anim.fade_in);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layout_main.setVisibility(View.VISIBLE);
                    layout_main.setAnimation(animation_fadein);
                }
            },100);
        }
    }
    private void loginUser(){
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!pass.isEmpty()){
                mAuth.signInWithEmailAndPassword(email , pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(AdminLogin.this, "Login Successfully !!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminLogin.this , AdminMain.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminLogin.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                mPass.setError("Empty Fields Are not Allowed");
            }
        }else if(email.isEmpty()){
            mEmail.setError("Empty Fields Are not Allowed");
        }else{
            mEmail.setError("Please Enter Correct Email");
        }
    }
}
