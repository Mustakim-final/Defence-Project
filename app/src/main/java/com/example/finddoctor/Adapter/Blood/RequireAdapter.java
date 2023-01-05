package com.example.finddoctor.Adapter.Blood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Require;
import com.example.finddoctor.R;

import java.util.List;

public class RequireAdapter extends RecyclerView.Adapter<RequireAdapter.MyHolder>{

    Context context;
    List<Require> requireList;

    public RequireAdapter(Context context, List<Require> requireList) {
        this.context = context;
        this.requireList = requireList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.blood_itme_two,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Require require=requireList.get(position);

        if (require.getImageUrl().equals("imageUrl")){
            holder.imageView.setImageResource(R.drawable.main_logu);
        }else {
            Glide.with(context).load(require.getImageUrl()).into(holder.imageView);
        }
        holder.nameText.setText(require.getName());
        holder.bloodText.setText(require.getB_group()+"("+require.getB_type()+")");
        holder.dateText.setText(require.getB_datetime());
        holder.giftText.setText("উপহারের পরিমাণ "+require.getB_gift()+" টাকা");
        if (require.getB_gift().equals("")){
            holder.emergencyText.setText("for humanity");
        }else {
            holder.emergencyText.setText("I want "+require.getB_group()+" blood in emergency");
        }


        holder.descriptionText.setText(require.getB_description());
    }

    @Override
    public int getItemCount() {
        return requireList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView,callBtn;
        TextView nameText,bloodText,dateText,giftText,emergencyText,descriptionText;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.requireImage);
            nameText=itemView.findViewById(R.id.requireName);
            bloodText=itemView.findViewById(R.id.requireBloodType);
            dateText=itemView.findViewById(R.id.requireDate);
            giftText=itemView.findViewById(R.id.requireGift);
            emergencyText=itemView.findViewById(R.id.requireEmergency);
            descriptionText=itemView.findViewById(R.id.requireDescription);

            callBtn=itemView.findViewById(R.id.callImageBtn);

            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Require require=requireList.get(getAdapterPosition());
                    String phone=require.getB_number();

                    Intent intent=new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phone));
                    context.startActivity(intent);
                }
            });
        }
    }

}
