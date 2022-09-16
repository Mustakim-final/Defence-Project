package com.example.finddoctor.Adapter.Diagnostic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.EventBus.MyUpdateCartEvent;
import com.example.finddoctor.Listener.IDiaCartListener;
import com.example.finddoctor.Listener.IProductRecyclerListener;
import com.example.finddoctor.Model.CartDiagonstic;
import com.example.finddoctor.Model.DiaCart;
import com.example.finddoctor.Model.Diagnostic;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagnosticItemAdapter extends RecyclerView.Adapter<DiagnosticItemAdapter.MyHolder>{
    Context context;
    List<Diagnostic> diagnosticList;
    IDiaCartListener iDiaCartListener;

    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    String myId=firebaseUser.getUid();

    public DiagnosticItemAdapter(Context context, List<Diagnostic> diagnosticList, IDiaCartListener iDiaCartListener) {
        this.context = context;
        this.diagnosticList = diagnosticList;
        this.iDiaCartListener = iDiaCartListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.diagnostic_item1,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Diagnostic diagnostic=diagnosticList.get(position);

        holder.textViewPrice.setText(diagnostic.getPrice());
        holder.textViewItem.setText(diagnostic.getItem());

        holder.setiProductRecyclerListener((view, position1) -> {
            addCart(diagnosticList.get(position1));
        });



    }



    private void addCart(Diagnostic diagnostic) {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String myId=firebaseUser.getUid();

        DatabaseReference userCart=FirebaseDatabase.getInstance().getReference("Diagnostic Cart").child(myId);
        userCart.child(diagnostic.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            DiaCart diaCart=snapshot.getValue(DiaCart.class);
                            Map<String,Object> updateData=new HashMap<>();
                            updateData.put("quantity",diaCart.getQuantity());
                            updateData.put("totalPrice",diaCart.getQuantity()*Float.parseFloat(diaCart.getPrice()));
                            userCart.child(diagnostic.getKey())
                                    .updateChildren(updateData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            iDiaCartListener.onCartLoadFailed("Add to Cart Success");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            iDiaCartListener.onCartLoadFailed(e.getMessage());
                                        }
                                    });
                        }else {
                            DiaCart diaCart=new DiaCart();
                            diaCart.setItem(diagnostic.getItem());
                            diaCart.setPrice(diagnostic.getPrice());
                            diaCart.setTitle(diagnostic.getTitle());
                            diaCart.setQuantity(1);
                            diaCart.setTotalPrice(Float.parseFloat(diagnostic.getPrice()));

                            userCart.child(diagnostic.getKey())
                                    .setValue(diaCart)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            iDiaCartListener.onCartLoadFailed("Add to Cart Success");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            iDiaCartListener.onCartLoadFailed(e.getMessage());
                                        }
                                    });
                        }

                        EventBus.getDefault().postSticky(new MyUpdateCartEvent());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return diagnosticList.size();
    }

    public  class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView addBtn;
        TextView textViewItem,textViewPrice;
        IProductRecyclerListener iProductRecyclerListener;

        public void setiProductRecyclerListener(IProductRecyclerListener iProductRecyclerListener) {
            this.iProductRecyclerListener = iProductRecyclerListener;
        }

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            addBtn=itemView.findViewById(R.id.addBtn);
            textViewPrice=itemView.findViewById(R.id.itemPriceID);
            textViewItem=itemView.findViewById(R.id.itemTextID);
            addBtn.setOnClickListener(this);

        }




        @Override
        public void onClick(View view) {
            iProductRecyclerListener.OnRecyclerCLick(view,getAdapterPosition());

        }

    }



}
