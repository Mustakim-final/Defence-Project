package com.example.finddoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finddoctor.Model.SerialInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SerilaIntroductionActivity extends AppCompatActivity {

    private EditText nameEdit,districtEdit,subdistrictEdit,localAddressEdit,illnessEdit,phoneEdit;
    private Button button;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serila_introduction);
        nameEdit=findViewById(R.id.nameSerial_ID);
        districtEdit=findViewById(R.id.districtSerial_ID);
        subdistrictEdit=findViewById(R.id.subdistrictSerial_ID);
        localAddressEdit=findViewById(R.id.localSerial_ID);
        illnessEdit=findViewById(R.id.illnessSerial_ID);
        phoneEdit=findViewById(R.id.phoneSerial_ID);
        button=findViewById(R.id.submitSerial_ID);

        reference= FirebaseDatabase.getInstance().getReference("serial");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentSeralData();
            }
        });
    }

    private void sentSeralData() {
        String name=nameEdit.getText().toString().trim();
        String district=districtEdit.getText().toString().trim();
        String subDistrict=subdistrictEdit.getText().toString().trim();
        String localAddress=localAddressEdit.getText().toString().trim();
        String illness=illnessEdit.getText().toString().trim();
        String phone=phoneEdit.getText().toString().trim();

        String key=reference.push().getKey();

        SerialInfo serialInfo=new SerialInfo(name,district,subDistrict,localAddress,illness,phone);
        reference.child(key).setValue(serialInfo);

        Toast.makeText(getApplicationContext(),"Your information sent",Toast.LENGTH_SHORT).show();
        nameEdit.setText("");
        districtEdit.setText("");
        subdistrictEdit.setText("");
        localAddressEdit.setText("");
        illnessEdit.setText("");
        phoneEdit.setText("");
    }
}