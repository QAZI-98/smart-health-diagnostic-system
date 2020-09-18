package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {


    EditText email, pass;
    Button signin;
    FirebaseAuth auth;
    String _email, _pass;
    ProgressBar pb1;
DatabaseReference adminreferecne;
    Intent i;
String db_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        signin = findViewById(R.id.email_sign_in_button);
        auth = FirebaseAuth.getInstance();
        pb1 = findViewById(R.id.progressBar1);

    signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                pb1.setVisibility(View.VISIBLE);
                _email = email.getText().toString().trim();
                _pass = pass.getText().toString().trim();

                if (_email.isEmpty() || _pass.isEmpty()) {

                    pb1.setVisibility(View.INVISIBLE);
                    Snackbar.make(view, "enter all fields!", Snackbar.LENGTH_LONG)
                            .setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {



                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else {
                    adminreferecne= FirebaseDatabase
                            .getInstance()
                             .getReference("admins")
                              .child(replacer.fireproof_string(_email));

                    adminreferecne.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            pb1.setVisibility(View.INVISIBLE);
                            db_pass = dataSnapshot.child("password").getValue(String.class);
                            if (db_pass != null && db_pass.equals(_pass)) {

                                Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                                i=new Intent(MainActivity.this,dashboard.class);
                                startActivity(i);
                                finish();
                            } else {

                                Snackbar.make(view, "Invalid username or password!", Snackbar.LENGTH_LONG)
                                        .setAction("Close", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) { }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }


            }
        });


    }
}