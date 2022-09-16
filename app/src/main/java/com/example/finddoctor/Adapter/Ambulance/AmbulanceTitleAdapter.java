package com.example.finddoctor.Adapter.Ambulance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.AmbulanceDetailsActivity;
import com.example.finddoctor.HospitalDetailsActivity;
import com.example.finddoctor.Model.Hospital;
import com.example.finddoctor.R;

import java.util.List;

public class AmbulanceTitleAdapter extends RecyclerView.Adapter<AmbulanceTitleAdapter.MyHolder>{
    Context context;
    List<Hospital> hospitalList;
    public AmbulanceTitleAdapter(Context context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.hospital_itme,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Hospital hospital=hospitalList.get(position);
        if (hospital.getAmbulance().equals("ambulance")){
            holder.imageViewHospital.setImageResource(R.drawable.ambulance);
        }else {
            Glide.with(context).load(hospital.getAmbulance()).into(holder.imageViewHospital);
        }

        holder.textViewTitle.setText(hospital.getTitle());
        holder.textViewAddress.setText(hospital.getAddress());
        holder.textViewPhone.setText(hospital.getPhone());
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageViewHospital,imageViewCall;
        TextView textViewTitle,textViewAddress,textViewPhone;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageViewHospital=itemView.findViewById(R.id.hospitalImage);
            textViewTitle=itemView.findViewById(R.id.hospitalTitle);
            textViewAddress=itemView.findViewById(R.id.hospitalAddress);
            textViewPhone=itemView.findViewById(R.id.hospitalPhone);

            imageViewCall=itemView.findViewById(R.id.hospitalCall);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Hospital hospital= hospitalList.get(getAdapterPosition());
            String title=hospital.getTitle();
            String phone=hospital.getPhone();
            Intent intent=new Intent(context, AmbulanceDetailsActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("phone",phone);
            context.startActivity(intent);

            if (view.getId()==R.id.hospitalCall){
                String phone1=hospital.getPhone();
                Callbutton(phone1);
            }
        }
    }

    private void Callbutton(String phone1) {
        String dail = "tel:" + phone1;
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dail)));
    }

}
