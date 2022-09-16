package com.example.finddoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Advertise;
import com.example.finddoctor.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class AdvertiseAdapter extends SliderViewAdapter<AdvertiseAdapter.MyView> {

    Context context;
    List<Advertise> advertiseList;

    public AdvertiseAdapter(Context context, List<Advertise> advertiseList) {
        this.context = context;
        this.advertiseList = advertiseList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.image_slider_item,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(MyView viewHolder, int position) {
        Advertise advertise=advertiseList.get(position);

        Glide.with(context).load(advertise.getImageUrl()).into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return advertiseList.size();
    }

    public class MyView extends ViewHolder{
        ImageView imageView;
        public MyView(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView_ID);
        }
    }
}
