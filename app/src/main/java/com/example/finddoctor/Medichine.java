package com.example.finddoctor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Medichine extends AppCompatActivity {

    private ExpandableListView expandableListView;

    CustomAdapter customAdapter;
    List<String> header_list;
    HashMap<String,List<String>> child_list;

    private int lastExpand=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medichine);
        prepareListData();
        expandableListView=findViewById(R.id.expandable_ID);
        customAdapter=new CustomAdapter(this,header_list,child_list);
        expandableListView.setAdapter(customAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpand !=-1 && lastExpand !=groupPosition){
                    expandableListView.collapseGroup(lastExpand);
                }
                lastExpand=groupPosition;
            }
        });
    }


    private void prepareListData() {
        String[] headerString=getResources().getStringArray(R.array.header_string);
        String[] childString=getResources().getStringArray(R.array.child_string);

        header_list=new ArrayList<>();
        child_list=new HashMap<>();

        for (int i=0;i<headerString.length;i++){
            header_list.add(headerString[i]);

            List<String> child=new ArrayList<>();
            child.add(childString[i]);

            child_list.put(header_list.get(i),child);
        }
    }
}