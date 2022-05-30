package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finddoctor.Adapter.DoctorAdapter;
import com.example.finddoctor.Model.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HeartDoctorActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    DatabaseReference reference;
    List<Doctor> doctorList;
    DoctorAdapter doctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_doctor);
        recyclerView = findViewById(R.id.doctor_recycler_ID);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        redInformation();


    }

    private void redInformation() {
        doctorList = new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Information");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctorList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Doctor doctor=dataSnapshot.getValue(Doctor.class);
                    doctorList.add(doctor);
                }

                doctorAdapter=new DoctorAdapter(HeartDoctorActivity.this,doctorList);
                recyclerView.setAdapter(doctorAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}