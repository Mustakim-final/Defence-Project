package com.example.finddoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finddoctor.R;

public class CorunaDoctorList extends BaseAdapter {
    Context context;
    String[] name;
    int[] photo;
    LayoutInflater inflater;

    public CorunaDoctorList(Context context, String[] name, int[] photo) {
        this.context = context;
        this.name = name;
        this.photo = photo;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.simple_layout_coruna_doctor,parent,false);
        }
        ImageView imageView=convertView.findViewById(R.id.corunaDocotrimageView_ID);
        TextView textView=convertView.findViewById(R.id.corunaDoctorPText_ID);

        imageView.setImageResource(photo[position]);
        textView.setText(name[position]);
        return convertView;
    }
}
