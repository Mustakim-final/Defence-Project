package com.example.finddoctor.Adapter.Profile;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Pharmacy;
import com.example.finddoctor.R;

import java.util.List;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.MyHolder>{

    Context context;
    List<Pharmacy> pharmacyList;
    private onItemClickListener listener;
    public PharmacyAdapter(Context context, List<Pharmacy> pharmacyList) {
        this.context = context;
        this.pharmacyList = pharmacyList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.hospital_itme,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Pharmacy pharmacy=pharmacyList.get(position);
        if (pharmacy.getPrescription().equals("prescription")){
            holder.prescriptionImage.setImageResource(R.drawable.main_logu);
        }else {
            Glide.with(context).load(pharmacy.getPrescription()).into(holder.prescriptionImage);
        }

        holder.pharmacyNameText.setText(pharmacy.getPharmacy_title());
        holder.pharmacyAddress.setText(pharmacy.getPharmacy_address()+"\n"+pharmacy.getNote());
        holder.pharmacyPhone.setText(pharmacy.getPharmacy_phone());


    }

    @Override
    public int getItemCount() {
        return pharmacyList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        ImageView prescriptionImage,callBtn;
        TextView pharmacyNameText,pharmacyAddress,pharmacyPhone;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            prescriptionImage=itemView.findViewById(R.id.hospitalImage);
            pharmacyNameText=itemView.findViewById(R.id.hospitalTitle);
            pharmacyAddress=itemView.findViewById(R.id.hospitalAddress);
            pharmacyPhone=itemView.findViewById(R.id.hospitalPhone);
            callBtn=itemView.findViewById(R.id.hospitalCall);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (listener!=null){
                int position=getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            listener.onDelete(position);
                            return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void onClick(View view) {

            if (listener!=null){
                int position=getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    listener.onItemClick(position);
                }
            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.setHeaderTitle("Options");
            MenuItem delete=contextMenu.add(Menu.NONE,1,1,"Delete");

            delete.setOnMenuItemClickListener(this);


        }
    }

    public interface onItemClickListener{
        void onItemClick(int position);
        void onDelete(int position);
    }

    public void setOnClickListener(onItemClickListener listener){
        this.listener=listener;
    }
}
