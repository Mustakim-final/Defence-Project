package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finddoctor.Adapter.Diagnostic.DiagnosticAdapter;
import com.example.finddoctor.Model.Diagnostic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiagnosticActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DiagnosticAdapter diagnosticAdapter;
    List<Diagnostic> diagnosticList;
    DatabaseReference reference;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("ডায়াগনস্টিক");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.diagnosticRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        diagnosticList=new ArrayList<>();

        readData();
    }

    private void readData() {
        reference= FirebaseDatabase.getInstance().getReference("diagnostic_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                diagnosticList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Diagnostic diagnostic=dataSnapshot.getValue(Diagnostic.class);
                    diagnosticList.add(diagnostic);
                }

                diagnosticAdapter=new DiagnosticAdapter(DiagnosticActivity.this,diagnosticList);
                recyclerView.setAdapter(diagnosticAdapter);


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