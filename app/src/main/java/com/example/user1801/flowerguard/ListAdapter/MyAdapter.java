package com.example.user1801.flowerguard.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.user1801.flowerguard.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<JavaBeanAdapter> adapters;
    LayoutInflater layoutInflater;

    public MyAdapter(Context context, ArrayList<JavaBeanAdapter> adapters) {
        this.context = context;
        this.adapters = adapters;
    }

    @Override
    public int getCount() {
        return adapters.size();
    }

    @Override
    public Object getItem(int i) {
        return adapters.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = layoutInflater.inflate(R.layout.listview_history_list,viewGroup,false);
        }
        MyHolder myHolder = new MyHolder(view);
        myHolder.who.setText(adapters.get(i).getWho());

        return view;
    }
}