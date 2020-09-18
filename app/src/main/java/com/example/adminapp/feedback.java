package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class feedback extends AppCompatActivity {
    RecyclerView recyclerView;

    List<clinic> cliniclList = new ArrayList<>();

    String date,email,name,text;
    DatabaseReference reference;

    UsersAdapter courseAdapter;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        reference= FirebaseDatabase.getInstance().getReference("feedback");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    date = ds.child("date").getValue(String.class);
                    email = ds.child("email").getValue(String.class);
                    name = ds.child("name").getValue(String.class);
                    text = ds.child("text").getValue(String.class);
                    Log.i("afc",text);
                    cliniclList.add(new clinic(name, date, text, email));
                }
                courseAdapter = new UsersAdapter(cliniclList);

                recyclerView.setAdapter(courseAdapter);
                linearLayoutManager = new LinearLayoutManager(feedback.this);
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

        private List<clinic> clinicList;
        private Context context;

        public UsersAdapter(List<clinic> courseModelList) {
            this.clinicList = courseModelList;

        }

        @NonNull
        @Override
        public UsersAdapter.UsersAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();

            return new UsersAdapterVh(LayoutInflater.from(context).inflate(R.layout.feedback_items,null));
        }

        @Override
        public void onBindViewHolder(@NonNull final UsersAdapter.UsersAdapterVh holder, int position) {

            clinic clinic_obj = clinicList.get(position);

            final String _doctorName = clinic_obj.getuser_Name();
            final String _speciality = clinic_obj.get_date();
            final String _desc = clinic_obj.get_text();
            final String _email = clinic_obj.getEmail();


            holder._user_name.setText(_doctorName);
            holder._date.setText(_speciality);


            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AlertDialog.Builder alertadd = new AlertDialog.Builder(feedback.this);
                    LayoutInflater factory = LayoutInflater.from(feedback.this);
                    View imageview = factory.inflate(R.layout.feed_details, null);
                    TextView em=imageview.findViewById(R.id.email);
                    TextView dt=imageview.findViewById(R.id.description);
                    em.setText(_email);
                    dt.setText(_desc);
                    alertadd.setView(imageview);
                       alertadd.setNeutralButton("Close!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {

                        }
                    });

                    alertadd.show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return clinicList.size();
        }


        public class UsersAdapterVh extends RecyclerView.ViewHolder {

            TextView _user_name;
            TextView _date;
            Button confirm;

            public UsersAdapterVh(@NonNull View itemView) {
                super(itemView);
                _user_name = itemView.findViewById(R.id.usersname);
                _date = itemView.findViewById(R.id.date);
                confirm=itemView.findViewById(R.id.confirm_button);
            }
        }
    }


    class clinic  {

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        private String email;
        private String _user_name;
        private String _date;
        private String _text;


        public String get_text() {
            return _text;
        }

        public void set_text(String _text) {
            this._text = _text;
        }



        public clinic(String dName,String _special,String txt,String _email) {

            email=_email;
            _user_name = dName;
            _date=_special;
            _text=txt;
        }


        public String get_date() {
            return _date;
        }

        public void setSpeciality(String speciality) {
            this._date = speciality;
        }


        public String getuser_Name() {
            return _user_name;
        }

        public void setDoctor_Name(String cName) {
            this._user_name = cName;
        }
    }
}