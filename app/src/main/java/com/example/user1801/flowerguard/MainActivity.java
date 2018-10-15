package com.example.user1801.flowerguard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.user1801.flowerguard.bluetoothChaos.chaosWithBluetooth;
import com.example.user1801.flowerguard.bluetoothThing.bluetoothTools;
import com.example.user1801.flowerguard.chaosThing.ChaosMath;
import com.example.user1801.flowerguard.firebaseThing.jBeanSetDevice;
import com.example.user1801.flowerguard.firebaseThing.jBeanSetHistory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.util.Base64;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String log = "ImageButtonListener";
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseAuth;
    DatabaseReference databaseReference;
    ChaosMath chaosMath;
    bluetoothTools a;
    LinearLayout linearLayoutLock, linearLayoutCamera, linearLayoutHistory, linearLayoutShare;
    chaosWithBluetooth chaosWithBluetooth;
    String checkBoxString;
    View.OnClickListener onImageButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton_bluetooth:
                    Log.d(log, "click bluetooth button");
                    bluetoothTools b = new bluetoothTools();
                    b.reverseBluetooth();
                    break;
                case R.id.imageButton_nfc:
                    Log.d(log, "click nfc button");
                    Toast.makeText(MainActivity.this, "nfc button", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageButton_lock:
                    Log.d(log, "click lock button");
                    linearLayoutLock.setVisibility(View.VISIBLE);
                    linearLayoutCamera.setVisibility(View.GONE);
                    linearLayoutHistory.setVisibility(View.GONE);
                    linearLayoutShare.setVisibility(View.GONE);
                    Log.d("chaosTest", "DONE");
                    break;
                case R.id.imageButton_camera:
                    Log.d(log, "click camera button");
                    linearLayoutLock.setVisibility(View.GONE);
                    linearLayoutCamera.setVisibility(View.VISIBLE);
                    linearLayoutHistory.setVisibility(View.GONE);
                    linearLayoutShare.setVisibility(View.GONE);
//                    Toast.makeText(MainActivity.this, "camera button", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageButton_history:
                    Log.d(log, "click history button");
                    linearLayoutLock.setVisibility(View.GONE);
                    linearLayoutCamera.setVisibility(View.GONE);
                    linearLayoutHistory.setVisibility(View.VISIBLE);
                    linearLayoutShare.setVisibility(View.GONE);
//                    Toast.makeText(MainActivity.this, "history button", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageButton_share:
                    Log.d(log, "click share button");
                    linearLayoutLock.setVisibility(View.GONE);
                    linearLayoutCamera.setVisibility(View.GONE);
                    linearLayoutHistory.setVisibility(View.GONE);
                    linearLayoutShare.setVisibility(View.VISIBLE);
//                    Toast.makeText(MainActivity.this, "share button", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    View.OnClickListener onControlFunctionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.controlLock:
                    if (chaosWithBluetooth.start(MainActivity.this)) {
                        jBeanSetHistory data = new jBeanSetHistory(firebaseAuth.getUid(),firebaseAuth.getEmail(),"Open");
                        databaseReference = firebaseDatabase.getReference("doorLife");
                        databaseReference.child(firebaseAuth.getUid()).push().setValue(data);
                    } else {
                        Toast.makeText(MainActivity.this, "解鎖失敗", Toast.LENGTH_SHORT).show();
                        jBeanSetHistory data = new jBeanSetHistory(firebaseAuth.getUid(),firebaseAuth.getEmail(),"fail");
                        databaseReference = firebaseDatabase.getReference("doorLife");
                        databaseReference.child(firebaseAuth.getUid()).push().setValue(data);
                    }
                    break;
            }
        }
    };
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
            Log.e("CheckBox","choose： "+checkBoxString);
        }
    };

    View.OnClickListener deviceButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button theButton = findViewById(v.getId());
            Toast.makeText(MainActivity.this, theButton.getText(), Toast.LENGTH_SHORT).show();
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                addNewDevice();
            }
        });
