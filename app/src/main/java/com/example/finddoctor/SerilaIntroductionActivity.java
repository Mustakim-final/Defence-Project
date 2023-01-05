package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Adapter.All_Doctor_Adapter.Em_DoctorAdapter;
import com.example.finddoctor.Adapter.All_doctor_Adapter;
import com.example.finddoctor.Model.All_Doctor;
import com.example.finddoctor.Model.Emergency_prescription;
import com.example.finddoctor.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SerilaIntroductionActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<Emergency_prescription> emergency_prescriptionList;
    Em_DoctorAdapter em_doctorAdapter;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    CircleImageView profilePic;
    TextView nameText;
    String myId;
    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serila_introduction);

        btnBack=findViewById(R.id.backBtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        profilePic=findViewById(R.id.profileImage_ID);
        nameText=findViewById(R.id.profileName_ID);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        myId=firebaseUser.getUid();

        reference=FirebaseDatabase.getInstance().getReference("Users").child(myId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);

                if (users.getImageUrl().equals("imageUrl")){
                    profilePic.setImageResource(R.drawable.ic_baseline_perm_identity_24);
                }else {
                    Glide.with(SerilaIntroductionActivity.this).load(users.getImageUrl()).into(profilePic);
                }

                nameText.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView=findViewById(R.id.prescriptionRecycler_ID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        emergency_prescriptionList=new ArrayList<>();
        redMyDoctor(myId);

    }

    private void redMyDoctor(String myId) {
        reference= FirebaseDatabase.getInstance().getReference("em_d_prescription");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                emergency_prescriptionList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Emergency_prescription emergency_prescription=dataSnapshot.getValue(Emergency_prescription.class);

                    if (emergency_prescription.getUserId().equals(myId)){
                        if (emergency_prescription.getUserId().equals(myId)){
                            emergency_prescription.setKey(dataSnapshot.getKey());
                            emergency_prescriptionList.add(emergency_prescription);
                        }

                    }

                }
                em_doctorAdapter=new Em_DoctorAdapter(SerilaIntroductionActivity.this,emergency_prescriptionList);
                recyclerView.setAdapter(em_doctorAdapter);

                em_doctorAdapter.setOnClickListener(new Em_DoctorAdapter.onCLickListener() {
                    @Override
                    public void delete(int position) {
                        Emergency_prescription selected=emergency_prescriptionList.get(position);
                        reference.child(selected.getKey()).removeValue();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_form_left,R.anim.slide_to_right);
    }

}