package com.example.finddoctor.Adapter.Diagnostic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finddoctor.Model.DiagonsicTest;
import com.example.finddoctor.R;

import java.util.ArrayList;

public class TestDiagnosticAdapter extends RecyclerView.Adapter<TestDiagnosticAdapter.MyHolder> {
    Context context;
    DiagonsicTest[] diagonsicTests;

    ArrayList<DiagonsicTest> checkedTeacher=new ArrayList<>();

    public TestDiagnosticAdapter(Context context, DiagonsicTest[] diagonsicTests) {
        this.context = context;
        this.diagonsicTests = diagonsicTests;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.diagnostic_item1,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DiagonsicTest diagonsicTest=diagonsicTests[position];

        holder.checkBox.setText(diagonsicTest.getPrice());
    }

    @Override
    public int getItemCount() {
        return diagonsicTests.length;
    }

    public static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CheckBox checkBox;

        ItemClickListener itemClickListener;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            checkBox=itemView.findViewById(R.id.check_box);


            checkBox.setOnClickListener(this);
        }


        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.OnItemClick(view,getLayoutPosition());
        }

        interface ItemClickListener{
            void OnItemClick(View view,int pos);
        }
    }
}
