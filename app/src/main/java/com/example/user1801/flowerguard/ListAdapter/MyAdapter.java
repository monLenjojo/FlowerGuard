package com.example.user1801.flowerguard.ListAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user1801.flowerguard.FirebaseThing.JavaBeanSetHistory;
import com.example.user1801.flowerguard.R;

public class MyAdapter extends BaseAdapter {
    private JavaBeanSetHistory data;
    private LayoutInflater layoutInflater;
    private Context context;

    public MyAdapter(Context context,JavaBeanSetHistory data) {
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class LayoutInfo{
        public ImageView imageView;
        public TextView deviceName;
        public TextView who;
        public TextView time;
    }

    @Override
    public int getCount() {
        Log.d("MyAdapter getCount","Not used");
        return -1;
    }

    @Override
    public Object getItem(int getItem) {
        return data;
    }

    @Override
    public long getItemId(int getItemId) {
        return getItemId;
    }

    @Override
    public View getView(int getView, View view, ViewGroup viewGroup) {
        LayoutInfo layoutInfo = new LayoutInfo();
        view = layoutInflater.inflate(R.layout.listview_history_list,null);
        layoutInfo.deviceName = view.findViewById(R.id.listView_history_list_deviceName);
        layoutInfo.who = view.findViewById(R.id.listView_history_list_who);
        layoutInfo.time = view.findViewById(R.id.listView_history_list_time);
        layoutInfo.deviceName.setText(data.getDeviceName());
        layoutInfo.who.setText(data.getWho());
        layoutInfo.time.setText(data.getOpenTime()+" to "+(data.getCloseTime()!=null?data.getCloseTime():"~"));
        return view;
    }
}
