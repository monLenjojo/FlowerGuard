package com.example.user1801.flowerguard.firebaseThing;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.user1801.flowerguard.bluetoothChaos.ChaosWithBluetooth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFirebaseButton {
    Context context;
    String firebaseUid;
    int dynamicButtonNum = 0;
    ChaosWithBluetooth chaosWithBluetooth = new ChaosWithBluetooth();

    public AddFirebaseButton(Context context, String firebaseUid) {
        this.context = context;
        this.firebaseUid = firebaseUid;
    }

    public void dataReference(final LinearLayout linearLayoutLock) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseReference firebaseListener = FirebaseDatabase.getInstance().getReference("userData/" + firebaseUid).child("myDevice");
                firebaseListener.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final JavaBeanSetDevice data = dataSnapshot.getValue(JavaBeanSetDevice.class);
                        final Button newButton = new Button(context);
                        newButton.setText(data.getDeviceName());
                        newButton.setId(Integer.parseInt(data.getDeviceID()));
                        newButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new LockButtonFuction(context, firebaseUid, data.getDeviceID());
                            }
                        });
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

    public void getShareDataReference(final LinearLayout linearLayoutLock) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseReference firebaseListener = FirebaseDatabase.getInstance().getReference("shareList/" + firebaseUid).child("deviceData");
                firebaseListener.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final JavaBeanSetShareList data = dataSnapshot.getValue(JavaBeanSetShareList.class);
                        final Button newButton = new Button(context);
                        newButton.setText(data.getDeviceName());
                        newButton.setId(Integer.parseInt(data.getDeviceID()));
                        newButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                linearLayoutLock.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (chaosWithBluetooth.connect(data.getMac(), context)) {
                                            chaosWithBluetooth.tryHOLTEKmathLoop(context);
                                        }
                                    }
                                });
                            }
                        });
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

    public void setShareDataReference(final LinearLayout linearLayoutLock, final String result) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseReference firebaseListener = FirebaseDatabase.getInstance().getReference("userData/" + firebaseUid).child("myDevice");
                firebaseListener.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final JavaBeanSetDevice data = dataSnapshot.getValue(JavaBeanSetDevice.class);
                        final Button newButton = new Button(context);
                        newButton.setText(data.getDeviceName());
                        newButton.setId(Integer.parseInt(data.getDeviceID()));
                        newButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ShareDevicePermission(firebaseUid, result, data.getDeviceID(), data.getDeviceName());
                            }
                        });
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

//    ChaosWithBluetooth tryConnectHoltek = new ChaosWithBluetooth();
//    View.OnClickListener deviceButtonListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Button theButton = v.findViewById(v.getId());
//
////            FirebaseDatabase.getInstance().getReference("deviceOnUsedList/"+firebaseUid).child(String.valueOf(v.getId()))
//
//
////            int i = 100;
////            for (int i1 = 0; i1 < i; i1++) {
////                FirebaseDatabase.getInstance().getReference("allDeviceList").child(String.valueOf(i1)).child("deviceData").setValue(
////                        new JavaBeanSetAllDeviceList("0",String.valueOf(i1),String.valueOf(i1)));
////            }
//            Toast.makeText(context, theButton.getText().toString(), Toast.LENGTH_SHORT).show();
////            DataGetInFirebase.savedata(userName,theButton.getText().toString());
//            if (!tryConnectHoltek.isConnect()) {
//                tryConnectHoltek.connect("98:D3:31:90:32:38", context);
//            }
////            tryConnectHoltek.chaosMath();
////            tryConnectHoltek.ieee754Write(tryConnectHoltek.getU1());
//
////            tryConnectHoltek.ieee754Write(tryConnectHoltek.getX1());
//            tryConnectHoltek.tryHOLTEKmathLoop(context);
////            new AlertDialog.Builder(context).setTitle("發送").setMessage(String.valueOf(tryConnectHoltek.getU1()) + "\n" + String.valueOf(tryConnectHoltek.getX1())).show();
//        }
//    };
}
