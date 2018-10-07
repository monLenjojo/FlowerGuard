package com.example.user1801.flowerguard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        show_Name = findViewById(R.id.ed_Name);
        show_Phone = findViewById(R.id.ed_Phone);
        show_Address = findViewById(R.id.ed_Address);
        show_Email = findViewById(R.id.ed_Email);
        ImageButton ed_Name = findViewById(R.id.change_Name);
        ImageButton ed_Phone = findViewById(R.id.change_Phone);
        ImageButton ed_Address = findViewById(R.id.change_Address);
        ImageButton ed_Email = findViewById(R.id.change_Email);
        ed_Name.setOnClickListener(clickListener);
        ed_Phone.setOnClickListener(clickListener);
        ed_Address.setOnClickListener(clickListener);
        ed_Email.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.change_Name:
                    LayoutInflater layoutInflater = LayoutInflater.from(UserInformationActivity.this);
                    final View addNewView = layoutInflater.inflate(R.layout.alertdialog_input_text,null);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserInformationActivity.this);
                    alertDialog.setView(addNewView)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    EditText newText = (EditText) addNewView.findViewById(R.id.changeDataView_newText);
                                    show_Name.setText(newText.getText().toString().trim());
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    alertDialog.create().show();
                    break;
                case R.id.change_Email:
                    break;
                case R.id.change_Phone:
                    break;
                case R.id.change_Address:
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
