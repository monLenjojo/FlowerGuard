package com.example.user1801.flowerguard;

import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Layout;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.user1801.flowerguard.bluetoothChaos.chaosWithBluetooth;
import com.example.user1801.flowerguard.bluetoothThing.bluetoothTools;
import com.example.user1801.flowerguard.chaosThing.ChaosMath;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.util.Base64;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String log = "ImageButtonListener";
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseAuth;
    DatabaseReference databaseReference;
    ChaosMath chaosMath;
    bluetoothTools a;
    LinearLayout linearLayoutLock,linearLayoutCamera,linearLayoutHistory,linearLayoutShare;
    chaosWithBluetooth chaosWithBluetooth;
    View.OnClickListener onImageButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton_bluetooth:
                    Log.d(log, "click bluetooth button");
//                    Toast.makeText(MainActivity.this, "bluetooth button", Toast.LENGTH_SHORT).show();
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

                    if(chaosWithBluetooth.connect("98:D3:31:FB:8A:D0")) {
                        Toast.makeText(MainActivity.this, "Connect false", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(MainActivity.this, "lock button", Toast.LENGTH_SHORT).show();
//                    a = new bluetoothTools();
//                    a.initializeBluetooth();
////                    a.connect("98:D3:31:90:32:38");
//                    Log.d("chaosTest", "Get connect");
//                    a.connect("98:D3:31:FB:8A:D0");
//                    chaosMath = new ChaosMath();
//                    chaosMath.inti();
//                    mathLoop(-12.543f);
//                    mathLoop(-378.54f);
//                    mathLoop(4.7487f);
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
                    databaseReference = firebaseDatabase.getReference("account");
                    Map<String,Object> data = new HashMap<>();
                    data.put("name","Bob");
                    data.put("phone","0987123456");
                    databaseReference.child(
                            FirebaseAuth.getInstance().getCurrentUser().getUid()).child("person").setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "work", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });

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
    View.OnClickListener onControlFunctionClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.controlLock:
                        if (chaosWithBluetooth.start(MainActivity.this)) {
                            Map<String, Object> data = new HashMap<>();
                            data.put("who", firebaseAuth.getUid());
                            data.put("theEmail", firebaseAuth.getEmail());
                            data.put("state", "true");
                            databaseReference = firebaseDatabase.getReference("doorLife");
                            databaseReference.child(firebaseAuth.getUid()).push().setValue(data);
                        } else {
                            Toast.makeText(MainActivity.this, "解鎖失敗", Toast.LENGTH_SHORT).show();
                            Map<String, Object> data = new HashMap<>();
                            data.put("who", firebaseAuth.getUid());
                            data.put("theEmail", firebaseAuth.getEmail());
                            data.put("state", "false");
                            databaseReference = firebaseDatabase.getReference("doorLife");
                            databaseReference.child(firebaseAuth.getUid()).push().setValue(data);
                        }
                    break;
            }
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
            }
        });
//        fab.setVisibility(View.GONE);
        activityOriginalSetting();
        findView();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        chaosWithBluetooth = new chaosWithBluetooth();
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
