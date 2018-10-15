package com.example.user1801.flowerguard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText ed_mEmail = findViewById(R.id.registerView_ed_email);
        EditText ed_mPassword = findViewById(R.id.registerView_ed_password);
        EditText ed_ckPassword = findViewById(R.id.registerView_ed_check_password);
        EditText ed_name = findViewById(R.id.registerView_ed_name);
        EditText ed_address = findViewById(R.id.registerView_ed_address);
        EditText ed_phone = findViewById(R.id.registerView_ed_phone);
        CheckBox ck_agree = findViewById(R.id.registerView_check_agree);
        Button buttonDone = findViewById(R.id.registerView_bt_done);
//        ed_mEmail.a
    }
}
