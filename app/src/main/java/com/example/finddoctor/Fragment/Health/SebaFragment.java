package com.example.finddoctor.Fragment.Health;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.finddoctor.Adapter.Health.SebaAdapter;
import com.example.finddoctor.HelthActivity;
import com.example.finddoctor.Model.Seba;
import com.example.finddoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SebaFragment extends Fragment {

    RecyclerView recyclerView;
    List<Seba> sebaList;
    SebaAdapter sebaAdapter;

    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_seba, container, false);

        recyclerView=view.findViewById(R.id.health_recycler_ID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        sebaList=new ArrayList<>();

        readData();



        return view;
    }

    private void readData() {
        reference= FirebaseDatabase.getInstance().getReference("health package");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sebaList.clear();

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Seba seba=dataSnapshot.getValue(Seba.class);
                    sebaList.add(seba);
                }

                sebaAdapter=new SebaAdapter(getContext(),sebaList);
                recyclerView.setAdapter(sebaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}