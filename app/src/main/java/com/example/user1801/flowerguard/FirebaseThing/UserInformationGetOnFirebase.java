package com.example.user1801.flowerguard.firebaseThing;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.user1801.flowerguard.MainActivity;
import com.example.user1801.flowerguard.localDatabase.UserInformationSharedPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class UserInformationGetOnFirebase {
    TextView txName,txEmail,txPhone,txAddress;
    DatabaseReference databaseReference;
    UserInformationSharedPreferences userInformationSharedPreferences;
    public UserInformationGetOnFirebase(String firebaseUid,TextView txName, TextView txEmail, TextView txPhone, TextView txAddress) {
        this.txName = txName;
        this.txEmail = txEmail;
        this.txPhone = txPhone;
        this.txAddress = txAddress;
        databaseReference = FirebaseDatabase.getInstance().getReference("userData").child(firebaseUid).child("information");
//        databaseReference.addChildEventListener();
    }

    public void updataNewInformation(String item,String value){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(item,value);
        databaseReference.child("personData").updateChildren(hashMap);
    }

    public void addListenter() {
        databaseReference.addChildEventListener(listener);
    }

    public void removeListenter(){
        databaseReference.removeEventListener(listener);
    }

    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            getData(dataSnapshot);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            getData(dataSnapshot);
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
    };

    private void getData(DataSnapshot dataSnapshot) {
        Log.d("TEST",dataSnapshot.getValue().toString());
        JavaBeanSetPerson data = dataSnapshot.getValue(JavaBeanSetPerson.class);
        if (!TextUtils.isEmpty(data.name)) {
        txName.setText(data.name);
        txEmail.setText(data.email);
        txPhone.setText(data.phone);
        txAddress.setText(data.address);
        }
    }
}
