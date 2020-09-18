package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class add_doctor extends AppCompatActivity {


    EditText email,pass,name,special,loc,ph,timing;
    String _username,_pass,_name,_special,_loc,_ph,_timing,selectedOption;

    Button create;
    DatabaseReference doctor_reference,insert_reference;

    boolean flag;

    ProgressBar pb1;

    Spinner spinner;
    String[] options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        name=findViewById(R.id.name);
        special=findViewById(R.id.speciality);
        loc=findViewById(R.id.location);
        ph=findViewById(R.id.phone);
        create=findViewById(R.id.add);
        timing=findViewById(R.id.timing);

        pb1 = findViewById(R.id.progressBar1);
        insert_reference=
                FirebaseDatabase.getInstance().getReference("doctor");
        Log.i("abc","here");


        spinner = (Spinner) findViewById(R.id.spinner1);
        options = new String[]
                {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        ArrayAdapter adapter =
                new ArrayAdapter(add_doctor.this, R.layout.spinner_item, options);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.getSelectedView();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                if (position == 0) {
                    selectedOption = "";
                } else if (position == 1) {

                    selectedOption = "Monday";
                } else if (position == 2) {
                    selectedOption = "Tuesday";


                } else if (position == 3) {
                    selectedOption = "Wednesday";


                } else if (position == 4) {
                    selectedOption = "Thursday";


                }else if (position == 5) {
                    selectedOption = "Friday";


                }else if (position == 6) {
                    selectedOption = "Saturday";


                }else if (position == 7) {
                    selectedOption = "Sunday";


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pb1.setVisibility(View.VISIBLE);
                _username = email.getText().toString().trim();
                _pass = pass.getText().toString().trim();
                _name = name.getText().toString().trim();
                _special = special.getText().toString().trim();
                _loc = loc.getText().toString().trim();
                _ph = ph.getText().toString().trim();
                _timing = timing.getText().toString().trim();


                if (_username.isEmpty()
                        || _name.isEmpty()|| _special.isEmpty()
                        || _loc.isEmpty()|| _pass.isEmpty()|| _ph.isEmpty()||_timing.isEmpty()
                        || selectedOption.equals(""))
                {
                    pb1.setVisibility(View.INVISIBLE);
                    Snackbar.make(view, "enter all fields!", Snackbar.LENGTH_LONG)
                            .setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {



                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                }
                else{

                 try {
                     doctor_reference =
                             FirebaseDatabase
                                     .getInstance()
                                     .getReference("doctor").child(_username);

                    doctor_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            pb1.setVisibility(View.INVISIBLE);
                            if (snapshot.child("username").getValue(String.class) == null) {
                                Toast.makeText(add_doctor.this, "yes", Toast.LENGTH_SHORT).show();
                                insert_reference.child(_username)
                                        .setValue(new doctor(_name, _username, _loc, _pass, _special,_ph,selectedOption,_timing));
                                flag=true;
                            } else {
                                if(!flag)
                                Toast.makeText(add_doctor.this, "Username Already exists", Toast.LENGTH_SHORT).show();

                                else{
                                    flag=false;
                                }

                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            pb1.setVisibility(View.INVISIBLE);
                            Toast.makeText(add_doctor.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                 }
                 catch (Exception e)
                 {
                     pb1.setVisibility(View.INVISIBLE);
                     Toast.makeText(add_doctor.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                 }

                }


            }
        });




    }
}