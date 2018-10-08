package com.example.user1801.flowerguard;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class UserInformationActivity extends Activity {
    TextView show_Name,show_Phone,show_Address,show_Email;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        show_Name = findViewById(R.id.ed_Name);
        show_Phone = findViewById(R.id.ed_Phone);
        show_Address = findViewById(R.id.ed_Address);
        show_Email = findViewById(R.id.ed_Email);
        imageView = findViewById(R.id.imageView);
        ImageButton ed_Name = findViewById(R.id.change_Name);
        ImageButton ed_Phone = findViewById(R.id.change_Phone);
        ImageButton ed_Address = findViewById(R.id.change_Address);
        ImageButton ed_Email = findViewById(R.id.change_Email);
        ed_Name.setOnClickListener(clickListener_changeButton);
        ed_Phone.setOnClickListener(clickListener_changeButton);
        ed_Address.setOnClickListener(clickListener_changeButton);
        ed_Email.setOnClickListener(clickListener_changeButton);
        imageView.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageView:
                    Toast.makeText(UserInformationActivity.this, "Touch here", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,1);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                switch (resultCode) {
                    case RESULT_OK:
                        if(data != null) {
                            Uri uri = data.getData();
                            ContentResolver cr = this.getContentResolver();
                            try{
                                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                                imageView.setImageBitmap(bitmap);
                                SharedPreferences sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);

                                //得到SharedPreferences.Editor对象，并保存数据到该对象中
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", et_name.getText().toString().trim());
                                editor.commit();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

    View.OnClickListener clickListener_changeButton = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            LayoutInflater layoutInflater = LayoutInflater.from(UserInformationActivity.this);
            final View addNewView = layoutInflater.inflate(R.layout.alertdialog_input_text,null);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserInformationActivity.this);
            TextView titleText = addNewView.findViewById(R.id.changeDataView_Title);
            final EditText editText = addNewView.findViewById(R.id.changeDataView_newText);
            switch (view.getId()){
                case R.id.change_Name:
                    titleText.setText("名稱");
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    editText.setText(show_Name.getText());
                    alertDialog.setView(addNewView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText newText = (EditText) addNewView.findViewById(R.id.changeDataView_newText);
                                String str = newText.getText().toString();
                                if(!TextUtils.isEmpty(str)){
                                    if(!str.contains("@") & !str.contains(".")){
                                        show_Name.setText(str);
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                editText.setEnabled(false);
                                dialogInterface.cancel();
                            }
                        }).show();
                    break;
                case R.id.change_Email:
                    titleText.setText("Email");
                    editText.setText(show_Email.getText());
                    alertDialog.setView(addNewView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText newText = (EditText) addNewView.findViewById(R.id.changeDataView_newText);
                                String str = newText.getText().toString();
                                if(!TextUtils.isEmpty(str)) {
                                    if (Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(str).matches()){
                                        show_Email.setText(newText.getText().toString().trim());
                                    }else{
                                        Toast.makeText(UserInformationActivity.this, "Email格式錯誤呦!!", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(UserInformationActivity.this, "Email不可為空哦", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                editText.setEnabled(false);
                                dialogInterface.cancel();
                            }
                        }).show();
                    break;
                case R.id.change_Phone:
                    titleText.setText("電話");
                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    editText.setText(show_Phone.getText());
                    alertDialog.setView(addNewView)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EditText newText = (EditText) addNewView.findViewById(R.id.changeDataView_newText);
                                    String str = newText.getText().toString();
                                    if(Pattern.compile("/^09\\d{2}-?\\d{3}-?\\d{3}$/").matcher(str).matches()){
                                        show_Phone.setText(str.trim());
                                    }else{
                                        Toast.makeText(UserInformationActivity.this, "請輸入正確號碼哦", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editText.setEnabled(false);
                            dialog.cancel();
                        }
                    }).show();
                    break;
                case R.id.change_Address:
                    titleText.setText("地址");
                    editText.setText(show_Address.getText());
                    alertDialog.setView(addNewView)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EditText newText = (EditText) addNewView.findViewById(R.id.changeDataView_newText);
                                    String str = newText.getText().toString();
                                    if(TextUtils.isEmpty(str)) {
                                        if (str.trim().length() > 0) {
                                            show_Address.setText(str);
                                        }else{
                                            Toast.makeText(UserInformationActivity.this, "地址不能空著哦", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(UserInformationActivity.this, "地址不能空著哦", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editText.setEnabled(false);
                            dialog.cancel();
                        }
                    }).show();
                    break;
            }
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 什麼都不用寫
        }
        else {
            // 什麼都不用寫
        }
    }
}
