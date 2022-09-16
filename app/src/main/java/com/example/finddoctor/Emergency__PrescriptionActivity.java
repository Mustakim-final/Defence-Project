package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finddoctor.Adapter.All_Doctor_Adapter.AllAdapter;
import com.example.finddoctor.Adapter.All_Doctor_Adapter.Em_PresAdapter;
import com.example.finddoctor.Model.All_Doctor;
import com.example.finddoctor.Model.Emergency_prescription;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Emergency__PrescriptionActivity extends AppCompatActivity {
    Intent intent;
    String userID;

    RecyclerView recyclerView;
    List<Emergency_prescription>emergency_prescriptionList;
    Em_PresAdapter em_presAdapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_prescription);

        intent=getIntent();
        userID=intent.getStringExtra("userID");

        recyclerView=findViewById(R.id.em_prescriptionRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        emergency_prescriptionList=new ArrayList<>();
        redPrescription();
    }

    private void redPrescription() {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        reference= FirebaseDatabase.getInstance().getReference("em_d_prescription");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                emergency_prescriptionList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Emergency_prescription emergency_prescription=dataSnapshot.getValue(Emergency_prescription.class);
                    if (emergency_prescription.getMyId().equals(userID)){
                        emergency_prescriptionList.add(emergency_prescription);
                    }
                }
                em_presAdapter=new Em_PresAdapter(Emergency__PrescriptionActivity.this,emergency_prescriptionList);
                recyclerView.setAdapter(em_presAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}