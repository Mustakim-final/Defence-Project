package com.example.finddoctor.Adapter.Pharmacy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Pharmacy;
import com.example.finddoctor.PharmacyApplyActivity;
import com.example.finddoctor.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.MyHolder>{
    Context context;
    List<Pharmacy> pharmacyList;



    String locality,address;

    public PharmacyAdapter(Context context, List<Pharmacy> pharmacyList) {
        this.context = context;
        this.pharmacyList = pharmacyList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.diagnostic_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Pharmacy pharmacy=pharmacyList.get(position);
        if (pharmacy.getPharmacy().equals("pharmacy")){
            holder.pharmacyImage.setImageResource(R.drawable.main_logu);
        }else {
            Glide.with(context).load(pharmacy.getPharmacy()).into(holder.pharmacyImage);
        }

        holder.textViewTitle.setText(pharmacy.getTitle());
        holder.textViewAddress.setText(pharmacy.getAddress());
        holder.textViewPhone.setText(pharmacy.getPhone());
    }

    @Override
    public int getItemCount() {
        return pharmacyList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView pharmacyImage;
        TextView textViewTitle,textViewAddress,textViewPhone;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            pharmacyImage=itemView.findViewById(R.id.diagnosticImage_ID);
            textViewTitle=itemView.findViewById(R.id.diagnosticTitle_ID);
            textViewAddress=itemView.findViewById(R.id.diagnosticAddress_ID);
            textViewPhone=itemView.findViewById(R.id.diagnosticPhone_ID);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Pharmacy pharmacy=pharmacyList.get(getAdapterPosition());
            String title=pharmacy.getTitle();
            String address=pharmacy.getAddress();
            String phone=pharmacy.getPhone();
            String id=pharmacy.getId();
            Intent intent=new Intent(context, PharmacyApplyActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("address",address);
            intent.putExtra("phone",phone);
            intent.putExtra("id",id);
            context.startActivity(intent);

        }
    }

}
