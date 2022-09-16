package com.example.finddoctor.BloodSubFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finddoctor.Adapter.Blood.RequireAdapter;
import com.example.finddoctor.BloodNeedsActivity;
import com.example.finddoctor.Model.Require;
import com.example.finddoctor.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class B_O_P_NeedsFragment extends Fragment {

    ExtendedFloatingActionButton fl;
    DatabaseReference reference;
    RecyclerView recyclerView;
    List<Require> requireList;
    RequireAdapter requireAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_b__o__p__needs, container, false);

        fl=view.findViewById(R.id.bloodNeedsFBtn);

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BloodNeedsActivity.class);
                startActivity(intent);
            }
        });

        recyclerView=view.findViewById(R.id.bloodNeedsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requireList=new ArrayList<>();

        reference= FirebaseDatabase.getInstance().getReference("B_Require");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requireList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Require require=dataSnapshot.getValue(Require.class);
                    if (require.getB_group().equals("O+")){
                        requireList.add(require);
                    }

                }

                requireAdapter=new RequireAdapter(getContext(),requireList);
                recyclerView.setAdapter(requireAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}