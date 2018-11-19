package com.example.user1801.flowerguard.listAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user1801.flowerguard.R;

public class MyHolder {
    ImageView imageView;
    TextView deviceName;
    TextView who;
    TextView time;

    public MyHolder(View itemView) {
        imageView = itemView.findViewById(R.id.listView_history_list_image);
        deviceName = itemView.findViewById(R.id.listView_history_list_deviceName);
        who = itemView.findViewById(R.id.listView_history_list_who);
        time = itemView.findViewById(R.id.listView_history_list_time);
    }
}
