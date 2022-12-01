package com.example.finddoctor.Adapter.Diagnostic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.EventBus.MyUpdateCartEvent;
import com.example.finddoctor.Model.DiaCart;
import com.example.finddoctor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DIaCartAdapter extends RecyclerView.Adapter<DIaCartAdapter.MyHolder>{

    Context context;
    List<DiaCart> diaCartList;

    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    String myId=firebaseUser.getUid();

    public DIaCartAdapter(Context context, List<DiaCart> diaCartList) {
        this.context = context;
        this.diaCartList = diaCartList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_item_dia,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DiaCart diaCart=diaCartList.get(position);

        holder.itemText.setText(diaCart.getItem());
        holder.priceText.setText(diaCart.getPrice());

        holder.btnDelete.setOnClickListener(view -> {
            AlertDialog dialog;
            dialog=new AlertDialog.Builder(context)
                    .setTitle("Delete Item")
                    .setMessage("Do you really want to delete item")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        notifyItemRemoved(position);
                        deleteFormFirebase(diaCartList.get(position));
                        dialogInterface.dismiss();

                    }).create();
            dialog.show();
        });
    }

    private void deleteFormFirebase(DiaCart diaCart) {
        FirebaseDatabase.getInstance()
                .getReference("Diagnostic Cart")
                .child(myId)
                .child(diaCart.getKey())
                .removeValue()
                .addOnSuccessListener(aVoid->EventBus.getDefault().postSticky(new MyUpdateCartEvent()));

    }

    @Override
    public int getItemCount() {
        return diaCartList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView itemText,priceText;
        ImageView btnDelete;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            itemText=itemView.findViewById(R.id.diagnosticItem_ID);
            priceText=itemView.findViewById(R.id.itemFee_ID);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }
    }
}