//        fab.setVisibility(View.GONE);
        activityOriginalSetting();
        findView();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        chaosWithBluetooth = new chaosWithBluetooth();
        DatabaseReference firebaseListener = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid()).child("mydevice");
        firebaseListener.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                jBeanSetDevice data = dataSnapshot.getValue(jBeanSetDevice.class);
                Toast.makeText(MainActivity.this, data.getDeviceName(), Toast.LENGTH_SHORT).show();
                Button newButton = new Button(MainActivity.this);
                newButton.setText(data.getDeviceName());
                newButton.setOnClickListener(deviceButtonListener);
                linearLayoutLock.addView(newButton);
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

    private void addNewDevice() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        final View alertDialogView = layoutInflater.inflate(R.layout.alertdialog_input_product, null);
        final EditText newDeviceKey = alertDialogView.findViewById(R.id.addDeviceView_ed_deviceKey);
        final EditText newDeviceName = alertDialogView.findViewById(R.id.addDeviceView_ed_deviceName);
        final EditText newOwner = alertDialogView.findViewById(R.id.addDeviceView_ed_owner);

        checkBox_Lock = alertDialogView.findViewById(R.id.addDeviceView_checkBox_Lock);
        checkBox_check = alertDialogView.findViewById(R.id.addDeviceView_checkBox_check);
        checkBox_camera = alertDialogView.findViewById(R.id.addDeviceView_checkBox_camera);
        checkBox_Lock.setOnCheckedChangeListener(checkBoxListener);
        checkBox_check.setOnCheckedChangeListener(checkBoxListener);
        checkBox_camera.setOnCheckedChangeListener(checkBoxListener);
        alertDialog.setView(alertDialogView)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String deviceKey = newDeviceKey.getText().toString();
                final String deviceName = newDeviceName.getText().toString();
                String ownerName = newOwner.getText().toString();
                if(TextUtils.isEmpty(deviceKey)){
                    Toast.makeText(MainActivity.this, "要記得輸入\"Device key\"呦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ownerName)){
                    Toast.makeText(MainActivity.this, "要記得輸入\"Owner\"是誰呦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(deviceName)){
                    Toast.makeText(MainActivity.this, "幫您的設備取個名子吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(checkBoxString)){
                    Toast.makeText(MainActivity.this, "要記得選擇產品呦", Toast.LENGTH_SHORT).show();
                    return;
                }
                jBeanSetDevice data = new jBeanSetDevice(deviceName,deviceKey,checkBoxString,ownerName);
                databaseReference = firebaseDatabase.getReference("lockData");
                databaseReference.push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "成功新增 "+deviceName+" 裝置", Toast.LENGTH_SHORT).show();
                        linearLayoutLock.setVisibility(View.VISIBLE);
                        linearLayoutCamera.setVisibility(View.GONE);
                        linearLayoutHistory.setVisibility(View.GONE);
                        linearLayoutShare.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "很遺憾，上傳失敗!!", Toast.LENGTH_SHORT).show();
                        Log.e("addNewDevice","Up date fail");
                    }
                });
            }
        }).setNegativeButton("cancel",null).show();
    }

    private void findView() {
        ImageButton imageButton_Bluetooth = findViewById(R.id.imageButton_bluetooth);
        ImageButton imageButton_NFC = findViewById(R.id.imageButton_nfc);
        ImageButton imageButton_lock = findViewById(R.id.imageButton_lock);
        ImageButton imageButton_Camera = findViewById(R.id.imageButton_camera);
        ImageButton imageButton_History = findViewById(R.id.imageButton_history);
        ImageButton imageButton_Share = findViewById(R.id.imageButton_share);
        imageButton_Bluetooth.setOnClickListener(onImageButtonClickListener);
        imageButton_NFC.setOnClickListener(onImageButtonClickListener);
        imageButton_lock.setOnClickListener(onImageButtonClickListener);
        imageButton_Camera.setOnClickListener(onImageButtonClickListener);
        imageButton_History.setOnClickListener(onImageButtonClickListener);
        imageButton_Share.setOnClickListener(onImageButtonClickListener);
        linearLayoutLock = findViewById(R.id.lockButtonLayout);
        linearLayoutCamera = findViewById(R.id.cameraButtonLayout);
        linearLayoutHistory = findViewById(R.id.historyButtonLayout);
        linearLayoutShare = findViewById(R.id.shareButtonLayout);
        ImageView controlLock = findViewById(R.id.controlLock);
        controlLock.setOnClickListener(onControlFunctionClickListener);
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
//
//    }

    private void activityOriginalSetting() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("ImageFile", MODE_PRIVATE);
        final String userImageString = sharedPreferences.getString("UserImage", "noFile");
        if (!userImageString.equals("noFile")) {
            Log.d("Image", "userImage is change");
            Log.d("Image", "Data：" + userImageString);
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            final View view = layoutInflater.inflate(R.layout.nav_header_main, null);
            final ImageView userImageView = view.findViewById(R.id.headerUserImage);
            final byte[] decodeByte = Base64.decode(userImageString, 0);
            userImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("touch", "touch dnow");
                    userImageView.setImageBitmap(BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length));
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_account) {
            Intent page = new Intent(MainActivity.this, UserInformationActivity.class);
            startActivity(page);
            return true;
        }
        if (id == R.id.action_logout) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            Intent page = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(page);
            MainActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
