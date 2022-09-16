package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.finddoctor.Adapter.Ambulance.AbmulanceInfoAdapter;
import com.example.finddoctor.Adapter.Hospital.HospitalInfoAdapter;
import com.example.finddoctor.Model.Hospital;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;
    List<Hospital> hospitalList;
    DatabaseReference reference;
    AbmulanceInfoAdapter abmulanceInfoAdapter;
    Button buttonPhn;

    Intent intent;

    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_details);

        intent = getIntent();
        String title = intent.getStringExtra("title");
        intent = getIntent();
        String phone = intent.getStringExtra("phone");

        toolbar = findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("অ্যাম্বুলেন্সের বিস্তারিত");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonPhn = findViewById(R.id.ambulancePhoneBtn);
        buttonPhn.setText(phone);

        buttonPhn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Callbutton();
            }
        });
        recyclerView = findViewById(R.id.ambulancelInfoRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        hospitalList = new ArrayList<>();

        redData(title);
    }

    private void redData(String title) {
        reference = FirebaseDatabase.getInstance().getReference("ambulance_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hospitalList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Hospital hospital = dataSnapshot.getValue(Hospital.class);
                    if (hospital.getTitle().equals(title)) {
                        hospitalList.add(hospital);
                    }
                }

                abmulanceInfoAdapter = new AbmulanceInfoAdapter(AmbulanceDetailsActivity.this, hospitalList);
                recyclerView.setAdapter(abmulanceInfoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void Callbutton() {
        intent=getIntent();
        String phone = intent.getStringExtra("phone");
        if (phone.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(AmbulanceDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AmbulanceDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dail = "tel:" + phone;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dail)));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Callbutton();
            } else {
                Toast.makeText(AmbulanceDetailsActivity.this, "কোন নাম্বার নেই", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}