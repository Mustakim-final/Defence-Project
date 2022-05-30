package com.example.finddoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.finddoctor.Adapter.CorunaDoctorList;

public class CorunaDoctor extends AppCompatActivity {
    private ListView listView;
    String[] name;
    int[] photo={R.drawable.shamim,R.drawable.huda};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coruna_doctor);

        name=getResources().getStringArray(R.array.coruna_doctor);
        listView=findViewById(R.id.corunaListView_ID);
        CorunaDoctorList corunaDoctorList=new CorunaDoctorList(CorunaDoctor.this,name,photo);
        listView.setAdapter(corunaDoctorList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CorunaDoctor.this, SerilaIntroductionActivity.class);
                startActivity(intent);
            }
        });
    }
}