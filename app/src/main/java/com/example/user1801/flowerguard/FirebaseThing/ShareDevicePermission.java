package com.example.user1801.flowerguard.firebaseThing;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShareDevicePermission{
    private String firebaseID;
    private String myFirebaseID;
    private String deviceID;
    private String deviceName;
    DatabaseReference shareList, onUsedList, myShareList;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public ShareDevicePermission(String myFirebaseID ,String firebaseID, String deviceID, String deviceName) {
        this.firebaseID = firebaseID;
        this.myFirebaseID = myFirebaseID;
        this.deviceID = deviceID;
        this.deviceName = deviceName;
        onUsedList = firebaseDatabase.getReference("deviceOnUsedList").child(myFirebaseID).child(deviceID);
        onUsedList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JavaBeanSetOnUsedDeviceList data = dataSnapshot.getValue(JavaBeanSetOnUsedDeviceList.class);
                addToShareList(data);
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
        shareList = firebaseDatabase.getReference("shareList").child(firebaseID).child("deviceData").child(deviceID);
        myShareList = firebaseDatabase.getReference("userData").child(myFirebaseID).child("myShareList").child(deviceID).child(firebaseID).child("deviceData");
    }

    private void addToShareList(JavaBeanSetOnUsedDeviceList data) {
        shareList.setValue(new JavaBeanSetShareList(data.getKey(),data.getMac(), myFirebaseID,deviceName,deviceID)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                myShareList.setValue(new JavaBeanSetMyShare(firebaseID,deviceID));
            }
        });
    }
}
