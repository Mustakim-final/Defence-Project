package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.finddoctor.Adapter.Shop.CategoryAdapter;
import com.example.finddoctor.Adapter.Shop.ProductAdapter;

import com.example.finddoctor.EventBus.MyUpdateCartEvent;
import com.example.finddoctor.Listener.ICartListener;
import com.example.finddoctor.Listener.IProductListener;
import com.example.finddoctor.Model.CartModel;
import com.example.finddoctor.Model.Hospital;
import com.example.finddoctor.Model.Product;
import com.example.finddoctor.Shop.CartActivity;
import com.example.finddoctor.Shop.CategoryActivity;

import com.example.finddoctor.Util.SpaceItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShopActivity extends AppCompatActivity implements IProductListener, ICartListener {

    RecyclerView recyclerViewPopular,categoryRecycler;
    ProductAdapter productAdapter;
    List<Product> productList;
    DatabaseReference reference;

    int[] images={
            R.drawable.medicine,R.drawable.others,R.drawable.surgery,
            R.drawable.oxygen,R.drawable.baby_product,R.drawable.equipment
    };

    String[] category;
    CategoryAdapter categoryAdapter;

    SearchView search;

    NotificationBadge badge;
    FrameLayout btnCart;
    ImageView btnBack;

    IProductListener iProductListener;
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
        countCartItem();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        btnBack=findViewById(R.id.backBtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        category=getResources().getStringArray(R.array.category);



        categoryAdapter=new CategoryAdapter(ShopActivity.this,category,images);

        categoryRecycler=findViewById(R.id.categoryRecycler);


        categoryRecycler.setHasFixedSize(true);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(ShopActivity.this,LinearLayoutManager.HORIZONTAL,false));
        categoryRecycler.setAdapter(categoryAdapter);





        categoryAdapter.setOnclickListener(new CategoryAdapter.onClickListener() {
            @Override
            public void click(int position,String category) {
                //Toast.makeText(ShopActivity.this, ""+category, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(ShopActivity.this, CategoryActivity.class);
                intent.putExtra("category",category);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);

            }
        });

        search = findViewById(R.id.search);
        search.clearFocus();




        recyclerViewPopular=findViewById(R.id.proudctRecycler_ID);
        badge=findViewById(R.id.badge);
        btnCart=findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ShopActivity.this, CartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
            }
        });



         productList=new ArrayList<>();


        init1();
        LoadDrinkFormFirebase();
        countCartItem();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                searchv(s);
                return true;
            }
        });

    }

    private void LoadDrinkFormFirebase() {
        List<Product> drinkModels=new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("product image")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                Product product=dataSnapshot.getValue(Product.class);
                                product.setKey(dataSnapshot.getKey());
                                drinkModels.add(product);
                            }
                            iProductListener.onDrinkLoadSuccess(drinkModels);
                        }else {
                            iProductListener.onDrinkLoadFailed("We can not find");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iProductListener.onDrinkLoadFailed(error.getMessage());

                    }
                });
    }

    private void init1() {
        iProductListener=this;
        iCartListener=this;

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerViewPopular.setLayoutManager(gridLayoutManager);
        recyclerViewPopular.addItemDecoration(new SpaceItemDecoration());
    }






    @Override
    public void onDrinkLoadSuccess(List<Product> productList) {
        ProductAdapter productAdapter=new ProductAdapter(this,productList,iCartListener);
        recyclerViewPopular.setAdapter(productAdapter);
    }


    private void searchv(String str) {
        List<Product> products = new ArrayList<>();
        for (Product object : productList) {
            if (object.getLeft_name().toLowerCase().contains(str.toLowerCase())) {
                products.add(object);
            }
        }
        productAdapter=new ProductAdapter(ShopActivity.this,products,iCartListener);
        recyclerViewPopular.setAdapter(productAdapter);
    }

    @Override
    public void onDrinkLoadFailed(String message) {

    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {
        int sum=0;
        for (CartModel cartModel:cartModelList)
            sum+=cartModel.getQuantity();
        badge.setNumber(sum);
    }

    @Override
    public void onCartLoadFailed(String message) {
        Toast.makeText(ShopActivity.this,message,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        countCartItem();
    }

    private void countCartItem() {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String myid=firebaseUser.getUid();
        List<CartModel> cartModelList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Cart").child(myid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            CartModel cartModel=dataSnapshot.getValue(CartModel.class);
                            cartModel.setKey(dataSnapshot.getKey());
                            cartModelList.add(cartModel);
                        }
                        iCartListener.onCartLoadSuccess(cartModelList);
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