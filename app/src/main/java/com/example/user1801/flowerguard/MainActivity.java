package com.example.user1801.flowerguard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.user1801.flowerguard.bluetoothChaos.ChaosWithBluetooth;
import com.example.user1801.flowerguard.bluetoothThing.BluetoothTools;
import com.example.user1801.flowerguard.chaosThing.ChaosMath;
import com.example.user1801.flowerguard.firebaseThing.AddFirebaseButton;
import com.example.user1801.flowerguard.firebaseThing.JavaBeanSetDevice;
import com.example.user1801.flowerguard.ListAdapter.DataGetInFirebase;
import com.example.user1801.flowerguard.firebaseThing.AddFirebaseButton;
import com.example.user1801.flowerguard.firebaseThing.JavaBeanSetPerson;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String log = "ImageButtonListener";
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseAuth;
    DatabaseReference databaseReference;
    ChaosMath chaosMath;
    BluetoothTools a;
    LinearLayout linearLayoutLock, linearLayoutCamera, linearLayoutHistory, linearLayoutShare;
    ChaosWithBluetooth ChaosWithBluetooth;
    String checkBoxString;
    private String firebaseUid, userName, userEmail;

    ListView listView_history;
    DataGetInFirebase DataGetInFirebase;

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
//        firebaseDatabase = FirebaseDatabase.getInstance();
        Intent page= getIntent();
        firebaseUid = page.getStringExtra("firebaseUid");
        userEmail = page.getStringExtra("userEmail");
//        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
//        firebaseUid = firebaseAuth.getUid();
//        userEmail = firebaseAuth.getEmail();
        activityOriginalSetting();
        findView();
        AddFirebaseButton addFirebaseButton = new AddFirebaseButton(this,firebaseUid,linearLayoutLock);
        addFirebaseButton.daraReference();
        ListView historyList = findViewById(R.id.listView_historyList);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        historyList.setDividerHeight(400);
        DataGetInFirebase = new DataGetInFirebase(this, historyList, firebaseUid);
        DataGetInFirebase.refreshData();
        ChaosWithBluetooth = new ChaosWithBluetooth();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("userData").child(firebaseUid);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JavaBeanSetPerson data = dataSnapshot.getValue(JavaBeanSetPerson.class);
                Toast.makeText(MainActivity.this, "Data Change", Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(data.getName())) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);
                    sharedPreferences.edit().putString("userName",data.getName());
                    sharedPreferences.edit().putString("userPhone",data.getPhone());
                    sharedPreferences.edit().putString("userAddress",data.getAddress());
                    sharedPreferences.edit().putString("userEmail", data.getEmail());
                    sharedPreferences.edit().commit();
                }

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
        final EditText newOwner = alertDialogView.findViewById(R.id.addDeviceView_ed_owner);

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
                String ownerName = newOwner.getText().toString();
                if (TextUtils.isEmpty(deviceKey)) {
                    Toast.makeText(MainActivity.this, "要記得輸入\"Device key\"呦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ownerName)) {
                    Toast.makeText(MainActivity.this, "要記得輸入\"Owner\"是誰呦", Toast.LENGTH_SHORT).show();
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
                final JavaBeanSetDevice data = new JavaBeanSetDevice(deviceName, deviceKey, checkBoxString, ownerName, firebaseUid);
                databaseReference = FirebaseDatabase.getInstance().getReference("lockData");
                databaseReference.child(firebaseUid).push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        databaseReference = FirebaseDatabase.getInstance().getReference("userData");
//                        if (databaseReference.child(firebaseUid).child("mydevice").push().setValue(data).isSuccessful()) {
                            Toast.makeText(MainActivity.this, "成功新增 " + deviceName + " 裝置", Toast.LENGTH_SHORT).show();
                            linearLayoutLock.setVisibility(View.VISIBLE);
                            linearLayoutCamera.setVisibility(View.GONE);
                            linearLayoutHistory.setVisibility(View.GONE);
                            linearLayoutShare.setVisibility(View.GONE);
//                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "很遺憾，上傳失敗!!", Toast.LENGTH_SHORT).show();
                        Log.e("addNewDevice", "Up date fail");
                    }
                });
            }
        }).setNegativeButton("cancel", null).show();
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

    private void activityOriginalSetting() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        SharedPreferences sharedPreferences = getSharedPreferences("ImageFile", MODE_PRIVATE);
//        final String userImageString = sharedPreferences.getString("UserImage", "noFile");
//        if (!userImageString.equals("noFile")) {
//            Log.d("Image", "userImage is change");
//            Log.d("Image", "Data：" + userImageString);
//            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
//            final View view = layoutInflater.inflate(R.layout.nav_header_main, null);
//            final ImageView userImageView = view.findViewById(R.id.headerUserImage);
//            final byte[] decodeByte = Base64.decode(userImageString, 0);
//            userImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("touch", "touch dnow");
//                    userImageView.setImageBitmap(BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length));
//                }
//            });
//        }
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
            page.putExtra("firebaseUid",firebaseUid);
            startActivity(page);
            return true;
        }
        if (id == R.id.action_logout) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            SharedPreferences sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);
            sharedPreferences.edit().clear();
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

    View.OnClickListener onImageButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton_bluetooth:
                    Log.d(log, "click bluetooth button");
                    BluetoothTools b = new BluetoothTools();
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
                    if (ChaosWithBluetooth.isConnect()) {
//                        if (ChaosWithBluetooth.start(MainActivity.this)) {
//                            JavaBeanSetHistory data = new JavaBeanSetHistory(userName,firebaseUid,userEmail,"Open",);
//                            databaseReference = firebaseDatabase.getReference("doorLife");
//                            databaseReference.child(firebaseUid).push().setValue(data);
//                        } else {
//                            Toast.makeText(MainActivity.this, "解鎖失敗", Toast.LENGTH_SHORT).show();
//                            JavaBeanSetHistory data = new JavaBeanSetHistory(firebaseUid, firebaseAuth.getEmail(), "fail");
//                            databaseReference = firebaseDatabase.getReference("doorLife");
//                            databaseReference.child(firebaseUid).push().setValue(data);
//                        }
                    } else {
                        Toast.makeText(MainActivity.this, "hava to connected first.", Toast.LENGTH_SHORT).show();
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
            Log.e("CheckBox", "choose： " + checkBoxString);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();

    }
}
