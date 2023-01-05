package com.example.finddoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {




    TextView button1,button2;

    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        onConnection();



        button1=findViewById(R.id.signIn_ID);
        button2=findViewById(R.id.signUpIn_ID);


    }




    public void onConnection(){
        ConnectivityManager manager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info= manager.getActiveNetworkInfo();

        if (info!=null){
//            if (info.getType()==ConnectivityManager.TYPE_WIFI){
//                Toast.makeText(StartActivity.this,"আপনি ওয়াই ফাই ব্যাবহার করছেন",Toast.LENGTH_SHORT).show();
//            }
//            if (info.getType()==ConnectivityManager.TYPE_MOBILE){
//                Toast.makeText(StartActivity.this,"আপনি মোবাইল ডাটা ব্যাবহার করছেন",Toast.LENGTH_SHORT).show();
//            }


            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent=new Intent(StartActivity.this,SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);




        }else {
            Toast.makeText(StartActivity.this,"কোন ইন্টারনেট সংযোগ নেই",Toast.LENGTH_LONG).show();
        }
    }



}