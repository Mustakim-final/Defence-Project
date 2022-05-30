package com.example.finddoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finddoctor.HelthActivity;
import com.example.finddoctor.R;

public class HelthAdapter extends BaseAdapter {

    Context context;
    String[] name;
    int[] icon;

    private LayoutInflater layoutInflater;

    public HelthAdapter(Context context, String[] name, int[] icon) {
        this.context = context;
        this.name = name;
        this.icon = icon;
    }

    @Override
    public int getCount() {
        return name.length ;
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
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= layoutInflater.inflate(R.layout.helth_simple_design,parent,false);
        }

        ImageView imageView=convertView.findViewById(R.id.imageView_ID);
        TextView textView=convertView.findViewById(R.id.fastText_ID);

        imageView.setImageResource(icon[position]);
        textView.setText(name[position]);

        return convertView;
    }
}
