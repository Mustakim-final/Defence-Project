package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.finddoctor.Adapter.Blog.DescriptionAdapter;
import com.example.finddoctor.Model.Blog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BlogDescriptionActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DescriptionAdapter descriptionAdapter;
    List<Blog> blogList;
    Intent intent;
    DatabaseReference reference;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_description);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("Health");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent=getIntent();
        String title=intent.getStringExtra("title");
        String type=intent.getStringExtra("type");

        recyclerView=findViewById(R.id.blogDescriptionRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        blogList=new ArrayList<>();

        readData(title,type);
    }

    private void readData(String title,String type) {
        reference= FirebaseDatabase.getInstance().getReference("Blog");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                blogList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Blog blog=dataSnapshot.getValue(Blog.class);
                    if (blog.getTitle().equals(title) && blog.getType().equals(type)){
                        blogList.add(blog);
                    }
                }

                descriptionAdapter=new DescriptionAdapter(BlogDescriptionActivity.this,blogList);
                recyclerView.setAdapter(descriptionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}