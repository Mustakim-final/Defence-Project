package com.example.finddoctor.BloodSubFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finddoctor.Adapter.Blood.DonnerAdapter;
import com.example.finddoctor.Model.Users;
import com.example.finddoctor.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class B_AB_N_DonnerFragment extends Fragment {

    ExtendedFloatingActionButton fl;
    DatabaseReference reference;

    FirebaseUser firebaseUser;
    String myId;

    RecyclerView recyclerView;
    DonnerAdapter donnerAdapter;
    List<Users> usersList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_b__a_b__n__donner, container, false);

        fl=view.findViewById(R.id.bloodNeedsFBtn);

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                myId=firebaseUser.getUid();

                if (myId==null){
                    Toast.makeText(getContext(), "Please Login", Toast.LENGTH_SHORT).show();
                }else {
                    reference= FirebaseDatabase.getInstance().getReference("Users").child(myId);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users users=snapshot.getValue(Users.class);

                            String name=users.getUsername();
                            String imageUrl=users.getImageUrl();
                            String blood=users.getBlood();
                            String id=users.getId();


                            if (blood.equals("AB-")){
                                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
                                HashMap<String,Object> hashMap=new HashMap<>();
                                hashMap.put("username",name);
                                hashMap.put("imageUrl",imageUrl);
                                hashMap.put("blood",blood);
                                hashMap.put("id",id);
                                reference1.child("B_Donner").push().setValue(hashMap);
                            }else {
                                Toast.makeText(getContext(),"আপনার রক্তের গ্রুপ AB- নয়",Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });


        recyclerView=view.findViewById(R.id.bloodNeedsRecycler);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        usersList=new ArrayList<>();

        readData();
        return view;
    }

    private void readData() {
        reference=FirebaseDatabase.getInstance().getReference("B_Donner");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);

                    if (users.getBlood().equals("AB-")){
                        usersList.add(users);
                    }
                }

                donnerAdapter=new DonnerAdapter(getContext(),usersList);
                recyclerView.setAdapter(donnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}