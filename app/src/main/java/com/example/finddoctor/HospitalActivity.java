package com.example.finddoctor;

import static com.example.finddoctor.Model.Hospital.AmbulanceTitleCamparator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
import java.util.Collections;
import java.util.List;


public class HospitalActivity extends AppCompatActivity {
    Toolbar toolbar;

    RecyclerView recyclerView;
    HospitalTitleAdapter hospitalTitleAdapter;
    List<Hospital>hospitalList;
    DatabaseReference reference;
    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("হাসপাতাল");
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

    private void searchv(String str) {
        List<Hospital> hospitalList1 = new ArrayList<>();
        for (Hospital object : hospitalList) {
            if (object.getTitle().toLowerCase().contains(str.toLowerCase())) {
                hospitalList1.add(object);
            }
        }
        hospitalTitleAdapter=new HospitalTitleAdapter(HospitalActivity.this,hospitalList1);
        recyclerView.setAdapter(hospitalTitleAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.lettershort,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }else if (item.getItemId()==R.id.latterShort_ID){
            Collections.sort(hospitalList,AmbulanceTitleCamparator);
            hospitalTitleAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_form_left,R.anim.slide_to_right);
    }
}