package com.example.user1801.flowerguard;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.user1801.flowerguard.bluetoothChaos.ChaosWithBluetooth;
import com.example.user1801.flowerguard.bluetoothThing.BluetoothTools;
import com.example.user1801.flowerguard.firebaseThing.AddDevice;
import com.example.user1801.flowerguard.firebaseThing.AddFirebaseButton;
import com.example.user1801.flowerguard.firebaseThing.JavaBeanSetDevice;
import com.example.user1801.flowerguard.listAdapter.DataGetInFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    String log = "ImageButtonListener";
    FirebaseUser firebaseAuth;
    BluetoothTools a;
    LinearLayout linearLayoutLock, linearLayoutCamera, linearLayoutHistory, linearLayoutShare,linearLayoutLockMy,linearLayoutLockShare;
    String checkBoxString;
    private String firebaseUid, userName, userEmail;

    ListView listView_history;
    DataGetInFirebase DataGetInFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewDevice();
            }
        });
        Intent page= getIntent();
        firebaseUid = page.getStringExtra("firebaseUid");
        userEmail = page.getStringExtra("userEmail");
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUid = firebaseAuth.getUid();
        userEmail = firebaseAuth.getEmail();
        findView();
        AddFirebaseButton addFirebaseButton = new AddFirebaseButton(this,firebaseUid);
        addFirebaseButton.dataReference(linearLayoutLockMy);
        addFirebaseButton.getShareDataReference(linearLayoutLockShare);
        ListView historyList = findViewById(R.id.listView_historyList);
        DataGetInFirebase = new DataGetInFirebase(this, historyList, firebaseUid);
        DataGetInFirebase.refreshData();
}

    @Override
    protected void onStart() {
        super.onStart();
        ImageView ivCode = (ImageView) findViewById(R.id.mQRCodeImg);
        BarcodeEncoder encoder = new BarcodeEncoder();
        try {
            Bitmap bit = encoder.encodeBitmap(firebaseUid, BarcodeFormat.QR_CODE,
                    250, 250);
            ivCode.setImageBitmap(bit);
        } catch (WriterException e) {
            e.printStackTrace();
        }
//        SharedPreferences sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);
//        final UserInformationSharedPreferences updataUserLocalInfo = new UserInformationSharedPreferences(sharedPreferences);
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("userData").child(firebaseUid).child("information");
//        ref.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                JavaBeanSetPerson data = dataSnapshot.getValue(JavaBeanSetPerson.class);
//                updataUserLocalInfo.setInformation(data);
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    //    private void disPlayDialog(){
//        Dialog d = new Dialog(this);
//        d.setTitle("SaveData");
//
//    }

    private void addNewDevice() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        final View alertDialogView = layoutInflater.inflate(R.layout.alertdialog_input_product, null);
        final EditText newDeviceKey = alertDialogView.findViewById(R.id.addDeviceView_ed_deviceKey);
        final EditText newDeviceName = alertDialogView.findViewById(R.id.addDeviceView_ed_deviceName);

        checkBox_Lock = alertDialogView.findViewById(R.id.addDeviceView_checkBox_Lock);
        checkBox_check = alertDialogView.findViewById(R.id.addDeviceView_checkBox_check);
        checkBox_camera = alertDialogView.findViewById(R.id.addDeviceView_checkBox_camera);
        checkBox_Lock.setOnCheckedChangeListener(checkBoxListener);
        checkBox_check.setOnCheckedChangeListener(checkBoxListener);
        checkBox_camera.setOnCheckedChangeListener(checkBoxListener);
        alertDialog.setView(alertDialogView).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String deviceKey = newDeviceKey.getText().toString();
                final String deviceName = newDeviceName.getText().toString();
                if (TextUtils.isEmpty(deviceKey)) {
                    Toast.makeText(MainActivity.this, "Device key不可為空呦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(deviceName)) {
                    Toast.makeText(MainActivity.this, "幫您的設備取個名子吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(checkBoxString)) {
                    Toast.makeText(MainActivity.this, "要記得選擇產品呦", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AddDevice(new JavaBeanSetDevice(deviceName,deviceKey,checkBoxString),firebaseUid,MainActivity.this);
            }
        }).setNegativeButton("cancel", null).show();
    }

    private void findView() {
        linearLayoutLock = findViewById(R.id.lockButtonLayout);
        linearLayoutLockMy = findViewById(R.id.linearLayoutLockMy);
        linearLayoutLockShare = findViewById(R.id.linearLayoutLockShare);
        linearLayoutCamera = findViewById(R.id.cameraButtonLayout);
        linearLayoutHistory = findViewById(R.id.historyButtonLayout);
        linearLayoutShare = findViewById(R.id.shareButtonLayout);
//        ImageView controlLock = findViewById(R.id.controlLock);
//        controlLock.setOnClickListener(onControlFunctionClickListener);
        listView_history = findViewById(R.id.listView_historyList);
    }
//    private void mathLoop(float val) {
//        float x1;
//        Log.d("chaosTest", "Start");
//        chaosMath.chaosMath();
//        a.ieee754Write(chaosMath.getU1());
//        x1 = chaosMath.getX1();
//        Log.d("chaosTest", "u1\t"+chaosMath.getU1());
//        a.ieee754Write((1 + (x1 * x1)) * val);
//        int check = a.inputPort();
//        if(check==65){
//            Log.d("MCUReturn", Float.toString(a.getMCUreturn()));
//            mathLoop(val);
//        }else if(check==66){
//            Log.d("MCUReturn",Float.toString(a.getMCUreturn()));
//            Log.d("MCUReturn", "=============Lock one Open=============");
//        }else if(check==67){
//            Log.d("MCUReturn", Float.toString(a.getMCUreturn()));
//            Log.d("MCUReturn", "=============Lock two Open=============");
//        }else if(check==68){
//            Log.d("MCUReturn", Float.toString(a.getMCUreturn()));
//            Log.d("MCUReturn", "=============Lock there Open=============");
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case  R.id.action_bluetooth:
                    Log.d(log, "click bluetooth button");
                    BluetoothTools b = new BluetoothTools();
                    b.reverseBluetooth();
                return true;
            case R.id.action_lock:
                Log.d(log, "click lock button");
                linearLayoutLock.setVisibility(View.VISIBLE);
                linearLayoutCamera.setVisibility(View.GONE);
                linearLayoutHistory.setVisibility(View.GONE);
                linearLayoutShare.setVisibility(View.GONE);
                Log.d("chaosTest", "DONE");
                return true;
            case R.id.action_history:
                Log.d(log, "click history button");
                linearLayoutLock.setVisibility(View.GONE);
                linearLayoutCamera.setVisibility(View.GONE);
                linearLayoutHistory.setVisibility(View.VISIBLE);
                linearLayoutShare.setVisibility(View.GONE);
                return true;
            case R.id.action_share:
                Log.d(log, "click share button");
                linearLayoutLock.setVisibility(View.GONE);
                linearLayoutCamera.setVisibility(View.GONE);
                linearLayoutHistory.setVisibility(View.GONE);
                linearLayoutShare.setVisibility(View.VISIBLE);
                return true;
            case R.id.action_camera:
                Log.d(log, "click camera button");
                linearLayoutLock.setVisibility(View.GONE);
                linearLayoutCamera.setVisibility(View.VISIBLE);
                linearLayoutHistory.setVisibility(View.GONE);
                linearLayoutShare.setVisibility(View.GONE);
                return true;
            case R.id.action_account:
                Intent page = new Intent(MainActivity.this, UserInformationActivity.class);
                page.putExtra("firebaseUid",firebaseUid);
                startActivity(page);
                return true;
//            case R.id.action_settings:
//                return true;
            case R.id.action_logout:
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                SharedPreferences sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);
                sharedPreferences.edit().clear();
                Intent logoutPage = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logoutPage);
                MainActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    View.OnClickListener onControlFunctionClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.controlLock:
