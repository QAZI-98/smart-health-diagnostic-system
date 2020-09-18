package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class dashboard extends AppCompatActivity {

    Button add_doc,view_doc,feedbac,useract;
    ImageView  sign_out;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sign_out = findViewById(R.id.signout);
        feedbac = findViewById(R.id.feedback);
        add_doc=findViewById(R.id.add_doc);
        view_doc=findViewById(R.id.view_doc);
        useract=findViewById(R.id.userv);


        useract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(dashboard.this, view_users.class);
                startActivity(i);
            }
        });

        feedbac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(dashboard.this, feedback.class);
                startActivity(i);

            }
        });
        view_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(dashboard.this, view_doctor.class);
                startActivity(i);

            }
        });

        add_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(dashboard.this, add_doctor.class);
                startActivity(i);

            }
        });


        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "confirm sign out?", Snackbar.LENGTH_LONG)
                        .setAction("sign out", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                i = new Intent(dashboard.this, MainActivity.class);
                                finish();
                                startActivity(i);
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });


    }
}