package com.example.finddoctor.BlogFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finddoctor.Adapter.Blog.BlogAdapter;
import com.example.finddoctor.Model.Blog;
import com.example.finddoctor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Blog_OrthopedicFragment extends Fragment {

    RecyclerView recyclerView;
    BlogAdapter blogAdapter;
    List<Blog> blogList;
    DatabaseReference reference;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blog__orthopedic, container, false);



        recyclerView=view.findViewById(R.id.blogRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        blogList=new ArrayList<>();

        reference= FirebaseDatabase.getInstance().getReference("Blog");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                blogList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Blog blog=dataSnapshot.getValue(Blog.class);
                    if (blog.getType().equals("অর্থোপেডিক চিকিৎসা"))
                        blogList.add(blog);
                }
                blogAdapter=new BlogAdapter(getContext(),blogList);
                recyclerView.setAdapter(blogAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),""+error,Toast.LENGTH_LONG).show();
            }
        });



        return view;
    }
}