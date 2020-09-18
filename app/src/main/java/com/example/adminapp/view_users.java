package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

public class view_users extends AppCompatActivity {


    RecyclerView recyclerView;

    List<user> cliniclList = new ArrayList<>();

    String name,email;
    DatabaseReference user_reference,delete_reference;
    FirebaseAuth authlistner;
    FirebaseUser firebaseUser;
    UsersAdapter courseAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        recyclerView = findViewById(R.id.ulist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        authlistner=FirebaseAuth.getInstance();
        user_reference= FirebaseDatabase.getInstance().getReference("users");


        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    name = ds.child("name").getValue(String.class);
                    cliniclList.add(new user
                       (name
                               ,ds.child("email").getValue(String.class)
                               ,ds.child("password").getValue(String.class)));
                }
                courseAdapter = new UsersAdapter(cliniclList);

                recyclerView.setAdapter(courseAdapter);
                linearLayoutManager = new LinearLayoutManager(view_users.this);
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

        private List<user> clinicList;
        private Context context;

        public UsersAdapter(List<user> courseModelList) {
            clinicList = courseModelList;

        }

        @NonNull
        @Override
        public UsersAdapter.UsersAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();

            return new UsersAdapterVh(LayoutInflater.from(context).inflate(R.layout.user_items,null));

        }

        @Override
        public void onBindViewHolder(@NonNull UsersAdapterVh holder, int position) {

            user clinic_obj = clinicList.get(position);
            holder._name.setText(clinic_obj.getName());
            holder._email.setText(clinic_obj.getEmail());

            final String _email = clinic_obj.getEmail();
            final String _pass = clinic_obj.getPassword();


            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Snackbar.make(view, "Remove user?", Snackbar.LENGTH_LONG)
                            .setAction("confirm", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    authlistner.signInWithEmailAndPassword(_email, _pass)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        firebaseUser=authlistner.getCurrentUser();
                                                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                           if (task.isSuccessful()){
                                                               delete_reference=user_reference
                                                                       .child(replacer.fireproof_string(_email));
                                                               delete_reference.removeValue();
                                                               Intent i=new Intent(view_users.this,view_users.class);
                                                               finish();
                                                               startActivity(i);

                                                               Log.i("mtag","successful");
                                                           }
                                                           else{Log.i("mtag","sucessfull");}

                                                            }
                                                        });
                                                    }
                                                    else{Log.i("mtag","sucessfully");}
                                                }
                                            });

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
            TextView _email;

            Button confirm;

            public UsersAdapterVh(@NonNull View itemView) {
                super(itemView);
                _name = itemView.findViewById(R.id.l_uname);
                _email = itemView.findViewById(R.id.lemail);

                confirm=itemView.findViewById(R.id.delete);

            }
        }
    }



}