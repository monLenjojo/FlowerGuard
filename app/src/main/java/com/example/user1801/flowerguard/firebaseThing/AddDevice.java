package com.example.user1801.flowerguard.firebaseThing;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddDevice {
    public String deviceID;
    public String deviceName;
    public String deviceModule;
    public String firebaseID;
    Context context;
    private DatabaseReference databaseReference;

    public AddDevice(JavaBeanSetDevice javaBeanSetDevice, String firebaseID, final Context context) {
        this.deviceID = javaBeanSetDevice.getDeviceID();
        this.deviceName = javaBeanSetDevice.getDeviceName();
        this.deviceModule = javaBeanSetDevice.getDeviceModule();
        this.firebaseID = firebaseID;
        this.context=context;
        databaseReference = FirebaseDatabase.getInstance().getReference("allDeviceList").child(deviceID);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JavaBeanSetAllDeviceList data = dataSnapshot.getValue(JavaBeanSetAllDeviceList.class);
                if (data.getOnUsed().compareTo("0")==0) {
                    //check the device is not used,call add
                    addDeviceToUsedList(data.getKey(),data.getMac());
                }else {
                    new AlertDialog.Builder(context).setMessage("請確認金鑰是否正確，\n或連繫客服人員").setPositiveButton("OK",null).show();
                }
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

    private void addDeviceToUsedList(String key,String mac) {
        FirebaseDatabase.getInstance().getReference("deviceOnUsedList").child(firebaseID).child(deviceID).child("deviceData")
                .setValue(new JavaBeanSetOnUsedDeviceList(key,mac)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    if (task.isSuccessful()) {
                        //usedList set OK, call add to my device
                        addToMyDevice();
                    }
                }
            }
        });
    }

    private void addToMyDevice() {
        FirebaseDatabase.getInstance().getReference("userData/"+firebaseID+"/myDevice").child(deviceID).setValue(new JavaBeanSetDevice(deviceName,deviceID,deviceModule)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    if (task.isSuccessful()) {
                        Map data = new HashMap<String,String>();
                        data.put("onUsed","1");
                        databaseReference.child("deviceData").updateChildren(data).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isComplete()) {
                                    if (task.isSuccessful()) {
                                        new AlertDialog.Builder(context).setMessage(deviceName+" is belong to you, enjoy!").show();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
