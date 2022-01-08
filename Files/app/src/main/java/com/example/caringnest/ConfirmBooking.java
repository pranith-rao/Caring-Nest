package com.example.caringnest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.internal.framed.Header;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import soup.neumorphism.NeumorphFloatingActionButton;

public class ConfirmBooking extends AppCompatActivity {
    TextView uName, uPhone, uLocation, aName, aPhone, aLocation;
    Button confirm;
    NeumorphFloatingActionButton backBtn;

    String id,id2;
    String name1, phone1, location1, name2, location2, phone2, email2, msg, sub = "EMERGENCY";

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        Utils.blackIconStatusBar(ConfirmBooking.this,R.color.light_background);

        uName = (TextView) findViewById(R.id.uName);
        uPhone = (TextView) findViewById(R.id.uPhone);
        uLocation = (TextView) findViewById(R.id.uLocation);
        aName = (TextView) findViewById(R.id.aName);
        aLocation = (TextView) findViewById(R.id.aLocation);
        aPhone = (TextView) findViewById(R.id.aPhone);
        confirm = (Button) findViewById(R.id.confirm);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        id = str;

        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("User").document("1");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                name1 = task.getResult().getString("name");
                phone1 = task.getResult().getString("phone");
                location1 = task.getResult().getString("location");

                uName.append(name1);
                uPhone.append(phone1);
                uLocation.append(location1);

                msg = "\nName : " + name1 + "\n\nLocation : " + location1 + "\n\nPhone : " + phone1;
            }
        });

        DocumentReference docRef2 = db.collection("Ambulance").document(id);
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document2 = task.getResult();
                name2 = document2.getString("name");
                phone2 = document2.getString("phone");
                location2 = document2.getString("location");
                email2 = document2.getString("email");

                aName.append(name2);
                aPhone.append(phone2);
                aLocation.append(location2);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
                uploadData(name1,phone1,location1,name2,phone2,location2,email2);
                click(id2);
                deleteData(id);
            }
        });
    }

    private void sendMail() {
        String mail = email2.trim();
        String message = msg;
        String subject = sub.trim();

        JavaMailApi javaMailApi = new JavaMailApi(this, mail, subject, message);
        javaMailApi.execute();

    }

    public void click(String id2) {
        String str = id2;

        Intent intent = new Intent(getApplicationContext(), Confirmed.class);
        intent.putExtra("message", str);

        startActivity(intent);
        finish();
    }

    private void uploadData(String p_name, String p_phone, String p_location, String a_name, String a_phone, String a_location, String a_email) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        id2 = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id2);

        doc.put("p_name", p_name);
        doc.put("p_phone", p_phone);
        doc.put("p_location", p_location);

        doc.put("a_name", a_name);
        doc.put("a_phone", a_phone);
        doc.put("a_email", a_email);
        doc.put("a_location", a_location);

        doc.put("time", currentDateandTime);

        db.collection("Logs").document(id2).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ConfirmBooking.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteData(String index){
        db.collection("Ambulance").document(index).delete();
    }
}