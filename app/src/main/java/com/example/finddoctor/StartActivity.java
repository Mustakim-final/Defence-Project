package com.example.finddoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {






    Button button1,button2;
    Intent intent;
    int value,value1;
    final static String serial="serial";
    final static String pres="pres";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        intent=getIntent();
        value=intent.getIntExtra(MainActivity.serial,0);
        intent=getIntent();
        value1=intent.getIntExtra(MainActivity.pres,0);




        button1=findViewById(R.id.signIn_ID);
        button1.setOnClickListener(this);
        button2=findViewById(R.id.signUpIn_ID);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.signIn_ID){
            Intent intent=new Intent(StartActivity.this,SignInActivity.class);
            intent.putExtra(serial,value);
            intent.putExtra(pres,value1);
            startActivity(intent);
        }else if (v.getId()==R.id.signUpIn_ID){
            Intent intent=new Intent(StartActivity.this,RegistrationActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null && value==1){
            Intent intent=new Intent(StartActivity.this,Prescription.class);
            startActivity(intent);
            finish();
        }else if (firebaseUser!=null && value1==2){
            Intent intent=new Intent(StartActivity.this,SerilaIntroductionActivity.class);
            startActivity(intent);
            finish();
        }
        super.onStart();
    }


}