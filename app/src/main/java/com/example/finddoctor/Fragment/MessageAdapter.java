package com.example.finddoctor.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.Model.Chats;
import com.example.finddoctor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewAdapter> {

    Context context;
    List<Chats> chatsList;

    public static final int MEG_RIGHT=0;
    public static final int MEG_LEFT=1;

    public MessageAdapter(Context context, List<Chats> chatsList) {
        this.context = context;
        this.chatsList = chatsList;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MEG_RIGHT){
            View view= LayoutInflater.from(context).inflate(R.layout.chat_right,parent,false);
            return new ViewAdapter(view);
        }else {
            View view=LayoutInflater.from(context).inflate(R.layout.chat_left,parent,false);
            return new ViewAdapter(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {
        Chats chats=chatsList.get(position);
        holder.textView.setText(chats.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.text_ID);
        }
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (chatsList.get(position).getSender().equals(firebaseUser.getUid())){
            return MEG_RIGHT;
        }else {
            return MEG_LEFT;
        }
    }
}