//                    if (MainActivity.isConnect()) {
////                        if (MainActivity.start(MainActivity.this)) {
////                            JavaBeanSetHistory data = new JavaBeanSetHistory(userName,firebaseUid,userEmail,"Open",);
////                            databaseReference = firebaseDatabase.getReference("doorLife");
////                            databaseReference.child(firebaseUid).push().setValue(data);
////                        } else {
////                            Toast.makeText(MainActivity.this, "解鎖失敗", Toast.LENGTH_SHORT).show();
////                            JavaBeanSetHistory data = new JavaBeanSetHistory(firebaseUid, firebaseAuth.getEmail(), "fail");
////                            databaseReference = firebaseDatabase.getReference("doorLife");
////                            databaseReference.child(firebaseUid).push().setValue(data);
////                        }
//                    } else {
//                        Toast.makeText(MainActivity.this, "hava to connected first.", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
//        }
//    };

    CheckBox checkBox_Lock, checkBox_check, checkBox_camera;
    CheckBox.OnCheckedChangeListener checkBoxListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.addDeviceView_checkBox_Lock:
                    if (isChecked) {
                        checkBox_check.setChecked(false);
                        checkBox_camera.setChecked(false);
                    }
                    break;
                case R.id.addDeviceView_checkBox_check:
                    if (isChecked) {
                        checkBox_Lock.setChecked(false);
                        checkBox_camera.setChecked(false);
                    }
                    break;
                case R.id.addDeviceView_checkBox_camera:
                    if (isChecked) {
                        checkBox_Lock.setChecked(false);
                        checkBox_check.setChecked(false);
                    }
                    break;
                case R.id.addDeviceView_checkBox_Future:
                    break;
            }
            checkBoxString = buttonView.getText().toString();
            Log.e("CheckBox", "choose： " + checkBoxString);
        }
    };

    public void buttonOnClick(View view) {
        Intent i = new Intent("la.droid.qr.scan");    //使用QRDroid的掃描功能
        i.putExtra("la.droid.qr.complete", true);   //完整回傳，不截掉scheme
        try {
            //開啟 QRDroid App 的掃描功能，等待 call back onActivityResult()
            startActivityForResult(i, 0);
        } catch (ActivityNotFoundException ex) {
            //若沒安裝 QRDroid，則開啟 Google Play商店，並顯示 QRDroid App
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=la.droid.qr"));
            startActivity(intent);
        }
    }
    ImageView mQRCodeImg;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if( 0==requestCode && null!=data && data.getExtras()!=null ) {
                //掃描結果存放在 key 為 la.droid.qr.result 的值中
                String result = data.getExtras().getString("la.droid.qr.result");
//                Log.d("TEST",result);
//                TextView a = findViewById(R.id.textView);
//                a.setText(result);  //將結果顯示在 TextVeiw 中
//                new ShareDevicePermission(firebaseUid,result);
                Intent shareToPage = new Intent(MainActivity.this,ShareToActivity.class);
                shareToPage.putExtra("firebaseUid",result);
                shareToPage.putExtra("myFirebaseUid",firebaseUid);
                startActivity(shareToPage);
            }
    }

    public void videoButtonOnClick(View view){
        VideoView videoView = findViewById(R.id.videoView);
        ImageButton videoButton = findViewById(R.id.videoButton);
        EditText rtspUrl = findViewById(R.id.rtspUrl);
            videoView.setVideoURI(Uri.parse(rtspUrl.getText().toString()));
            videoView.requestFocus();
            videoView.start();

    }



    @Override
    protected void onStop() {
        super.onStop();

    }
}
