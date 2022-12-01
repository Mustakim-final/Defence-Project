package com.example.finddoctor.Shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finddoctor.Adapter.Shop.MyCartAdapter;
import com.example.finddoctor.EventBus.MyUpdateCartEvent;
import com.example.finddoctor.Listener.ICartListener;
import com.example.finddoctor.Model.CartModel;
import com.example.finddoctor.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements ICartListener {
    RecyclerView recyclerCart;
    RelativeLayout mainLayout;
    ImageView btnBack;
    TextView textTotal,total;
    ExtendedFloatingActionButton fl;

    ICartListener iCartListener;

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
        loadCartFormFirebase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerCart=findViewById(R.id.recycler_Cart);
        mainLayout=findViewById(R.id.mainLayout);
        btnBack=findViewById(R.id.back);
        textTotal=findViewById(R.id.textTotal);
        total=findViewById(R.id.total);
        fl=findViewById(R.id.checkOutBtn);

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CartActivity.this,CheckOutActivity.class);
                intent.putExtra("total",total.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });

        init1();
        loadCartFormFirebase();
    }

    private void loadCartFormFirebase() {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String myId=firebaseUser.getUid();

        List<CartModel> cartModelList=new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Cart")
                .child(myId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                CartModel cartModel=dataSnapshot.getValue(CartModel.class);
                                cartModelList.add(cartModel);
                            }

                            iCartListener.onCartLoadSuccess(cartModelList);
                        }else {
                            iCartListener.onCartLoadFailed("Cart Empty");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iCartListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

    private void init1() {
        iCartListener=this;
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerCart.setLayoutManager(layoutManager);
        recyclerCart.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));
        btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {
        double sum=0;
        for (CartModel cartModel:cartModelList){
            sum+=cartModel.getTotalPrice();
        }
        textTotal.setText(new StringBuilder("টাকা ").append(sum));
        total.setText(String.valueOf(sum));
        MyCartAdapter myCartAdapter=new MyCartAdapter(this,cartModelList);
        recyclerCart.setAdapter(myCartAdapter);


    }

    @Override
    public void onCartLoadFailed(String message) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_form_left,R.anim.slide_to_right);
    }
}