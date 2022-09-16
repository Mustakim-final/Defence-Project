package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Users;
import com.example.finddoctor.Profile.Pro_PharmacyActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    Button editBtn,pharmacyBtn;
    CircleImageView circleImageViewProfile;
    TextView textViewName,textViewPhone;

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    String myID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editBtn=findViewById(R.id.profileEditBtn);
        editBtn.setOnClickListener(this);
        circleImageViewProfile=findViewById(R.id.profileImage);
        textViewName=findViewById(R.id.profileName);
        textViewPhone=findViewById(R.id.profilePhone);
        pharmacyBtn=findViewById(R.id.profilePharmacy_ID);
        pharmacyBtn.setOnClickListener(this);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        myID=firebaseUser.getUid();

        reference= FirebaseDatabase.getInstance().getReference("Users").child(myID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);

                textViewName.setText(users.getUsername());
                textViewPhone.setText(users.getPhone());

                if (users.getImageUrl().equals("imageUrl")) {
                    circleImageViewProfile.setImageResource(R.drawable.ic_baseline_perm_identity_24);

                } else {
                    Glide.with(getApplicationContext()).load(users.getImageUrl()).into(circleImageViewProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.profileEditBtn){
            Intent intent=new Intent(ProfileActivity.this, ProfileEditActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
        }else if (view.getId()==R.id.profilePharmacy_ID){
            Intent intent=new Intent(ProfileActivity.this, Pro_PharmacyActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_form_left,R.anim.slide_to_right);
    }
}