package com.example.user1801.flowerguard.firebaseThing;

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

public class LockButtonFuction {
    private Context context;
    private String firebaseUid;
    private String deviceID;
    ChaosWithBluetooth tryConnectHoltek;
    public LockButtonFuction() {
        super();
    }

    public LockButtonFuction(Context context, String firebaseUid, String deviceID, ChaosWithBluetooth chaosWithBluetooth) {
        this.context = context;
        this.firebaseUid = firebaseUid;
        this.deviceID = deviceID;
        tryConnectHoltek=chaosWithBluetooth;
        Log.d("TEST",deviceID);
        FirebaseDatabase.getInstance().getReference("deviceOnUsedList").child(firebaseUid).child(deviceID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JavaBeanSetOnUsedDeviceList data = dataSnapshot.getValue(JavaBeanSetOnUsedDeviceList.class);
                Log.d("TEST",data.getKey()+" // "+data.getMac());
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
        ChaosWithBluetooth chaosWithBluetooth = new ChaosWithBluetooth();
        if (!chaosWithBluetooth.isConnect()) {
            chaosWithBluetooth.connect(mac, context);
        }
        if (chaosWithBluetooth.isConnect()) {
        chaosWithBluetooth.tryHOLTEKmathLoop(context);
        }
    }
}
