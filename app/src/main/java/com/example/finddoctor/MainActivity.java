package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    private CardView doctorCard,serialCard,prescriptionCard,medicineCard,ambulance,health,emergencyCard,shopCard;

    final static String serial="serial";
    final static String pres="pres";

    FirebaseAuth mAuth;
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

        doctorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,doctorList.class);
                startActivity(intent);

            }
        });
        serialCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,StartActivity.class);
                intent.putExtra(serial,1);
                startActivity(intent);

            }
        });
        prescriptionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,StartActivity.class);
                intent.putExtra(pres,2);
                startActivity(intent);
            }
        });
        medicineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Medichine.class);
                startActivity(intent);

            }
        });
        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AmbulanceActvity.class);
                startActivity(intent);
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,HelthActivity.class);
                startActivity(intent);
            }
        });

        emergencyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EmergencyDoctorActivity.class);
                startActivity(intent);
            }
        });

        shopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ShopActivity.class);
                startActivity(intent);
            }
        });


        drawerLayout=findViewById(R.id.drawer_ID);
        NavigationView navigationView=findViewById(R.id.navigation_ID);
        navigationView.setNavigationItemSelectedListener(this);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



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
            Intent intent=new Intent(MainActivity.this,StartActivity.class);
            startActivity(intent);
        }else if (item.getItemId()== R.id.signOut_ID){
            mAuth=FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
        }
        return false;
    }


}