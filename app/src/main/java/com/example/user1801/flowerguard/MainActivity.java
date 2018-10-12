package com.example.user1801.flowerguard;

import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.example.user1801.flowerguard.bluetoothThing.bluetoothTools;
import com.example.user1801.flowerguard.chaosThing.ChaosMath;

import android.util.Base64;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String log = "ImageButtonListener";
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
    }

    View.OnClickListener onImageButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageButton_bluetooth:
                    Log.d(log,"click bluetooth button");
                    Toast.makeText(MainActivity.this, "bluetooth button", Toast.LENGTH_SHORT).show();
                    bluetoothTools b = new bluetoothTools();
                    b.initializeBluetooth();
                    b.connect("98:D3:31:90:32:38");
                    break;
                case R.id.imageButton_nfc:
                    Log.d(log,"click nfc button");
                    Toast.makeText(MainActivity.this, "nfc button", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageButton_lock:
                    Log.d(log,"click lock button");
                    Toast.makeText(MainActivity.this, "lock button", Toast.LENGTH_SHORT).show();
                    bluetoothTools a = new bluetoothTools();
                    a.initializeBluetooth();
                    a.connect("98:D3:31:90:32:38");
                    a.sendChaosUs(12.5f);
//                    ChaosMath chaosMath = new ChaosMath();
//                    chaosMath.inti();
//                    for (int i = 0; i <30 ; i++) {
//                        chaosMath.chaosMath();
//                        a.ieee754Write(12.5f);
//                        float x1 = chaosMath.getX1();
//                        a.ieee754Write((float) 12.5);//(1+(x1*x1))* 123456);
//                    }
                    break;
                case R.id.imageButton_camera:
                    Log.d(log,"click camera button");
                    Toast.makeText(MainActivity.this, "camera button", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageButton_history:
                    Log.d(log,"click history button");
                    Toast.makeText(MainActivity.this, "history button", Toast.LENGTH_SHORT).show();
                    break;
                case  R.id.imageButton_share:
                    Log.d(log,"click share button");
                    Toast.makeText(MainActivity.this,"share button", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

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
        SharedPreferences sharedPreferences = getSharedPreferences("ImageFile",MODE_PRIVATE);
        final String userImageString = sharedPreferences.getString("UserImage","noFile");
        if(!userImageString.equals("noFile")){
            Log.d("Image","userImage is change");
            Log.d("Image","Data："+userImageString);
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            final View view = layoutInflater.inflate(R.layout.nav_header_main,null);
            final ImageView userImageView = view.findViewById(R.id.headerUserImage);
            final byte[] decodeByte = Base64.decode(userImageString,0);
            userImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("touch","touch dnow");
                    userImageView.setImageBitmap(BitmapFactory.decodeByteArray(decodeByte,0,decodeByte.length));
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
            Intent page = new Intent(MainActivity.this,UserInformationActivity.class);
            startActivity(page);
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
