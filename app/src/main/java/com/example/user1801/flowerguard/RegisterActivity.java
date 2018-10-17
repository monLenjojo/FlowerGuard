package com.example.user1801.flowerguard;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.user1801.flowerguard.FirebaseThing.JavaBeanSetPerson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText ed_mEmail, ed_mPassword, ed_ckPassword, ed_name, ed_address, ed_phone;
    CheckBox ck_agree;
    Button buttonDone;
    JavaBeanSetPerson JavaBeanSetPerson;
    FirebaseAuth auth;
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ed_mEmail = findViewById(R.id.registerView_ed_email);
        ed_mPassword = findViewById(R.id.registerView_ed_password);
        ed_ckPassword = findViewById(R.id.registerView_ed_check_password);
        ed_name = findViewById(R.id.registerView_ed_name);
        ed_address = findViewById(R.id.registerView_ed_address);
        ed_phone = findViewById(R.id.registerView_ed_phone);
        ck_agree = findViewById(R.id.registerView_check_agree);
        buttonDone = findViewById(R.id.registerView_bt_done);
        ck_agree.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        buttonDone.setEnabled(true);
                    } else {
                        buttonDone.setEnabled(false);
                    }
                }
            }
        );
        auth = FirebaseAuth.getInstance();
    }

    public void attemptLogin(View view) {
        View focusView = null;
        boolean checkData = true;
        ed_mEmail.setError(null);
        ed_mPassword.setError(null);
        ed_ckPassword.setError(null);
        ed_name.setError(null);
        ed_address.setError(null);
        ed_phone.setError(null);

        String mEmail = ed_mEmail.getText().toString();
        String mPassword = ed_mPassword.getText().toString();
        String ckPassword = ed_ckPassword.getText().toString();
        String name = ed_name.getText().toString();
        String address = ed_address.getText().toString();
        String phone = ed_phone.getText().toString();

        if (!TextUtils.isEmpty(phone)) {
            if (Pattern.compile("[0]{1}[9]{1}[0-9]{2}[0-9]{6}").matcher(phone).matches()) {
                //正確
            } else {
                ed_phone.setError("請輸入正確的號碼");
                focusView = ed_phone;
                checkData = false;
            }
        } else {
            ed_phone.setError("請輸入電話");
            focusView = ed_phone;
            checkData = false;
        }

        if (TextUtils.isEmpty(address.trim())) {
            ed_address.setError("請輸入你的住址");
            focusView = ed_address;
            checkData = false;
        }

        if (!TextUtils.isEmpty(name)) {
            //不為空
        } else {
            ed_name.setError("請輸入你的名子");
            focusView = ed_name;
            checkData = false;
        }

        if (!TextUtils.isEmpty(ckPassword)) {
            if (!ckPassword.contains(mPassword)) {
                ed_ckPassword.setError("確認密碼錯誤");
                focusView = ed_ckPassword;
                checkData = false;
            }
        } else {
            ed_ckPassword.setError("請再次輸入你的密碼");
            focusView = ed_ckPassword;
            checkData = false;
        }

        if (TextUtils.isEmpty(mPassword) && mPassword.length() <= 6) {
            ed_mPassword.setError("密碼過短，請輸入6位以上");
            focusView = ed_mPassword;
            checkData = false;
        }

        if (!TextUtils.isEmpty(mEmail)) {
            if (!Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(mEmail).matches()) {
                ed_mEmail.setError("請輸入正確資料");
                focusView = ed_mEmail;
                checkData = false;
            }
        } else {
            ed_mEmail.setError("不能為空");
            focusView = ed_mEmail;
            checkData = false;
        }

        if (checkData) {
            //firebase
            JavaBeanSetPerson = new JavaBeanSetPerson(name, phone, address, mEmail);
            registerWithEmail(mEmail, mPassword);
        } else {
            focusView.requestFocus();
        }
    }

    public void registerWithEmail(String email, String password) {
        Log.d("CreateUser", email + " ," + password);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    if (task.isSuccessful()) {
                        FirebaseDatabase.getInstance().getReference("userData").child(Uid).setValue(JavaBeanSetPerson);
                        new AlertDialog.Builder(RegisterActivity.this).setMessage(JavaBeanSetPerson.getName()+" ,新帳號註冊成功囉\n自動轉換頁面中..").setPositiveButton("OK", null).show();
                        Uid = auth.getCurrentUser().getUid();
                        Log.d("CreateUser", "Uid：" + Uid);

                        RegisterActivity.this.finish();
                    } else {
                        new AlertDialog.Builder(RegisterActivity.this).setMessage("註冊失敗").setPositiveButton("OK", null).show();
                    }
                }
            }
        });
    }
}
