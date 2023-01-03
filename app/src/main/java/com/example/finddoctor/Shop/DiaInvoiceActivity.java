package com.example.finddoctor.Shop;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finddoctor.Adapter.Diagnostic.DIaCartAdapter;
import com.example.finddoctor.EventBus.MyUpdateCartEvent;
import com.example.finddoctor.Listener.IDiaCartListener;
import com.example.finddoctor.Listener.IDiaInvoiceListener;
import com.example.finddoctor.Model.DiaCart;
import com.example.finddoctor.Model.Diagnostic;
import com.example.finddoctor.Model.Users;
import com.example.finddoctor.R;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DiaInvoiceActivity extends AppCompatActivity implements IDiaInvoiceListener {

    RecyclerView recyclerView;
    TextView textName,textTotal,totalGrand;
    IDiaInvoiceListener iDiaInvoiceListener;
    Toolbar toolbar;
    DatabaseReference reference;

    Button paymentBtn;
    public static final String clientId="ATwkFrgxEu_gif8XFVxeMIo82bmmSSmp0St0Li5rHmZmaX7KOdWr4lGU8BcXdFhfGtEdlHS3gChZdpI0";
    public static final int PAYPAL_REQUEST_CODE=123;

    public static PayPalConfiguration configuration=new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(clientId);

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if (EventBus.getDefault().hasSubscriberForEvent(MyUpdateCartEvent.class)){
            EventBus.getDefault().removeStickyEvent(MyUpdateCartEvent.class);
        }
        EventBus.getDefault().unregister(this);

        super.onStop();
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)

    public void onUpdateCart(MyUpdateCartEvent event){
        LoadDiaCartFormFirebase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_invoice);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("ইনভয়েস");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recycler_Cart);
        textName=findViewById(R.id.invoiceCartName);
        textTotal=findViewById(R.id.totalCost);
        totalGrand=findViewById(R.id.grandTotal);
        paymentBtn=findViewById(R.id.gen_formBtn);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String myID=firebaseUser.getUid();

        reference=FirebaseDatabase.getInstance().getReference("Users").child(myID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                textName.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount=totalGrand.getText().toString();
                getPayment(amount);
            }
        });


        init1();
        LoadDiaCartFormFirebase();
    }



    private void LoadDiaCartFormFirebase() {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String myID=firebaseUser.getUid();

        List<DiaCart> diaCartList=new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Diagnostic Cart")
                .child(myID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                DiaCart diaCart=dataSnapshot.getValue(DiaCart.class);
                                diaCart.setKey(dataSnapshot.getKey());
                                diaCartList.add(diaCart);
                            }

                            iDiaInvoiceListener.onCartLoadSuccess(diaCartList);
                        }else {
                            iDiaInvoiceListener.onCartLoadFailed("Cart Empty");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iDiaInvoiceListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

    private void init1() {
        iDiaInvoiceListener=this;

        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onCartLoadSuccess(List<DiaCart> diaCartList) {
        int sum=0;
        for (DiaCart diaCart:diaCartList){
            sum+=diaCart.getTotalPrice();
        }

        textTotal.setText(String.valueOf(sum));
        totalGrand.setText(String.valueOf(sum));

        DIaCartAdapter dIaCartAdapter=new DIaCartAdapter(this,diaCartList);
        recyclerView.setAdapter(dIaCartAdapter);
    }

    @Override
    public void onCartLoadFailed(String message) {

    }

    private void getPayment(String amount) {
        PayPalPayment payment=new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD","Learn",PayPalPayment.PAYMENT_INTENT_SALE);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_form_left,R.anim.slide_to_right);
    }
}