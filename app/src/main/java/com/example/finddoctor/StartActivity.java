package com.example.finddoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onStart() {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        super.onStart();
    }

    TextView button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        onConnection();



        button1=findViewById(R.id.signIn_ID);
        button1.setOnClickListener(this);
        button2=findViewById(R.id.signUpIn_ID);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.signIn_ID){
            Intent intent=new Intent(StartActivity.this,SignInActivity.class);
            startActivity(intent);
        }else if (v.getId()==R.id.signUpIn_ID){
            Intent intent=new Intent(StartActivity.this,RegistrationActivity.class);
            startActivity(intent);
        }
    }


    public void onConnection(){
        ConnectivityManager manager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info= manager.getActiveNetworkInfo();

        if (info!=null){
            if (info.getType()==ConnectivityManager.TYPE_WIFI){
                Toast.makeText(StartActivity.this,"আপনি ওয়াই ফাই ব্যাবহার করছেন",Toast.LENGTH_SHORT).show();
            }
            if (info.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(StartActivity.this,"আপনি মোবাইল ডাটা ব্যাবহার করছেন",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(StartActivity.this,"কোন ইন্টারনেট সংযোগ নেই",Toast.LENGTH_LONG).show();
        }
    }



}