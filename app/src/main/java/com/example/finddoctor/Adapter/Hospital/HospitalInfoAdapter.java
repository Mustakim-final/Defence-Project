package com.example.finddoctor.Adapter.Hospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Hospital;
import com.example.finddoctor.R;

import java.util.List;

public class HospitalInfoAdapter extends RecyclerView.Adapter<HospitalInfoAdapter.MyHolder>{
    Context context;
    List<Hospital> hospitalList;

    public HospitalInfoAdapter(Context context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.hospital_itme1,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Hospital hospital=hospitalList.get(position);

        Glide.with(context).load(hospital.getHospital()).into(holder.imageViewHospital);
        holder.textViewTitle.setText(hospital.getTitle());
        holder.textViewAddress.setText(hospital.getAddress());
        holder.textViewPhone.setText(hospital.getPhone());
        holder.textViewInfo.setText(hospital.getInfo());
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageViewHospital;
        TextView textViewTitle,textViewAddress,textViewPhone,textViewInfo;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageViewHospital=itemView.findViewById(R.id.hospitalInfoImage);
            textViewTitle=itemView.findViewById(R.id.hospitalinfoTitle);
            textViewAddress=itemView.findViewById(R.id.hospitalinfoAddress);
            textViewPhone=itemView.findViewById(R.id.hospitalinfoPhone);
            textViewInfo=itemView.findViewById(R.id.hospitalinfo);
        }
    }
}
