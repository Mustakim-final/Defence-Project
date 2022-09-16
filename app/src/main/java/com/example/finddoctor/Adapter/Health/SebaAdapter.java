package com.example.finddoctor.Adapter.Health;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.Model.Seba;
import com.example.finddoctor.R;
import com.example.finddoctor.SebaFecilitiesActivity;

import java.util.List;

public class SebaAdapter extends RecyclerView.Adapter<SebaAdapter.MyHolder>{
    Context context;
    List<Seba> sebaList;

    public SebaAdapter(Context context, List<Seba> sebaList) {
        this.context = context;
        this.sebaList = sebaList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.seba_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Seba seba=sebaList.get(position);


        if (seba.getValue1().equals("শাপলা")){
            holder.relativeLayout.setBackgroundResource(R.drawable.shaplabackgorund);
        }else if (seba.getValue1().equals("গোলাপ")){
            holder.relativeLayout.setBackgroundResource(R.drawable.gulapbackgound);
        }else if (seba.getValue1().equals("প্রজাপতি")){
            holder.relativeLayout.setBackgroundResource(R.drawable.projapotibackground);
        }else if (seba.getValue1().equals("রজনীগন্ধা")){
            holder.relativeLayout.setBackgroundResource(R.drawable.rojonigondhabackground);
        }else if (seba.getValue1().equals("জেসমিন")){
            holder.relativeLayout.setBackgroundResource(R.drawable.jesminbackground);
        }




        holder.titleText.setText(seba.getValue1());
        holder.appointmentText.setText(seba.getValue2());
        holder.validationText.setText(seba.getValue3());
        holder.prizeText.setText(seba.getValue4()+" টাকা");


    }

    @Override
    public int getItemCount() {
        return sebaList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titleText,appointmentText,validationText,prizeText;
        RelativeLayout relativeLayout;
        LinearLayout linearLayout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            titleText=itemView.findViewById(R.id.health_Title);
            appointmentText=itemView.findViewById(R.id.health_appointment);
            validationText=itemView.findViewById(R.id.health_validation);
            prizeText=itemView.findViewById(R.id.health_prize);
            relativeLayout=itemView.findViewById(R.id.health_relativeLayout);
            linearLayout=itemView.findViewById(R.id.health_linerlayout_ID);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Seba seba=sebaList.get(getAdapterPosition());
            String type=seba.getValue1();
            Intent intent=new Intent(context, SebaFecilitiesActivity.class);
            intent.putExtra("type",type);
            context.startActivity(intent);
        }
    }
}
