package com.example.finddoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.R;

public class BloodAdapter extends RecyclerView.Adapter<BloodAdapter.MyHolder>{

    Context context;
    String[] title;

    private onItemClickListener listener;

    public BloodAdapter(Context context, String[] title) {
        this.context = context;
        this.title = title;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.blood_itme,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView.setText(title[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.bloodText_ID);

            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener!=null){
                int position=getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    listener.onItemClick(position);
                }
            }
        }
    }


    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(onItemClickListener listener){
        this.listener=listener;
    }
}
