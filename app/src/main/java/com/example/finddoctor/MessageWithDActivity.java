package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finddoctor.Fragment.MessageAdapter;
import com.example.finddoctor.Model.Chats;
import com.example.finddoctor.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageWithDActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private EditText messageEdit;
    private ImageButton sentMesageBtn;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;

    List<Chats> chatsList;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_with_dactivity);

        userNameTextView=findViewById(R.id.user_name_chat_ID);
        recyclerView=findViewById(R.id.recycler_ID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        messageEdit=findViewById(R.id.messageEdit_ID);
        sentMesageBtn=findViewById(R.id.messageSentBtn_ID);

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String myID=firebaseUser.getUid();

        intent=getIntent();
        String userID=intent.getStringExtra("userID");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Doctor List").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                userNameTextView.setText(users.getUsername());

                redMessage(myID,userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sentMesageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=messageEdit.getText().toString().trim();
                if (!message.equals("")){
                    sentMessage(myID,userID,message);
                }else{
                    Toast.makeText(MessageWithDActivity.this,"Type message!!!",Toast.LENGTH_SHORT).show();
                }

                messageEdit.setText("");
            }
        });
    }

    private void redMessage(String myID, String userID) {
        chatsList=new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Chats chats=dataSnapshot.getValue(Chats.class);
                    if (chats.getSender().equals(myID) && chats.getReciver().equals(userID) || chats.getSender().equals(userID) && chats.getReciver().equals(myID)){
                        chatsList.add(chats);
                    }
                    messageAdapter=new MessageAdapter(MessageWithDActivity.this,chatsList);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sentMessage(String myID, String userID, String message) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",myID);
        hashMap.put("reciver",userID);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);
    }

}