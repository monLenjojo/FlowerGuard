package com.example.user1801.flowerguard.firebaseThing;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.user1801.flowerguard.bluetoothChaos.ChaosWithBluetooth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LockButtonFuction {
    private Context context;
    private String firebaseUid;
    private String deviceID;
    ChaosWithBluetooth chaosWithBluetooth = new ChaosWithBluetooth();
    public LockButtonFuction() {
        super();
    }

    public LockButtonFuction(Context context, String firebaseUid, String deviceID) {
        this.context = context;
        this.firebaseUid = firebaseUid;
        this.deviceID = deviceID;
        FirebaseDatabase.getInstance().getReference("deviceOnUsedList").child(firebaseUid).child(deviceID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JavaBeanSetOnUsedDeviceList data = dataSnapshot.getValue(JavaBeanSetOnUsedDeviceList.class);
                Log.d("TEST","Key："+ data.getKey()+"\tMac："+data.getMac());
                start(data.getMac(),data.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void start(String mac, String key) {
        if (!chaosWithBluetooth.isConnect()) {
            if (chaosWithBluetooth.connect(mac, context)){
                chaosWithBluetooth.tryHOLTEKmathLoop(context);
            }
        }else {
            if (chaosWithBluetooth.isConnect()) {
                chaosWithBluetooth.tryHOLTEKmathLoop(context);
            }
        }
    }
}
