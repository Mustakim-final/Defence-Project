package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.finddoctor.Adapter.Ambulance.AmbulanceTitleAdapter;
import com.example.finddoctor.Adapter.Hospital.HospitalTitleAdapter;
import com.example.finddoctor.Model.Hospital;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceActvity extends AppCompatActivity {
    Toolbar toolbar;
    SearchView search;
    RecyclerView recyclerView;
    AmbulanceTitleAdapter ambulanceTitleAdapter;
    List<Hospital> hospitalList;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_actvity);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("অ্যাম্বুলেন্স");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        search = findViewById(R.id.search);
        search.clearFocus();


          search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    searchv(s);
                    return true;
                }
            });




        recyclerView=findViewById(R.id.ambulanceTitleRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        hospitalList=new ArrayList<>();

        redData();


    }

    private void redData() {

        reference= FirebaseDatabase.getInstance().getReference("ambulance_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hospitalList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Hospital hospital=dataSnapshot.getValue(Hospital.class);
                    hospitalList.add(hospital);
                }

                ambulanceTitleAdapter=new AmbulanceTitleAdapter(AmbulanceActvity.this,hospitalList);
                recyclerView.setAdapter(ambulanceTitleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchv(String str) {
        List<Hospital> hospitalList1 = new ArrayList<>();
        for (Hospital object : hospitalList) {
            if (object.getTitle().toLowerCase().contains(str.toLowerCase())) {
                hospitalList1.add(object);
            }
        }
        ambulanceTitleAdapter=new AmbulanceTitleAdapter(AmbulanceActvity.this,hospitalList1);
        recyclerView.setAdapter(ambulanceTitleAdapter);
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