package com.example.finddoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.Model.DatePik;
import com.example.finddoctor.R;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyHolder>{
    Context context;
    List<DatePik> datePikList;

    private onItemClickListener listener;

    public TimeAdapter(Context context, List<DatePik> datePikList) {
        this.context = context;
        this.datePikList = datePikList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.time_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        DatePik datePik=datePikList.get(position);
        holder.time1Text.setText(datePik.getTime1());
        holder.time2Text.setText(datePik.getTime2());
        holder.time3Text.setText(datePik.getTime3());
        holder.time4Text.setText(datePik.getTime4());
        holder.time5Text.setText(datePik.getTime5());
        holder.time6Text.setText(datePik.getTime6());
        holder.time7Text.setText(datePik.getTime7());

    }

    @Override
    public int getItemCount() {
        return datePikList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout saturdayLayout,sundayLayout,mondayLayout,tuesdayLayout,wednesdayLayout,thursdayLayout,fridayLayout;
        TextView bar8Text,time1Text,bar9Text,time2Text,bar10Text,time3Text,bar11Text,time4Text,bar12Text,time5Text,bar13Text,time6Text,bar14Text,time7Text;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            bar8Text=itemView.findViewById(R.id.bar8_ID);
            bar9Text=itemView.findViewById(R.id.bar9_ID);
            bar10Text=itemView.findViewById(R.id.bar10_ID);
            bar11Text=itemView.findViewById(R.id.bar11_ID);
            bar12Text=itemView.findViewById(R.id.bar12_ID);
            bar13Text=itemView.findViewById(R.id.bar13_ID);
            bar14Text=itemView.findViewById(R.id.bar14_ID);


            time1Text=itemView.findViewById(R.id.time1_ID);
            time2Text=itemView.findViewById(R.id.time2_ID);
            time3Text=itemView.findViewById(R.id.time3_ID);
            time4Text=itemView.findViewById(R.id.time4_ID);
            time5Text=itemView.findViewById(R.id.time5_ID);
            time6Text=itemView.findViewById(R.id.time6_ID);
            time7Text=itemView.findViewById(R.id.time7_ID);

            saturdayLayout=itemView.findViewById(R.id.saturday1Layout_ID);
            sundayLayout=itemView.findViewById(R.id.sunday2Layout_ID);
            mondayLayout=itemView.findViewById(R.id.monday1Layout_ID);
            tuesdayLayout=itemView.findViewById(R.id.tuesDay1Layout_ID);
            wednesdayLayout=itemView.findViewById(R.id.wednesday1Layout_ID);
            thursdayLayout=itemView.findViewById(R.id.thursday1Layout_ID);
            fridayLayout=itemView.findViewById(R.id.friday1Layout_ID);

            time1Text.setOnClickListener(this);
            time2Text.setOnClickListener(this);
            time3Text.setOnClickListener(this);
            time4Text.setOnClickListener(this);
            time5Text.setOnClickListener(this);
            time6Text.setOnClickListener(this);
            time7Text.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String time1=time1Text.getText().toString();
            String time2=time2Text.getText().toString();
            String time3=time3Text.getText().toString();
            String time4=time4Text.getText().toString();
            String time5=time5Text.getText().toString();
            String time6=time6Text.getText().toString();
            String time7=time7Text.getText().toString();
            DatePik datePik=datePikList.get(getAdapterPosition());
            String fee=datePik.getFees();

            if (view.getId()==R.id.time1_ID){
                saturdayLayout.setBackgroundResource(R.drawable.btn_background);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);
            }else if (view.getId()==R.id.time2_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.btn_background);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);
            }else if (view.getId()==R.id.time3_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.btn_background);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);
            }else if (view.getId()==R.id.time4_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.btn_background);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);
            }else if (view.getId()==R.id.time5_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.btn_background);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);
            }else if (view.getId()==R.id.time6_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.btn_background);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);
            }else if (view.getId()==R.id.time7_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.btn_background);
            }

            if (listener!=null){
                int position=getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    listener.onItemClick(position,time1,time2,time3,time4,time5,time6,time7,fee);
                }
            }

        }
    }

    public interface onItemClickListener{
        void onItemClick(int position,String time1,String time2,String time3,String time4,String time5,String time6,String time7,String fee);
    }

    public void setOnClickListener(onItemClickListener listener){
        this.listener=listener;
    }
}
