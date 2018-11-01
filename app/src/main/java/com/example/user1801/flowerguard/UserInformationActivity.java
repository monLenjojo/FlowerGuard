package com.example.user1801.flowerguard;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1801.flowerguard.firebaseThing.UserInformationGetOnFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
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
    TextView show_Name, show_Phone, show_Address, show_Email;
    ImageView imageView, ed_Name, ed_Phone, ed_Address, ed_Email;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView:
                    Log.d("ImageFile","Touch to Change");
                    Intent intent = new Intent();
                    intent.setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
                    break;
            }
        }
    };
    View.OnClickListener clickListener_changeButton = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            LayoutInflater layoutInflater = LayoutInflater.from(UserInformationActivity.this);
            final View addNewView = layoutInflater.inflate(R.layout.alertdialog_input_text, null);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserInformationActivity.this);
            TextView titleText = addNewView.findViewById(R.id.changeDataView_Title);
            final EditText editText = addNewView.findViewById(R.id.changeDataView_newText);
            switch (view.getId()) {
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
                                    if (!TextUtils.isEmpty(str)) {
                                        if (!str.contains("@") & !str.contains(".")) {
                                            show_Name.setText(str);
                                            setData.updataNewInformation("name",str);
                                        }
                                    }
                                    editText.setEnabled(false);
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
                                    final String str = newText.getText().toString().trim();
                                    if (!TextUtils.isEmpty(str)) {
                                        if (Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(str).matches()) {
                                            FirebaseAuth.getInstance().getCurrentUser().updateEmail(str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isComplete()) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(UserInformationActivity.this, "Email change successful", Toast.LENGTH_SHORT).show();
                                                            show_Email.setText(str);
                                                            setData.updataNewInformation("email",str);
                                                        }else {
                                                            String errorMessage = "";
                                                            if (!TextUtils.isEmpty(task.getException().getMessage())) {
                                                                errorMessage = "\n"+task.getException().getMessage();
                                                                Log.e("firebaseAuthEmailChange",task.getException().getMessage());
                                                            }
                                                            new AlertDialog.Builder(UserInformationActivity.this).setTitle("Error").setMessage("Change is fail." +errorMessage).setPositiveButton("OK",null).show();
                                                        }
                                                    }
                                                }
                                            });

                                        } else {
                                            Toast.makeText(UserInformationActivity.this, "Email格式錯誤呦!!", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(UserInformationActivity.this, "Email不可為空哦", Toast.LENGTH_SHORT).show();
                                    }
                                    editText.setEnabled(false);
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
                                    String str = newText.getText().toString().trim();
                                    if (Pattern.compile("[0]{1}[9]{1}[0-9]{2}[0-9]{6}").matcher(str).matches()) {
                                        show_Phone.setText(str);
                                        setData.updataNewInformation("phone",str);
                                    } else {
                                        Toast.makeText(UserInformationActivity.this, "請輸入正確號碼哦", Toast.LENGTH_SHORT).show();
                                    }
                                    editText.setEnabled(false);
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
                                    if (TextUtils.isEmpty(str)) {
                                        if (str.trim().length() > 0) {
                                            show_Address.setText(str);
                                            setData.updataNewInformation("address",str);
                                        } else {
                                            Toast.makeText(UserInformationActivity.this, "地址不能空著哦", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(UserInformationActivity.this, "地址不能空著哦", Toast.LENGTH_SHORT).show();
                                    }
                                    editText.setEnabled(false);
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

    UserInformationGetOnFirebase setData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        findView();
        setViewListener();
        sharedPreferences = getSharedPreferences("ImageFile", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String UserImage = sharedPreferences.getString("UserImage", "noFile");
        if (!UserImage.equals("noFile")) {
            Log.d("ImageFile", "Is get old image");
            byte[] decodeByte = Base64.decode(UserImage, 0);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length));
        } else {
            Log.d("ImageFile", "Is no have old image");
        }
        Intent data = getIntent();
        if (data.getStringExtra("firebaseUid").length()>0) {
            setData = new UserInformationGetOnFirebase(data.getStringExtra("firebaseUid"), show_Name, show_Email, show_Phone, show_Address);
            setData.addListenter();
        }else{
            Log.e("UserInformationActivity","Intent input uid is null");
            String tryGetUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if (tryGetUid.isEmpty()) {
                Intent page = new Intent(this,LoginActivity.class);
                startActivity(page);
                this.finish();
            }else{
                setData = new UserInformationGetOnFirebase(tryGetUid, show_Name, show_Email, show_Phone, show_Address);
                setData.addListenter();
            }
        }
    }

    private void setViewListener() {
        ed_Name.setOnClickListener(clickListener_changeButton);
        ed_Phone.setOnClickListener(clickListener_changeButton);
        ed_Address.setOnClickListener(clickListener_changeButton);
        ed_Email.setOnClickListener(clickListener_changeButton);
        imageView.setOnClickListener(clickListener);
    }

    private void findView() {
        show_Name = findViewById(R.id.ed_Name);
        show_Phone = findViewById(R.id.ed_Phone);
        show_Address = findViewById(R.id.ed_Address);
        show_Email = findViewById(R.id.ed_Email);
        imageView = findViewById(R.id.imageView);
        ed_Name = findViewById(R.id.change_Name);
        ed_Phone = findViewById(R.id.change_Phone);
        ed_Address = findViewById(R.id.change_Address);
        ed_Email = findViewById(R.id.change_Email);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                switch (resultCode) {
                    case RESULT_OK:
                        if (data != null) {
                            final Uri uri = data.getData();
                            final ContentResolver cr = this.getContentResolver();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        final Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                        byte[] imageByte = baos.toByteArray();
                                        String imageEncoded = Base64.encodeToString(imageByte, Base64.DEFAULT);
                                        editor.putString("UserImage", imageEncoded);
                                        editor.commit();
                                        imageView.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageView.setImageBitmap(bitmap);
                                            }
                                        });
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                        break;
                    case RESULT_CANCELED:
                        //在選擇時取消
                        break;
                }
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 什麼都不用寫
        } else {
            // 什麼都不用寫
        }
    }
}
