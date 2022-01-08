package com.example.caringnest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.NeumorphFloatingActionButton;

public class MainSelect extends AppCompatActivity {

    Button about;
    LinearLayout layout_main;
    private Animation animation_fadein;

    NeumorphCardView admin_select,user_select;

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_select);

        Utils.blackIconStatusBar(MainSelect.this,R.color.light_background);

        admin_select=findViewById(R.id.admin_select);
        user_select=findViewById(R.id.user_select);
        about = findViewById(R.id.about);

        layout_main = findViewById(R.id.layout_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            animation_fadein = AnimationUtils.loadAnimation(MainSelect.this, R.anim.fade_in);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layout_main.setVisibility(View.VISIBLE);
                    layout_main.setAnimation(animation_fadein);
                }
            },700);
        }

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainSelect.this,About.class);
                startActivity(intent);
            }
        });

        admin_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainSelect.this,AdminLogin.class);
                startActivity(intent);
            }
        });

        user_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainSelect.this,UserAddDetails.class);
                startActivity(intent);
            }
        });
    }
}