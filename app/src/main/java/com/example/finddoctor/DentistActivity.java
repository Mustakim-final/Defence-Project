package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.finddoctor.Adapter.All_Doctor_Adapter.AllAdapter;
import com.example.finddoctor.Model.All_Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DentistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<All_Doctor> all_doctorList;
    AllAdapter allAdapter;
    DatabaseReference reference;

    Toolbar toolbar;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentist);
        intent=getIntent();
        String type=intent.getStringExtra("dentist");

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle(type);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.dentist_Recycler_ID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        all_doctorList=new ArrayList<>();
        redAllDoctor();
    }

    private void redAllDoctor() {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        reference= FirebaseDatabase.getInstance().getReference("Doctor List");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                all_doctorList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    All_Doctor all_doctor=dataSnapshot.getValue(All_Doctor.class);
                    if (all_doctor.getType().equals("Dentist")){
                        all_doctorList.add(all_doctor);
                    }
                }
                allAdapter=new AllAdapter(DentistActivity.this,all_doctorList);
                recyclerView.setAdapter(allAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_form_left,R.anim.slide_to_right);
    }
}