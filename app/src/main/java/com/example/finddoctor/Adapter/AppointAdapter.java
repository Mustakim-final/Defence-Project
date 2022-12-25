package com.example.finddoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.MessageWithDActivity;
import com.example.finddoctor.Model.Appointment;
import com.example.finddoctor.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointAdapter extends RecyclerView.Adapter<AppointAdapter.MyHolder>{
    Context context;
    List<Appointment> appointmentList;

    public AppointAdapter(Context context, List<Appointment> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.appoint_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Appointment appointment=appointmentList.get(position);
        if (appointment.getImageUrl().equals("imageUrl")){
            holder.imageView.setImageResource(R.mipmap.ic_launcher_round);
        }else {
            Glide.with(context).load(appointment.getImageUrl()).into(holder.imageView);
        }

        holder.nameText.setText(appointment.getName());
        holder.dateText.setText(appointment.getDate());
        holder.meetText.setText(appointment.getMeet());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView imageView;
        TextView nameText,dateText,meetText;
        ImageView messageViewBtn;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.appointImage);
            nameText=itemView.findViewById(R.id.appointName);
            dateText=itemView.findViewById(R.id.appointDate);
            meetText=itemView.findViewById(R.id.appointMeetLink);
            messageViewBtn=itemView.findViewById(R.id.messageBtn_ID);

            messageViewBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Appointment appointment=appointmentList.get(getAdapterPosition());
            String doctorId=appointment.getD_id();
            Intent intent=new Intent(context,MessageWithDActivity.class);
            intent.putExtra("d_id",doctorId);
            context.startActivity(intent);
        }
    }
}
