package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.model.Model;
import com.example.finddoctor.Adapter.Diagnostic.DiagnosticAdapter;
import com.example.finddoctor.Adapter.Diagnostic.DiagnosticItemAdapter;
import com.example.finddoctor.EventBus.MyUpdateCartEvent;
import com.example.finddoctor.Listener.IDiaCartListener;
import com.example.finddoctor.Listener.IDiaProductListener;
import com.example.finddoctor.Model.CartDiagonstic;
import com.example.finddoctor.Model.DiaCart;
import com.example.finddoctor.Model.Diagnostic;
import com.example.finddoctor.Shop.DiaInvoiceActivity;
import com.example.finddoctor.Util.SpaceItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticItmeActivity extends AppCompatActivity implements IDiaProductListener, IDiaCartListener {
    Toolbar toolbar;



    DatabaseReference reference;



    Button buttonNext;
    TextView textViewCount,textViewPrice;

    Intent intent;
    String title;

    RelativeLayout mainLayout;
    RecyclerView diagnosticRecycler;
    IDiaProductListener iDiaProductListener;
    IDiaCartListener iDiaCartListener;

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
        countCartItem();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_itme);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("ডায়াগনস্টিক পরিষেবাদি");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonNext=findViewById(R.id.diagnosticItemnextBtnID);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DiagnosticItmeActivity.this, DiaInvoiceActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });
        textViewCount=findViewById(R.id.diagnosticItemSelect);
        textViewPrice=findViewById(R.id.diagnosticItemSelectPrice);

        intent=getIntent();
        title=intent.getStringExtra("title");

        diagnosticRecycler=findViewById(R.id.diagnosticItemRecycler);


        init1();
        LoadProductFormFirebase(title);
        countCartItem();

    }

    private void LoadProductFormFirebase(String title) {
        List<Diagnostic> diagnosticList=new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("diagnostic_info1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                Diagnostic diagnostic=dataSnapshot.getValue(Diagnostic.class);
                                diagnostic.setKey(dataSnapshot.getKey());
                                if (diagnostic.getTitle().equals(title)){
                                    diagnosticList.add(diagnostic);
                                }

                            }
                            iDiaProductListener.onProductLoadSuccess(diagnosticList);
                        }else {
                            iDiaProductListener.onProductLoadFailed("We can not find");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iDiaProductListener.onProductLoadFailed(error.getMessage());
                    }
                });
    }

    private void init1() {
        iDiaProductListener=this;
        iDiaCartListener=this;

        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        diagnosticRecycler.setLayoutManager(layoutManager);
        //diagnosticRecycler.addItemDecoration(new SpaceItemDecoration());
    }

    @Override
    public void onProductLoadSuccess(List<Diagnostic> diagnosticList) {
        DiagnosticItemAdapter diagnosticItemAdapter=new DiagnosticItemAdapter(DiagnosticItmeActivity.this,diagnosticList,iDiaCartListener);
        diagnosticRecycler.setAdapter(diagnosticItemAdapter);
    }

    @Override
    public void onProductLoadFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartLoadSuccess(List<DiaCart> diaCartList) {

        int cartSum = 0;
        int sum = 0;
        for (DiaCart diaCart : diaCartList){
            cartSum += diaCart.getQuantity();
            sum+=diaCart.getTotalPrice();
        }
        textViewCount.setText(String.valueOf(cartSum));
        textViewPrice.setText(String.valueOf(sum));
    }

    @Override
    public void onCartLoadFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countCartItem();
    }

    private void countCartItem() {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String myId=firebaseUser.getUid();

        List<DiaCart> diaCartList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Diagnostic Cart").child(myId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            DiaCart diaCart=dataSnapshot.getValue(DiaCart.class);
                            diaCart.setKey(dataSnapshot.getKey());
                            diaCartList.add(diaCart);
                        }

                        iDiaCartListener.onCartLoadSuccess(diaCartList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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