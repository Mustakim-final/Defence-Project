package com.example.finddoctor.Adapter.All_Doctor_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Emergency_prescription;
import com.example.finddoctor.R;

import java.util.List;

public class Em_PresAdapter extends RecyclerView.Adapter<Em_PresAdapter.MyHolder> {
    Context context;
    List<Emergency_prescription> emergency_prescriptionList;

    public Em_PresAdapter(Context context, List<Emergency_prescription> emergency_prescriptionList) {
        this.context = context;
        this.emergency_prescriptionList = emergency_prescriptionList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.emergency_prescription_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Emergency_prescription emergency_prescription=emergency_prescriptionList.get(position);
        holder.prescriptionText.setText(emergency_prescription.getPrescription());
        holder.meetText.setText(emergency_prescription.getMeet());
        holder.dateText.setText(emergency_prescription.getDate());
        Glide.with(context).load(emergency_prescription.getImg_pres()).into(holder.imageViewPrecription);

    }

    @Override
    public int getItemCount() {
        return emergency_prescriptionList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageViewPrecription;
        TextView prescriptionText,meetText,dateText;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            prescriptionText=itemView.findViewById(R.id.em_prescription_ID);
            meetText=itemView.findViewById(R.id.em_meetLink_ID);
            imageViewPrecription=itemView.findViewById(R.id.em_prescriptionImage_ID);
            dateText=itemView.findViewById(R.id.date_and_time);
        }
    }
}
