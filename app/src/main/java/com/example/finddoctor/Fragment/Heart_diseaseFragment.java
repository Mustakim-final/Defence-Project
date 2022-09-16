package com.example.finddoctor.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finddoctor.Adapter.All_doctor_Adapter;
import com.example.finddoctor.Model.All_Doctor;
import com.example.finddoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Heart_diseaseFragment extends Fragment {

    RecyclerView recyclerView;
    List<All_Doctor> all_doctorList;
    All_doctor_Adapter all_doctor_adapter;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_heart_disease, container, false);

        recyclerView=view.findViewById(R.id.heart_diseaseRecycler_ID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        all_doctorList=new ArrayList<>();
        redAllDoctor();

        return view;
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
                    if (all_doctor.getType().equals("Heart disease")){
                        all_doctorList.add(all_doctor);
                    }
                }
                all_doctor_adapter=new All_doctor_Adapter(getContext(),all_doctorList);
                recyclerView.setAdapter(all_doctor_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}