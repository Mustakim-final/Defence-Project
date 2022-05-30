package com.example.finddoctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends BaseExpandableListAdapter {
    private Context context;
    List<String> header_list;
    HashMap<String,List<String>> child_list;

    public CustomAdapter(Context context, List<String> header_list, HashMap<String, List<String>> child_list) {
        this.context = context;
        this.header_list = header_list;
        this.child_list = child_list;
    }

    @Override
    public int getGroupCount() {
        return header_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child_list.get(header_list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header_list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_list.get(header_list.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerText= (String) getGroup(groupPosition);
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.expendablehader,null);
        }

        TextView textView=convertView.findViewById(R.id.headerText_ID);
        textView.setText(headerText);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childText= (String) getChild(groupPosition,childPosition);
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.expendablechild,null);
        }

        TextView textView=convertView.findViewById(R.id.childText_ID);
        textView.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
