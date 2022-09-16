package com.example.finddoctor.Adapter.Blood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Users;
import com.example.finddoctor.R;

import java.util.List;

public class DonnerAdapter extends RecyclerView.Adapter<DonnerAdapter.MyHolder>{
    Context context;
    List<Users> usersList;

    public DonnerAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.blood_item_one,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Users users=usersList.get(position);

        if (users.getImageUrl().equals("imageUrl")){
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(context).load(users.getImageUrl()).into(holder.imageView);
        }

        holder.nameText.setText(users.getUsername());
        holder.bloodText.setText(users.getBlood());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameText,bloodText;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.donnerImage);
            nameText=itemView.findViewById(R.id.donnerName);
            bloodText=itemView.findViewById(R.id.donnerBloodType);
        }
    }
}
