package com.example.user1801.flowerguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.user1801.flowerguard.firebaseThing.AddFirebaseButton;
import com.example.user1801.flowerguard.firebaseThing.ShareDevicePermission;

public class shareToActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to);
        LinearLayout linearLayout = findViewById(R.id.layout_ShareTo);
        Intent intent = getIntent();
        String result = intent.getStringExtra("firebaseUid");
        String myFirebaseUid  = intent.getStringExtra("myFirebaseUid");

        AddFirebaseButton addFirebaseButton = new AddFirebaseButton(this,myFirebaseUid);
        addFirebaseButton.setShareDataReference(linearLayout,result);
    }
}
