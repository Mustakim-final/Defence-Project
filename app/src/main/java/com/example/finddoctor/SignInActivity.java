package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignInActivity extends AppCompatActivity {

    private EditText gmailEditText,passwordEditText;
    private Button button;
    private ProgressBar progressBar;
    Intent intent;
    int value;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        gmailEditText=findViewById(R.id.signEmail_ID);
        passwordEditText=findViewById(R.id.signPassword_ID);
        button=findViewById(R.id.signSubmit_ID);
        progressBar=findViewById(R.id.signProgressbar_ID);

        intent=getIntent();
        value=intent.getIntExtra(StartActivity.serial,0);

        firebaseAuth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmail=gmailEditText.getText().toString().trim();
                String password=passwordEditText.getText().toString().trim();


                if (gmail.isEmpty()){
                    gmailEditText.setError("Enter Gmail !!");
                    gmailEditText.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(gmail).matches()){
                    gmailEditText.setError("Enter Valid gmail !!");
                    gmailEditText.requestFocus();
                }else if (password.isEmpty()){
                    passwordEditText.setError("Enter Password !!");
                    passwordEditText.requestFocus();
                }else if (password.length()<6){
                    passwordEditText.setError("Enter 6 digit password !!");
                    passwordEditText.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    signInUser(gmail,password);
                }
            }
        });
    }

    private void signInUser(String gmail, String password) {
        firebaseAuth.signInWithEmailAndPassword(gmail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    if (value==1){
                        Intent intent=new Intent(SignInActivity.this,SerilaIntroductionActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(SignInActivity.this, "LogIn Successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent=new Intent(SignInActivity.this,Prescription.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(SignInActivity.this, "LogIn Successfully", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}