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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import soup.neumorphism.NeumorphFloatingActionButton;

public class AdminAddDetails extends AppCompatActivity {

    EditText etPhone,etLocation,etName,etEmail;
    TextView admin_addDetails_head;
    Button btnAdd;

    NeumorphFloatingActionButton btn_cancel;

    LinearLayout layout_main;

    private Animation animation_fadein;

    ProgressDialog pd;

    FirebaseFirestore db;

    String pId, pName, pPhone, pLocation, pEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_details);

        Utils.blackIconStatusBar(AdminAddDetails.this,R.color.light_background);

        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etName = findViewById(R.id.et_name);
        etLocation = findViewById(R.id.et_location);
        btnAdd = findViewById(R.id.btn_addDetails);
        admin_addDetails_head = findViewById(R.id.admin_addDetails_head);

        layout_main = findViewById(R.id.layout_main);


        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            animation_fadein = AnimationUtils.loadAnimation(AdminAddDetails.this, R.anim.fade_in);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layout_main.setVisibility(View.VISIBLE);
                    layout_main.setAnimation(animation_fadein);
                }
            },100);
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            admin_addDetails_head.setText("Update Ambulance Details");
            btnAdd.setText("Update Data");

            pId = bundle.getString("pId");
            pName = bundle.getString("pName");
            pPhone = bundle.getString("pPhone");
            pLocation = bundle.getString("pLocation");
            pEmail = bundle.getString("pEmail");

            etName.setText(pName);
            etEmail.setText(pEmail);
            etLocation.setText(pLocation);
            etPhone.setText(pPhone);
        }
        else{
            admin_addDetails_head.setText("Add Ambulance Details");
            btnAdd.setText("Add Data");
        }

        pd = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null){
                    String id = pId;
                    String name = etName.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    String email = etEmail.getText().toString().trim();
                    String location = etLocation.getText().toString().trim();

                    if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !location.isEmpty() && phone.length()>=10) {
                        updateData(id, name, phone, location, email);
                        finish();
                    }
                    else if(name.isEmpty())
                        etName.setError("Empty fields are not allowed");
                    else if(phone.isEmpty())
                        etPhone.setError("Empty fields are not allowed");
                    else if(location.isEmpty())
                        etLocation.setError("Empty fields are not allowed");
                    else if(email.isEmpty())
                        etEmail.setError("Empty fields Are not allowed");
                    else if(phone.length()<10)
                        etPhone.setError("Enter valid Phone Number");
                }
                else{
                    String name = etName.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    String email = etEmail.getText().toString().trim();
                    String location = etLocation.getText().toString().trim();

                    if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !location.isEmpty() && phone.length()>=10) {
                        uploadData(name, phone, location, email);
                        finish();
                    }
                    else if(name.isEmpty())
                        etName.setError("Empty fields are not allowed");
                    else if(phone.isEmpty())
                        etPhone.setError("Empty fields are not allowed");
                    else if(location.isEmpty())
                        etLocation.setError("Empty fields are not allowed");
                    else if(email.isEmpty())
                        etEmail.setError("Empty fields are not allowed");
                    else if(phone.length()<10)
                        etPhone.setError("Enter valid Phone Number");
                }
            }
        });
    }

    private void updateData(String id, String name, String phone, String location, String email) {
        pd.setTitle("Updating Data...");
        pd.show();

        db.collection("Ambulance").document(id)
                .update("name", name, "phone",phone,"location",location,"email",email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(AdminAddDetails.this, "Updated...", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AdminAddDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadData(String name, String phone, String location, String email) {
        pd.setTitle("Adding Details");
        pd.show();

        String id= UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id",id);
        doc.put("name",name);
        doc.put("phone",phone);
        doc.put("email",email);
        doc.put("location",location);

        db.collection("Ambulance").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(AdminAddDetails.this, "Details Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AdminAddDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}