package com.example.finddoctor.Adapter.Shop;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.EventBus.MyUpdateCartEvent;
import com.example.finddoctor.Listener.ICartListener;
import com.example.finddoctor.Listener.IRecyclerListener;
import com.example.finddoctor.Model.CartModel;
import com.example.finddoctor.Model.Product;
import com.example.finddoctor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder>{
    Context context;
    List<Product> productList;
    ICartListener iCartListener;

    public ProductAdapter(Context context, List<Product> productList, ICartListener iCartListener) {
        this.context = context;
        this.productList = productList;
        this.iCartListener = iCartListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Product product=productList.get(position);
        Glide.with(context).load(product.getLeft_image()).into(holder.productImage);
        holder.nameText.setText(product.getLeft_name());
        holder.prizeText.setText(product.getLeft_prize()+" টাকা");


        holder.setiRecyclerListener(new IRecyclerListener() {
            @Override
            public void OnRecyclerCLick(View view, int position) {
                addCart(productList.get(position));
            }
        });

    }

    private void addCart(Product product) {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String myid=firebaseUser.getUid();
        DatabaseReference userCart= FirebaseDatabase.getInstance().getReference("Cart").child(myid);
        userCart.child(product.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            CartModel cartModel=snapshot.getValue(CartModel.class);
                            Map<String,Object> updateData=new HashMap<>();
                            updateData.put("quantity",cartModel.getQuantity());
                            updateData.put("totalPrice",cartModel.getQuantity()*Float.parseFloat(cartModel.getLeft_prize()));
                            userCart.child(product.getKey())
                                    .updateChildren(updateData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                           iCartListener.onCartLoadFailed("Add to Cart Success");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            iCartListener.onCartLoadFailed(e.getMessage());
                                        }
                                    });
                        }else {

                            CartModel cartModel=new CartModel();
                            cartModel.setLeft_name(product.getLeft_name());
                            cartModel.setLeft_image(product.getLeft_image());
                            cartModel.setKey(product.getKey());
                            cartModel.setLeft_prize(product.getLeft_prize());
                            cartModel.setQuantity(1);
                            cartModel.setTotalPrice(Float.parseFloat(product.getLeft_prize()));

                            userCart.child(product.getKey())
                                    .setValue(cartModel)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            iCartListener.onCartLoadFailed("Add to Cart Success");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            iCartListener.onCartLoadFailed(e.getMessage());
                                        }
                                    });
                        }
                        EventBus.getDefault().postSticky(new MyUpdateCartEvent());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iCartListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView productImage;
        TextView nameText,prizeText;

        IRecyclerListener iRecyclerListener;

        public void setiRecyclerListener(IRecyclerListener iRecyclerListener) {
            this.iRecyclerListener = iRecyclerListener;
        }

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.productImage_ID);
            nameText=itemView.findViewById(R.id.productName_ID);
            prizeText=itemView.findViewById(R.id.productPrize_ID);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            iRecyclerListener.OnRecyclerCLick(view,getAdapterPosition());
        }
    }




}
