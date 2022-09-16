package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.finddoctor.Adapter.Ambulance.AmbulanceTitleAdapter;
import com.example.finddoctor.Adapter.Hospital.HospitalTitleAdapter;
import com.example.finddoctor.Adapter.Pharmacy.PharmacyAdapter;
import com.example.finddoctor.Model.Hospital;
import com.example.finddoctor.Model.Pharmacy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Medichine extends AppCompatActivity {

    Toolbar toolbar;
    SearchView search;
    RecyclerView recyclerView;
    PharmacyAdapter pharmacyAdapter;
    List<Pharmacy> pharmacyList;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medichine);


        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("ফার্মেসী");
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

        recyclerView=findViewById(R.id.pharmacyRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        pharmacyList=new ArrayList<>();

        redData();

    }


    private void redData() {

        reference= FirebaseDatabase.getInstance().getReference("pharmacy_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pharmacyList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Pharmacy pharmacy=dataSnapshot.getValue(Pharmacy.class);
                    pharmacyList.add(pharmacy);
                }

                pharmacyAdapter=new PharmacyAdapter(Medichine.this,pharmacyList);
                recyclerView.setAdapter(pharmacyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchv(String str) {
        List<Pharmacy> pharmacyList1 = new ArrayList<>();
        for (Pharmacy object : pharmacyList) {
            if (object.getTitle().toLowerCase().contains(str.toLowerCase())) {
                pharmacyList1.add(object);
            }
        }
        pharmacyAdapter=new PharmacyAdapter(Medichine.this,pharmacyList1);
        recyclerView.setAdapter(pharmacyAdapter);
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