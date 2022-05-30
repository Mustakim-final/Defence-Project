package com.example.finddoctor.Fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.MessageWithDActivity;
import com.example.finddoctor.Model.Users;
import com.example.finddoctor.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    List<Users> userList;

    String userID;

    public UserAdapter(Context context, List<Users> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.doctorlist_chat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users=userList.get(position);

        userID=users.getId();

        holder.textView.setText(users.getUsername());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CircleImageView circleImageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.doctorImg_ID);
            textView=itemView.findViewById(R.id.doctorName_ID);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Users users=userList.get(getAdapterPosition());

            userID=users.getId();

            Intent intent=new Intent(context, MessageWithDActivity.class);
            intent.putExtra("userID",userID);
            context.startActivity(intent);

        }
    }
}
