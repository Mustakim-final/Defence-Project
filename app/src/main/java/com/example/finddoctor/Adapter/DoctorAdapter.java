package com.example.finddoctor.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.Model.Doctor;
import com.example.finddoctor.R;
import com.example.finddoctor.SerilaIntroductionActivity;
import com.example.finddoctor.StartActivity;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    Context context;
    List<Doctor> doctorList;
    String doctorID;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.simple_layout_heart_doctor,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doctor doctor=doctorList.get(position);

        holder.nameText.setText(doctor.getName());
        holder.profileText.setText(doctor.getInformation());
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameText,profileText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText=itemView.findViewById(R.id.heartDoctorNameText_ID);
            profileText=itemView.findViewById(R.id.heartInfotDoctorPText_ID);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, StartActivity.class);
            intent.putExtra("serial",1);
            context.startActivity(intent);
        }
    }

}
