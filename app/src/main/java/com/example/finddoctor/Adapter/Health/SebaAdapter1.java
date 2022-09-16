package com.example.finddoctor.Adapter.Health;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.Model.Seba;
import com.example.finddoctor.R;

import java.util.List;

public class SebaAdapter1 extends RecyclerView.Adapter<SebaAdapter1.MyHolder>{
    Context context;
    List<Seba> sebaList;

    public SebaAdapter1(Context context, List<Seba> sebaList) {
        this.context = context;
        this.sebaList = sebaList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.seba_item1,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Seba seba=sebaList.get(position);

        /*
        if (seba.getValue5().equals("")){
            holder.textView.setVisibility(View.GONE);
        }else if (seba.getValue6().equals("")){
            holder.textView1.setVisibility(View.GONE);
        }else if (seba.getValue7().equals("")){
            holder.textView2.setVisibility(View.GONE);
        }else if (seba.getValue8().equals("")){
            holder.textView3.setVisibility(View.GONE);
        }else if (seba.getValue9().equals("")){
            holder.textView4.setVisibility(View.GONE);
        }else if (seba.getValue10().equals("")){
            holder.textView5.setVisibility(View.GONE);
        }else {
            holder.textView.setText(seba.getValue5());
            holder.textView1.setText(seba.getValue6());
            holder.textView2.setText(seba.getValue7());
            holder.textView3.setText(seba.getValue8());
            holder.textView4.setText(seba.getValue9());
            holder.textView5.setText(seba.getValue10());
        }

         */

        if (seba.getValue5().equals("")){
            holder.textView.setVisibility(View.GONE);
        }else {
            holder.textView.setText(seba.getValue5());
        }

        if (seba.getValue6().equals("")){
            holder.textView1.setVisibility(View.GONE);
        }else {
            holder.textView1.setText(seba.getValue6());
        }

        if (seba.getValue7().equals("")){
            holder.textView2.setVisibility(View.GONE);
        }else {
            holder.textView2.setText(seba.getValue7());
        }

        if (seba.getValue8().equals("")){
            holder.textView3.setVisibility(View.GONE);
        }else {
            holder.textView3.setText(seba.getValue8());
        }

        if (seba.getValue9().equals("")){
            holder.textView4.setVisibility(View.GONE);
        }else {
            holder.textView4.setText(seba.getValue9());
        }

        if (seba.getValue10().equals("")){
            holder.textView5.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
        }else {
            holder.textView5.setText(seba.getValue10());
        }





    }

    @Override
    public int getItemCount() {
        return sebaList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView textView,textView1,textView2,textView3,textView4,textView5;
        ImageView imageView;
        View view;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.seba_text_ID);
            textView1=itemView.findViewById(R.id.seba_text1_ID);
            textView2=itemView.findViewById(R.id.seba_text2_ID);
            textView3=itemView.findViewById(R.id.seba_text3_ID);
            textView4=itemView.findViewById(R.id.seba_text4_ID);
            textView5=itemView.findViewById(R.id.seba_text5_ID);

            imageView=itemView.findViewById(R.id.seba_image5_ID);
            view=itemView.findViewById(R.id.seba_view5_ID);
        }
    }
}
