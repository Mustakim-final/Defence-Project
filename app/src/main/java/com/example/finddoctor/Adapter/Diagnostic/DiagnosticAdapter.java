package com.example.finddoctor.Adapter.Diagnostic;

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
import com.example.finddoctor.DiagnosticItmeActivity;
import com.example.finddoctor.Model.Diagnostic;
import com.example.finddoctor.R;

import java.util.List;

public class DiagnosticAdapter extends RecyclerView.Adapter<DiagnosticAdapter.MyHolder>{

    Context context;
    List<Diagnostic> diagnosticList;


    public DiagnosticAdapter(Context context, List<Diagnostic> diagnosticList) {
        this.context = context;
        this.diagnosticList = diagnosticList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.diagnostic_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Diagnostic diagnostic=diagnosticList.get(position);
        Glide.with(context).load(diagnostic.getDiagnostic()).into(holder.diagnosticImage);

        holder.textViewTitle.setText(diagnostic.getTitle());
        holder.textViewAddress.setText(diagnostic.getAddress());
        holder.textViewPhone.setText(diagnostic.getPhone());
    }

    @Override
    public int getItemCount() {
        return diagnosticList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView diagnosticImage;
        TextView textViewTitle,textViewAddress,textViewPhone;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            diagnosticImage=itemView.findViewById(R.id.diagnosticImage_ID);
            textViewTitle=itemView.findViewById(R.id.diagnosticTitle_ID);
            textViewAddress=itemView.findViewById(R.id.diagnosticAddress_ID);
            textViewPhone=itemView.findViewById(R.id.diagnosticPhone_ID);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Diagnostic diagnostic=diagnosticList.get(getAdapterPosition());

            String title=diagnostic.getTitle();
            Intent intent=new Intent(context, DiagnosticItmeActivity.class);
            intent.putExtra("title",title);
            context.startActivity(intent);
        }
    }


}
