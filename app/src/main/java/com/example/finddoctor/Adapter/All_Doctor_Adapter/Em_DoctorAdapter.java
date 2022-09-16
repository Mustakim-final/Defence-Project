package com.example.finddoctor.Adapter.All_Doctor_Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Emergency__PrescriptionActivity;
import com.example.finddoctor.Model.All_Doctor;
import com.example.finddoctor.Model.Emergency_prescription;
import com.example.finddoctor.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Em_DoctorAdapter extends RecyclerView.Adapter<Em_DoctorAdapter.MyHolder>{
    Context context;
    List<Emergency_prescription> emergency_prescriptionList;

    String userID;

    public Em_DoctorAdapter(Context context, List<Emergency_prescription> emergency_prescriptionList) {
        this.context = context;
        this.emergency_prescriptionList = emergency_prescriptionList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.emergency_doctor_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Emergency_prescription emergency_prescription=emergency_prescriptionList.get(position);

        if (emergency_prescription.getImageUrl().equals("imageUrl")){
            holder.circleImageView.setImageResource(R.drawable.ic_baseline_perm_identity_24);
        }else {
            Glide.with(context).load(emergency_prescription.getImageUrl()).into(holder.circleImageView);
        }

        holder.nameText.setText(emergency_prescription.getName());
        holder.informationText.setText(emergency_prescription.getInformation());
        holder.dayText.setText(emergency_prescription.getDay());

        holder.feesText.setVisibility(View.GONE);
        holder.requestBtnText.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return emergency_prescriptionList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView circleImageView;
        TextView nameText,informationText,dayText,feesText,requestBtnText;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView=itemView.findViewById(R.id.em_doctorImage_ID);
            nameText=itemView.findViewById(R.id.em_doctorName_ID);
            informationText=itemView.findViewById(R.id.em_doctorInformation_ID);
            dayText=itemView.findViewById(R.id.em_doctor_day_ID);
            feesText=itemView.findViewById(R.id.em_doctorFees);
            requestBtnText=itemView.findViewById(R.id.em_doctorRequestBtn_ID);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Emergency_prescription emergency_prescription=emergency_prescriptionList.get(getAdapterPosition());
            userID=emergency_prescription.getMyId();

            Intent intent=new Intent(context, Emergency__PrescriptionActivity.class);
            intent.putExtra("userID",userID);
            context.startActivity(intent);
        }
    }
}
