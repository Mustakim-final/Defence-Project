package com.example.finddoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.ApplyActivity;
import com.example.finddoctor.Model.All_Doctor;
import com.example.finddoctor.Prescription;
import com.example.finddoctor.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class All_doctor_Adapter extends RecyclerView.Adapter<All_doctor_Adapter.MyHolder> {

    Context context;
    List<All_Doctor> all_doctorList;
    String userID;

    public All_doctor_Adapter(Context context, List<All_Doctor> all_doctorList) {
        this.context = context;
        this.all_doctorList = all_doctorList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.emergency_doctor_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        All_Doctor all_doctor=all_doctorList.get(position);

        holder.nameText.setText(all_doctor.getName());
        holder.informationText.setText(all_doctor.getInformation());
        holder.dayText.setText("Available: "+all_doctor.getDay());

        holder.feesText.setText("ফি "+all_doctor.getFees());

        if (all_doctor.getImageUrl().equals("imageUrl")){
            holder.circleImageView.setImageResource(R.drawable.ic_baseline_perm_identity_24);

        }else {
            Glide.with(context).load(all_doctor.getImageUrl()).into(holder.circleImageView);
        }
    }

    @Override
    public int getItemCount() {
        return all_doctorList.size();
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

            requestBtnText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    All_Doctor all_doctor=all_doctorList.get(getAdapterPosition());
                    userID=all_doctor.getId();
                    Intent intent=new Intent(context, ApplyActivity.class);
                    intent.putExtra("userID",userID);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View view) {

        }
    }
}
