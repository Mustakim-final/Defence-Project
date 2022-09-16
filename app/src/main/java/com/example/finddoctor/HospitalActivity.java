package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.finddoctor.Adapter.Hospital.HospitalTitleAdapter;
import com.example.finddoctor.Model.Hospital;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HospitalActivity extends AppCompatActivity {
    Toolbar toolbar;

    RecyclerView recyclerView;
    HospitalTitleAdapter hospitalTitleAdapter;
    List<Hospital>hospitalList;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("হাসপাতাল");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.hospitalTitleRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        hospitalList=new ArrayList<>();

        redData();
    }

    private void redData() {

        reference= FirebaseDatabase.getInstance().getReference("hospital_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hospitalList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Hospital hospital=dataSnapshot.getValue(Hospital.class);
                    hospitalList.add(hospital);
                }

                hospitalTitleAdapter=new HospitalTitleAdapter(HospitalActivity.this,hospitalList);
                recyclerView.setAdapter(hospitalTitleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_form_left,R.anim.slide_to_right);
    }
}