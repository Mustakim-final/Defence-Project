package com.example.finddoctor.Shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finddoctor.Model.Users;
import com.example.finddoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckOutActivity extends AppCompatActivity {
    Intent intent;
    String total;

    TextView nameText,chargeDhaka,chargeOut,subTotalText,chargeText,totalText;
    EditText phoneEdit,addressEdit;
    private RadioGroup radioGroup;
    private RadioButton radioButton,radioButton1;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String myId;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);


        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("ইনভয়েস");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent=getIntent();
        total=intent.getStringExtra("total");


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        myId=firebaseUser.getUid();

        nameText=findViewById(R.id.checkOutName);
        phoneEdit=findViewById(R.id.checkOutPhone);
        addressEdit=findViewById(R.id.checkOutAddress);
        radioGroup=findViewById(R.id.radioGroup_ID);
        radioButton=findViewById(R.id.dhaka_ID);
        radioButton1=findViewById(R.id.out_ID);

        chargeDhaka=findViewById(R.id.checkOutInChargeAmount);
        chargeOut=findViewById(R.id.checkOutOutChargeAmount);

        subTotalText=findViewById(R.id.checkOutSubTotal);
        subTotalText.setText(total);
        chargeText=findViewById(R.id.checkOutDeliveryCharge);
        totalText=findViewById(R.id.checkOutTotal);

        reference= FirebaseDatabase.getInstance().getReference("Users").child(myId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                nameText.setText(users.getUsername());
                phoneEdit.setText(users.getPhone());
                addressEdit.setText(users.getDistrict()+","+users.getSubdivision()+","+users.getDivision());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String charge1=chargeDhaka.getText().toString();
                String charge2=chargeOut.getText().toString();

                if (i==R.id.dhaka_ID){
                    chargeText.setText(charge1);

                    float subtotal=Float.parseFloat(total);
                    String charge=chargeText.getText().toString();
                    float delivery=Float.parseFloat(charge);
                    float res=subtotal+delivery;

                    totalText.setText(String.valueOf(res));
                }else {
                    chargeText.setText(charge2);

                    float subtotal=Float.parseFloat(total);
                    String charge=chargeText.getText().toString();
                    float delivery=Float.parseFloat(charge);
                    float res=subtotal+delivery;
                    totalText.setText(String.valueOf(res));
                }
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