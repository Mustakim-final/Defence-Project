package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.finddoctor.Adapter.Health.SebaAdapter1;
import com.example.finddoctor.Model.Seba;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SebaFecilitiesActivity extends AppCompatActivity {

    Intent intent;
    Toolbar toolbar;

    RecyclerView recyclerView;
    SebaAdapter1 sebaAdapter1;
    List<Seba> sebaList;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seba_fecilities);

        intent=getIntent();
        String type=intent.getStringExtra("type");

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle(type);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.seba_Recycler_ID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        sebaList=new ArrayList<>();

        redFacilities(type);
    }

    private void redFacilities(String type) {
        reference=FirebaseDatabase.getInstance().getReference("health package1");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sebaList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Seba seba=dataSnapshot.getValue(Seba.class);
                    if (seba.getValue1().equals(type)){
                        sebaList.add(seba);
                    }
                }

                sebaAdapter1=new SebaAdapter1(SebaFecilitiesActivity.this,sebaList);
                recyclerView.setAdapter(sebaAdapter1);
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
}