package com.example.finddoctor.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.Model.DatePik;
import com.example.finddoctor.R;

import java.util.Date;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyHolder>{

    Context context;
    List<DatePik> datePikList;
    private onItemClickListener listener;
    String userID;

    public DateAdapter(Context context, List<DatePik> datePikList) {
        this.context = context;
        this.datePikList = datePikList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.date_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DatePik datePik=datePikList.get(position);
        holder.date1Text.setText(datePik.getDate1());
        holder.date2Text.setText(datePik.getDate2());
        holder.date3Text.setText(datePik.getDate3());
        holder.date4Text.setText(datePik.getDate4());
        holder.date5Text.setText(datePik.getDate5());
        holder.date6Text.setText(datePik.getDate6());
        holder.date7Text.setText(datePik.getDate7());


    }

    @Override
    public int getItemCount() {
        return datePikList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout saturdayLayout,sundayLayout,mondayLayout,tuesdayLayout,wednesdayLayout,thursdayLayout,fridayLayout;
        TextView bar1Text,date1Text,bar2Text,date2Text,bar3Text,date3Text,bar4Text,date4Text,bar5Text,date5Text,bar6Text,date6Text,bar7Text,date7Text;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            bar1Text=itemView.findViewById(R.id.bar8_ID);
            bar2Text=itemView.findViewById(R.id.bar9_ID);
            bar3Text=itemView.findViewById(R.id.bar10_ID);
            bar4Text=itemView.findViewById(R.id.bar11_ID);
            bar5Text=itemView.findViewById(R.id.bar12_ID);
            bar6Text=itemView.findViewById(R.id.bar13_ID);
            bar7Text=itemView.findViewById(R.id.bar14_ID);


            date1Text=itemView.findViewById(R.id.time1_ID);
            date2Text=itemView.findViewById(R.id.time2_ID);
            date3Text=itemView.findViewById(R.id.time3_ID);
            date4Text=itemView.findViewById(R.id.time4_ID);
            date5Text=itemView.findViewById(R.id.time5_ID);
            date6Text=itemView.findViewById(R.id.time6_ID);
            date7Text=itemView.findViewById(R.id.time7_ID);

            saturdayLayout=itemView.findViewById(R.id.saturday1Layout_ID);
            sundayLayout=itemView.findViewById(R.id.sunday2Layout_ID);
            mondayLayout=itemView.findViewById(R.id.monday1Layout_ID);
            tuesdayLayout=itemView.findViewById(R.id.tuesDay1Layout_ID);
            wednesdayLayout=itemView.findViewById(R.id.wednesday1Layout_ID);
            thursdayLayout=itemView.findViewById(R.id.thursday1Layout_ID);
            fridayLayout=itemView.findViewById(R.id.friday1Layout_ID);



           saturdayLayout.setOnClickListener(this);
           sundayLayout.setOnClickListener(this);
           mondayLayout.setOnClickListener(this);
           tuesdayLayout.setOnClickListener(this);
           wednesdayLayout.setOnClickListener(this);
           thursdayLayout.setOnClickListener(this);
           fridayLayout.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {

            String date1=date1Text.getText().toString();
            String date2=date2Text.getText().toString();
            String date3=date3Text.getText().toString();
            String date4=date4Text.getText().toString();
            String date5=date5Text.getText().toString();
            String date6=date6Text.getText().toString();
            String date7=date7Text.getText().toString();


            if (view.getId()==R.id.saturday1Layout_ID){

                saturdayLayout.setBackgroundResource(R.drawable.btn_background);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);

                if (listener!=null){

                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(1,date1);
                    }
                }
            }else if (view.getId()==R.id.sunday2Layout_ID){

                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.btn_background);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);

                if (listener!=null){

                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        listener.onItmeCLick1(2,date2);
                    }
                }
            }else if (view.getId()==R.id.monday1Layout_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.btn_background);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);
                if (listener!=null){

                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        listener.onItmeCLick2(3,date3);
                    }
                }
            }else if (view.getId()==R.id.tuesDay1Layout_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.btn_background);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);
                if (listener!=null){

                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        listener.onItmeCLick3(4,date4);
                    }
                }
            }else if (view.getId()==R.id.wednesday1Layout_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.btn_background);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);
                if (listener!=null){

                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        listener.onItmeCLick4(5,date5);
                    }
                }
            }else if (view.getId()==R.id.thursday1Layout_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.btn_background);
                fridayLayout.setBackgroundResource(R.drawable.backgorund_white);
                if (listener!=null){

                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        listener.onItmeCLick5(6,date6);
                    }
                }
            }else if (view.getId()==R.id.friday1Layout_ID){
                saturdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                sundayLayout.setBackgroundResource(R.drawable.backgorund_white);
                mondayLayout.setBackgroundResource(R.drawable.backgorund_white);
                tuesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                wednesdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                thursdayLayout.setBackgroundResource(R.drawable.backgorund_white);
                fridayLayout.setBackgroundResource(R.drawable.btn_background);
                if (listener!=null){

                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        listener.onItmeCLick6(7,date7);
                    }
                }
            }



        }


    }

    public interface onItemClickListener{
        void onItemClick(int position,String date1);
        void onItmeCLick1(int position,String date2);
        void onItmeCLick2(int position,String date3);
        void onItmeCLick3(int position,String date4);
        void onItmeCLick4(int position,String date5);
        void onItmeCLick5(int position,String date6);
        void onItmeCLick6(int position,String date7);
    }

    public void setOnClickListener(onItemClickListener listener){
        this.listener=listener;
    }
}
