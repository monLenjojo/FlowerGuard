package com.example.user1801.flowerguard.listAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataGetInFirebase {
    Context context;
    ListView listView;
    String firebaseUid;
    DatabaseReference databaseReference;
    ArrayList<JavaBeanAdapter> arrayList = new ArrayList<JavaBeanAdapter>();
    MyAdapter myAdapter;

    public DataGetInFirebase(Context context, ListView listView, String firebaseUid) {
        this.context = context;
        this.listView = listView;
        this.firebaseUid = firebaseUid;
        databaseReference = FirebaseDatabase.getInstance().getReference("doorHistory").child("doorLife").child(firebaseUid);
    }

    public void savedata(String name, String deviceName){
        JavaBeanAdapter javaBeanAdapter = new JavaBeanAdapter();
        javaBeanAdapter.setWho(name);
        javaBeanAdapter.setDeviceName(deviceName);

        databaseReference.push().setValue(javaBeanAdapter);
    }

    public void  refreshData(){
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getUpdata(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                getUpdata(dataSnapshot);
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

    public void getUpdata(DataSnapshot dataSnapshot){
//        arrayList.clear();

//        for (DataSnapshot data:dataSnapshot.getChildren()){
//            Log.i("DataSnapshot",data.getValue().toString());
//            JavaBeanAdapter javaBeanAdapter = new JavaBeanAdapter();
//            javaBeanAdapter.setWho(dataSnapshot.getValue(JavaBeanAdapter.class).getWho());
//            javaBeanAdapter.setDeviceName(dataSnapshot.getValue());
            arrayList.add(dataSnapshot.getValue(JavaBeanAdapter.class));
//        }
        if(arrayList.size()>0){
            myAdapter = new MyAdapter(context,arrayList);
            listView.setAdapter((ListAdapter) myAdapter);
        }else {
            Toast.makeText(context, "Not data", Toast.LENGTH_SHORT).show();
        }
    }
}
