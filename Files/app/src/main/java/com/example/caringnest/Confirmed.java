package com.example.caringnest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Confirmed extends AppCompatActivity {

    String id2, cname, clocation,cphone;
    TextView tv3, tv4, tv5;
    Button close,call;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed);

        Utils.blackIconStatusBar(Confirmed.this,R.color.light_background);

        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        close = (Button) findViewById(R.id.close);
        call = (Button) findViewById(R.id.call);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        id2 = str;

        DocumentReference docRef = db.collection("Logs").document(id2);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                cname=task.getResult().getString("a_name");
                cphone=task.getResult().getString("a_phone");
                clocation=task.getResult().getString("a_location");

                tv3.append(cname);
                tv4.append(cphone);
                tv5.append(clocation);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Confirmed.this, MainSelect.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int REQUEST_PHONE_CALL=1;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(Confirmed.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(Confirmed.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        String number=cphone.toString();
                        Intent callIntent= new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+ number));
                        startActivity(callIntent);
                    }
                }
            }
        });


    }
    @Override
    public void onBackPressed(){
        Intent i = new Intent(Confirmed.this, MainSelect.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}