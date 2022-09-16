package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.finddoctor.Adapter.Question.S_AnswerAdapter;
import com.example.finddoctor.Model.Answer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class S_AnswerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Answer> answerList;
    S_AnswerAdapter answerAdapter;
    DatabaseReference reference;


    Intent intent;

    String id,type;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanswer);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("উত্তর");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent=getIntent();
        id=intent.getStringExtra("id");
        type=intent.getStringExtra("type");

        recyclerView=findViewById(R.id.ansRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        answerList=new ArrayList<>();

        readData(id,type);
    }

    private void readData(String id,String type) {
        reference= FirebaseDatabase.getInstance().getReference("user_answer");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                answerList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Answer answer=dataSnapshot.getValue(Answer.class);
                    if (answer.getId().equals(id) && answer.getQuestionType().equals(type)){
                        answerList.add(answer);
                    }

                }

                answerAdapter=new S_AnswerAdapter(S_AnswerActivity.this,answerList);
                recyclerView.setAdapter(answerAdapter);
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