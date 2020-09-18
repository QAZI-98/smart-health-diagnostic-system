package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class view_doctor extends AppCompatActivity {

    RecyclerView recyclerView;
    List<doctor> cliniclList = new ArrayList<>();
    String loc,phone,name,special;
    DatabaseReference reference,delete_reference;
    UsersAdapter courseAdapter;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);

        recyclerView = findViewById(R.id.dlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference= FirebaseDatabase.getInstance().getReference("doctor");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    loc = ds.child("location").getValue(String.class);
                    phone = ds.child("phone").getValue(String.class);
                    name = ds.child("name").getValue(String.class);
                    special = ds.child("speciality").getValue(String.class);
                    cliniclList.add(new doctor(special, name, loc, phone,ds.child("username").getValue(String.class)));
                }
                courseAdapter = new UsersAdapter(cliniclList);

                recyclerView.setAdapter(courseAdapter);
                linearLayoutManager = new LinearLayoutManager(view_doctor.this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterVh> {

        private List<doctor> clinicList;
        private Context context;

        public UsersAdapter(List<doctor> courseModelList) {
            this.clinicList = courseModelList;

        }

        @NonNull
        @Override
        public UsersAdapter.UsersAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();

            return new UsersAdapter.UsersAdapterVh(LayoutInflater.from(context).inflate(R.layout.list_items,null));
        }

        @Override
        public void onBindViewHolder(@NonNull final UsersAdapterVh holder, int position) {

             doctor  clinic_obj = clinicList.get(position);

            holder._name.setText(clinic_obj.getName());
            holder._loc.setText(clinic_obj.getLocation());
            holder._spec.setText(clinic_obj.getSpeciality());
            holder._phon.setText(clinic_obj.getPhone());
           final String _email = clinic_obj.getUsername();

            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Snackbar.make(view, "Remove doctor", Snackbar.LENGTH_LONG)
                            .setAction("confirm", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
delete_reference=reference.child(_email);
delete_reference.removeValue();
                                    Intent i=new Intent(view_doctor.this,view_doctor.class);
                                    finish();
                                    startActivity(i);

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return clinicList.size();
        }


        public class UsersAdapterVh extends RecyclerView.ViewHolder {

            TextView _name;
            TextView _spec;
            TextView _loc;
            TextView _phon;

            Button confirm;

            public UsersAdapterVh(@NonNull View itemView) {
                super(itemView);
                _name = itemView.findViewById(R.id.lname);
                _spec = itemView.findViewById(R.id.special);
                _loc = itemView.findViewById(R.id.list_loc);
                _phon = itemView.findViewById(R.id.list_phone);

                confirm=itemView.findViewById(R.id.delete);

            }
        }
    }



}