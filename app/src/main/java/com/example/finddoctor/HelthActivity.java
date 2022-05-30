package com.example.finddoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.finddoctor.Adapter.HelthAdapter;

public class HelthActivity extends AppCompatActivity {

    private ListView listView;
    String[] name;
    int[] icon={R.drawable.protin,R.drawable.fat,R.drawable.karbohaitet,R.drawable.vitamin};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helth);

        name=getResources().getStringArray(R.array.khabarer_name);


        listView=findViewById(R.id.listView_ID);


        HelthAdapter helthAdapter=new HelthAdapter(HelthActivity.this,name,icon);
        listView.setAdapter(helthAdapter);

    }
}