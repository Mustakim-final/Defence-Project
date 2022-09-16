package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finddoctor.Model.SerialInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ApplyActivity extends AppCompatActivity {

    private EditText nameEdit,districtEdit,subdistrictEdit,localAddressEdit,illnessEdit,phoneEdit;
    private Button button;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    String myId;

    Intent intent;
    String userID;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("জরুরী অনুরোধ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEdit=findViewById(R.id.nameSerial_ID);
        districtEdit=findViewById(R.id.districtSerial_ID);
        subdistrictEdit=findViewById(R.id.subdistrictSerial_ID);
        localAddressEdit=findViewById(R.id.localSerial_ID);
        illnessEdit=findViewById(R.id.illnessSerial_ID);
        phoneEdit=findViewById(R.id.phoneSerial_ID);
        button=findViewById(R.id.submitSerial_ID);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        myId=firebaseUser.getUid();

        intent=getIntent();
        userID=intent.getStringExtra("userID");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=nameEdit.getText().toString().trim();
                String district=districtEdit.getText().toString().trim();
                String subDistrict=subdistrictEdit.getText().toString().trim();
                String localAddress=localAddressEdit.getText().toString().trim();
                String illness=illnessEdit.getText().toString().trim();
                String phone=phoneEdit.getText().toString().trim();

                sentSeralData(name,district,subDistrict,localAddress,illness,phone,userID,myId);

                /*
                nameEdit.setText("");
                districtEdit.setText("");
                subdistrictEdit.setText("");
                localAddressEdit.setText("");
                illnessEdit.setText("");
                phoneEdit.setText("");

                 */
            }
        });
    }

    private void sentSeralData(String name, String district, String subDistrict, String localAddress, String illness, String phone, String userID, String myId) {
        reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("district",district);
        hashMap.put("subDistrict",subDistrict);
        hashMap.put("localAddress",localAddress);
        hashMap.put("illness",illness);
        hashMap.put("phone",phone);
        hashMap.put("userID",userID);
        hashMap.put("myID",myId);

        reference.child("emergency prescription").push().setValue(hashMap);

        Toast.makeText(ApplyActivity.this,"তথ্য প্রেরন হয়েছে।",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}