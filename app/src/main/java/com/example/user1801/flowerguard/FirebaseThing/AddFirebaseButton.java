package com.example.user1801.flowerguard.firebaseThing;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user1801.flowerguard.bluetoothChaos.ChaosWithBluetooth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFirebaseButton {
    Context context;
    String firebaseUid;
    LinearLayout linearLayoutLock;
    int dynamicButtonNum = 0;
    ChaosWithBluetooth tryConnectHoltek = new ChaosWithBluetooth();
    View.OnClickListener deviceButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button theButton = v.findViewById(v.getId());
            Toast.makeText(context, theButton.getText().toString(), Toast.LENGTH_SHORT).show();
//            DataGetInFirebase.savedata(userName,theButton.getText().toString());
            if(!tryConnectHoltek.isConnect()) {
            tryConnectHoltek.connect("98:D3:31:90:32:38", context);
            }
//            tryConnectHoltek.chaosMath();
//            tryConnectHoltek.ieee754Write(tryConnectHoltek.getU1());

//            tryConnectHoltek.ieee754Write(tryConnectHoltek.getX1());
            tryConnectHoltek.start(context);
            new AlertDialog.Builder(context).setTitle("發送").setMessage(String.valueOf(tryConnectHoltek.getU1()) + "\n" + String.valueOf(tryConnectHoltek.getX1())).show();
        }
    };

    public AddFirebaseButton(Context context, String firebaseUid, LinearLayout linearLayoutLock) {
        this.context = context;
        this.firebaseUid = firebaseUid;
        this.linearLayoutLock = linearLayoutLock;
    }

    public void daraReference() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseReference firebaseListener = FirebaseDatabase.getInstance().getReference("lockData").child(firebaseUid);
                firebaseListener.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        JavaBeanSetDevice data = dataSnapshot.getValue(JavaBeanSetDevice.class);
                        final Button newButton = new Button(context);
                        newButton.setText(data.getDeviceName());
                        newButton.setId(dynamicButtonNum);
                        newButton.setOnClickListener(deviceButtonListener);
                        dynamicButtonNum++;
                        linearLayoutLock.post(new Runnable() {
                            @Override
                            public void run() {
                                linearLayoutLock.addView(newButton);
                            }
                        });
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }).start();
    }
}
