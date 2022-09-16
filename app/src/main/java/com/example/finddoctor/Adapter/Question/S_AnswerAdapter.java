package com.example.finddoctor.Adapter.Question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Answer;
import com.example.finddoctor.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class S_AnswerAdapter extends RecyclerView.Adapter<S_AnswerAdapter.MyHolder>{
    Context context;
    List<Answer> answerList;

    public S_AnswerAdapter(Context context, List<Answer> answerList) {
        this.context = context;
        this.answerList = answerList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.s_answer_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Answer answer=answerList.get(position);
        if (answer.getDoctorImage().equals("imageUrl")){
            holder.profileImage.setImageResource(R.drawable.ic_baseline_perm_identity_24);
        }else {
            Glide.with(context).load(answer.getDoctorImage()).into(holder.profileImage);
        }

        holder.textViewName.setText(answer.getDoctorName());
        holder.textViewType.setText(answer.getDoctorType());
        holder.textViewAnswer.setText(answer.getAnswer());
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        CircleImageView profileImage;
        TextView textViewName,textViewAnswer,textViewType;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            profileImage=itemView.findViewById(R.id.s_answerProfileImage_ID);
            textViewName=itemView.findViewById(R.id.s_answerProfileName_ID);
            textViewType=itemView.findViewById(R.id.s_answerType_ID);
            textViewAnswer=itemView.findViewById(R.id.s_answer_ID);
        }
    }
}
