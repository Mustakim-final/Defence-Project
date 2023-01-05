package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finddoctor.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class General_Apply_FormActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button submitBtn,paymentBtn;
    Intent intent;
    TextView chamberText,patientText,feeText,totalCostText,grandTotalText;
    String date,time,fee,name,chamber,type,d_id,problem;
    float cost;
    DatabaseReference reference;

    public static final String clientId="ATwkFrgxEu_gif8XFVxeMIo82bmmSSmp0St0Li5rHmZmaX7KOdWr4lGU8BcXdFhfGtEdlHS3gChZdpI0";
    public static final int PAYPAL_REQUEST_CODE=123;

    public static PayPalConfiguration configuration=new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(clientId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_apply_form);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("ইনভয়েস");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent=getIntent();
        date=intent.getStringExtra("date");
        time=intent.getStringExtra("time");
        fee=intent.getStringExtra("fee");
        chamber=intent.getStringExtra("chamber");
        type=intent.getStringExtra("type");
        d_id=intent.getStringExtra("userID");
        problem=intent.getStringExtra("problem");

        cost=Float.parseFloat(fee);


        chamberText=findViewById(R.id.invoiceType);
        chamberText.setText(chamber);
        patientText=findViewById(R.id.invoicePatientName);
        feeText=findViewById(R.id.appointFee);
        feeText.setText(String.valueOf(cost));
        totalCostText=findViewById(R.id.totalCost);
        totalCostText.setText(String.valueOf(fee));
        grandTotalText=findViewById(R.id.grandTotal);
        grandTotalText.setText(String.valueOf(fee));

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String myID=firebaseUser.getUid();
        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Users").child(myID);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                patientText.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submitBtn=findViewById(R.id.gen_formBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                String myid=firebaseUser.getUid();

                reference=FirebaseDatabase.getInstance().getReference("Users").child(myid);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users=snapshot.getValue(Users.class);
                        String name=users.getUsername();
                        String image=users.getImageUrl();
                        String id=users.getId();

                        Calendar calendar=Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy"+"/"+"hh:mm:ss");
                        String currentDate= simpleDateFormat.format(calendar.getTime());

                        DatabaseReference reference1= FirebaseDatabase.getInstance().getReference();
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("name",name);
                        hashMap.put("image",image);
                        hashMap.put("id",id);
                        hashMap.put("date",date);
                        hashMap.put("time",time);
                        hashMap.put("currentDate",currentDate);
                        hashMap.put("fee",String.valueOf(fee));
                        hashMap.put("chamber",chamber);
                        hashMap.put("type",type);
                        hashMap.put("d_id",d_id);
                        hashMap.put("problem",problem);
                        reference1.child("general prescription").push().setValue(hashMap);
                        Toast.makeText(General_Apply_FormActivity.this,"submit",Toast.LENGTH_SHORT).show();

                        getPayment(fee);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(General_Apply_FormActivity.this,"error"+error,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void getPayment(String fee) {


        PayPalPayment payment=new PayPalPayment(new BigDecimal(String.valueOf(fee)),"USD","Learn",PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent=new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PAYPAL_REQUEST_CODE){
            PayPalConfiguration config=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if (config!=null){
                String paymentDetails=config.toString();
                Toast.makeText(this, ""+paymentDetails, Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode== Activity.RESULT_CANCELED){
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}