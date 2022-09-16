package com.example.finddoctor.Adapter.Question;

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
import com.example.finddoctor.Model.Question;
import com.example.finddoctor.R;
import com.example.finddoctor.S_AnswerActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class S_questionAdapter extends RecyclerView.Adapter<S_questionAdapter.MyHolder>{
    Context context;
    List<Question> questionList;


    public S_questionAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.s_question_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Question question=questionList.get(position);

        if (question.getImageUrl().equals("imageUrl")){
            holder.profileImage.setImageResource(R.drawable.ic_baseline_perm_identity_24);
        }else {
            Glide.with(context).load(question.getImageUrl()).into(holder.profileImage);
        }

        holder.textViewName.setText(question.getUsername());
        holder.textViewType.setText(question.getQuestionType());

        if (question.getQuestion().equals("")){
            holder.textViewQuestion.setVisibility(View.GONE);
        }else {
            holder.textViewQuestion.setText(question.getQuestion());
        }


        if (question.getQuestionImage().equals("questionImage")){
            holder.imageViewQuestion.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(question.getQuestionImage()).into(holder.imageViewQuestion);
        }


    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView profileImage;
        TextView textViewName,textViewQuestion,textViewType;
        ImageView imageViewQuestion,commentBtn;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            profileImage=itemView.findViewById(R.id.s_questionProfileImage_ID);
            textViewName=itemView.findViewById(R.id.s_questionName_ID);
            textViewQuestion=itemView.findViewById(R.id.s_question_ID);
            imageViewQuestion=itemView.findViewById(R.id.s_questionPhoto);
            textViewType=itemView.findViewById(R.id.s_questionType_ID);
            commentBtn=itemView.findViewById(R.id.s_questionCommentBtn_ID);
            commentBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Question question=questionList.get(getAdapterPosition());
            String id=question.getId();
            String type=question.getQuestionType();
            if (view.getId()==R.id.s_questionCommentBtn_ID){
                Intent intent=new Intent(context, S_AnswerActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("type",type);
                context.startActivity(intent);
            }
        }
    }
}
