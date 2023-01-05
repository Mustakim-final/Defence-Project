package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.finddoctor.Adapter.AdvertiseAdapter;
import com.example.finddoctor.Model.Advertise;
import com.example.finddoctor.Model.Users;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    private CardView doctorCard,serialCard,prescriptionCard,medicineCard,ambulance,health,emergencyCard,shopCard,hospitalCard,diagnosticCard;
    private CardView suggestionCard,extraCard;

    final static String serial="serial";
    final static String pres="pres";

    FirebaseAuth mAuth;

    List<Advertise> advertiseList;
    AdvertiseAdapter advertiseAdapter;
    SliderView sliderView;

    FirebaseStorage firebaseStorage;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    CircleImageView profileImage;
    TextView profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolBar_ID);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        doctorCard =findViewById(R.id.doctorCard_ID);
        serialCard=findViewById(R.id.serialCard_ID);
        prescriptionCard=findViewById(R.id.prescriptionCard_ID);
        medicineCard=findViewById(R.id.medicineCard_ID);
        ambulance=findViewById(R.id.ambulanceCard_ID);
        health=findViewById(R.id.helthCard_ID);
        emergencyCard=findViewById(R.id.emergency_doctorCard_ID);
        shopCard=findViewById(R.id.shopCard_ID);
        hospitalCard=findViewById(R.id.hospitalCard_ID);
        diagnosticCard=findViewById(R.id.diagnosticCard_ID);
        suggestionCard=findViewById(R.id.suggestionCard_ID);
        extraCard=findViewById(R.id.extraCard_ID);
        profileImage=findViewById(R.id.profileImage_ID);
        profileName=findViewById(R.id.profile_ID);



        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                profileName.setText(users.getUsername());


                if (users.getImageUrl().equals("imageUrl")){
                    profileImage.setImageResource(R.drawable.ic_baseline_perm_identity_24);

                }else {
                    Glide.with(getApplicationContext()).load(users.getImageUrl()).into(profileImage);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        doctorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,doctorList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);

            }
        });
        serialCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Prescription.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);

            }
        });
        prescriptionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SerilaIntroductionActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });
        medicineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Medichine.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);

            }
        });
        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AmbulanceActvity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,BloodActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });

        emergencyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EmergencyDoctorActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });

        shopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ShopActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });

        hospitalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,HospitalActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });

        diagnosticCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DiagnosticActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });

        suggestionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SuggestionActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });

        extraCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ExtraMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });


        drawerLayout=findViewById(R.id.drawer_ID);
        NavigationView navigationView=findViewById(R.id.navigation_ID);
        navigationView.setNavigationItemSelectedListener(this);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        sliderView=findViewById(R.id.sliderView_ID);

        advertiseList=new ArrayList<>();

        firebaseStorage=FirebaseStorage.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("advertise");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                advertiseList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Advertise advertise=dataSnapshot.getValue(Advertise.class);
                    advertiseList.add(advertise);
                }

                advertiseAdapter=new AdvertiseAdapter(MainActivity.this,advertiseList);
                sliderView.setSliderAdapter(advertiseAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.chat_ID){
            //
        }else if (item.getItemId()== R.id.signOut_ID){
            mAuth=FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
        }else if (item.getItemId()==R.id.profile_ID){
            Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("আপনি প্রস্থান করতে চান?");
        builder.setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("না", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.create().show();
    }
}