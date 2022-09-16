package com.example.finddoctor.Adapter.Blog;

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
import com.example.finddoctor.BlogDescriptionActivity;
import com.example.finddoctor.Model.Blog;
import com.example.finddoctor.Model.Pharmacy;
import com.example.finddoctor.R;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyHolder>{

    Context context;
    List<Blog> blogList;

    public BlogAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.blog_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Blog blog=blogList.get(position);
        if (blog.getImageUrl().equals("imageUrl")){
            holder.imageView.setImageResource(R.drawable.ic_baseline_perm_identity_24);
        }else {
            Glide.with(context).load(blog.getImageUrl()).into(holder.imageView);
        }
        holder.titleText.setText(blog.getTitle());
        holder.typeText.setText(blog.getType());
        holder.dateText.setText(blog.getDay()+"/"+blog.getMonth()+"/"+blog.getYear());

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView titleText,typeText,dateText;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.blogProfileImage);
            titleText=itemView.findViewById(R.id.blogTitle);
            typeText=itemView.findViewById(R.id.blogType);
            dateText=itemView.findViewById(R.id.blogDate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Blog blog=blogList.get(getAdapterPosition());
            String title=blog.getTitle();
            String type=blog.getType();

            Intent intent=new Intent(context, BlogDescriptionActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("type",type);
            context.startActivity(intent);
        }
    }
}
