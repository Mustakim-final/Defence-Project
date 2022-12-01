package com.example.finddoctor.Adapter.Shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder>{
    Context context;
    String[] title;
    int[] image;
    public onClickListener listener;
    public CategoryAdapter(Context context, String[] title, int[] image) {
        this.context = context;
        this.title = title;
        this.image = image;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.imageView.setImageResource(image[position]);
        holder.textView.setText(title[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.categoryImage);
            textView=itemView.findViewById(R.id.categoryName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener!=null){
                int position=getAdapterPosition();
                String category=title[position];

                if (position!=RecyclerView.NO_POSITION){
                    listener.click(position,category);
                }
            }
        }
    }

    public interface onClickListener{
        void click(int position,String category);
    }

    public void setOnclickListener(onClickListener listener){
        this.listener=listener;
    }
}
