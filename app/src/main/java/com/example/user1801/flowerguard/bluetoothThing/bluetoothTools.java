package com.example.user1801.flowerguard.bluetoothThing;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class bluetoothTools {
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter adapter;
    BluetoothDevice device;
    BluetoothSocket socket;
    OutputStream write;
    InputStream read;
    String log = "test",strSet="0";
    public bluetoothTools() {
        adapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void initializeBluetooth() {
        boolean isEnable = adapter.isEnabled();
        if (!isEnable) {
            openBluetooth();
        }
        adapter.startDiscovery();
    }

    public boolean openBluetooth() {
        return adapter.enable();
    }

    public boolean closeBluetooth() {
        return adapter.disable();
    }

    public void reverseBluetooth() {
        if (adapter.isEnabled()) {
            adapter.disable();
        } else {
            adapter.enable();
        }
    }

    public void connect(String address) {
        device = adapter.getRemoteDevice(address);
        try {
            socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("BluetoothSocket", String.valueOf(e));
        }
    }

    public void inputPort() {
        try {
            read = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outputPort() {
        try {
            write = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ieee754Write(float val) {
        int sendVal = Float.floatToIntBits(val);
        byte valArray[] = new byte[11];
        int floatSet = Integer.parseInt("0");
        int[] adapterVal = {floatSet, floatSet, floatSet, floatSet, floatSet, floatSet, floatSet, floatSet, floatSet, floatSet, floatSet};
        for (int i = 0; i < 11; i++) {
            valArray[i] = (byte) ((sendVal & 0xe0000000) >>> (29-(i*3)));
            adapterVal[i] = valArray[i];
            Log.d("test", "val："+String.valueOf(adapterVal[i]));
        }
        try {
            write = socket.getOutputStream();
            for (int i = 0; i <11 ; i++) {
                write.write(adapterVal[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendChaosUs(float u1) {
        int sendUm = Float.floatToIntBits(u1);
        Log.i(log, "sendUm = " + sendUm);
        int f1 = Integer.parseInt(strSet), f2 = Integer.parseInt(strSet), f3 = Integer.parseInt(strSet), f4 = Integer.parseInt(strSet), f5 = Integer.parseInt(strSet), f6 = Integer.parseInt(strSet), f7 = Integer.parseInt(strSet), f8 = Integer.parseInt(strSet), f9 = Integer.parseInt(strSet), f10 = Integer.parseInt(strSet), f11 = Integer.parseInt(strSet);
        byte us[] = new byte[11];
        us[0] = (byte) ((sendUm & 0xe0000000) >>> 29);
        f1 = us[0];
        us[1] = (byte) ((sendUm & 0x1c000000) >>> 26);
        f2 = us[1];
        us[2] = (byte) ((sendUm & 0x03800000) >>> 23);
        f3 = us[2];
        us[3] = (byte) ((sendUm & 0x00700000) >>> 20);
        f4 = us[3];
        us[4] = (byte) ((sendUm & 0x000e0000) >>> 17);
        f5 = us[4];
        us[5] = (byte) ((sendUm & 0x0001c000) >>> 14);
        f6 = us[5];
        us[6] = (byte) ((sendUm & 0x00003800) >>> 11);
        f7 = us[6];
        us[7] = (byte) ((sendUm & 0x00000700) >>> 8);
        f8 = us[7];
        us[8] = (byte) ((sendUm & 0x000000e0) >>> 5);
        f9 = us[8];
        us[9] = (byte) ((sendUm & 0x0000001c) >>> 2);
        f10 = us[9];
        us[10] = (byte) ((sendUm & 0x00000003));
        f11 = us[10];
        try {
            OutputStream os = socket.getOutputStream();
            os.write(f1);
            Log.i(log, "f1 = " + f1);
            os.write(f2);
            Log.i(log, "f2 = " + f2);
            os.write(f3);
            Log.i(log, "f3 = " + f3);
            os.write(f4);
            Log.i(log, "f4 = " + f4);
            os.write(f5);
            Log.i(log, "f5 = " + f5);
            os.write(f6);
            Log.i(log, "f6 = " + f6);
            os.write(f7);
            Log.i(log, "f7 = " + f7);
            os.write(f8);
            Log.i(log, "f8 = " + f8);
            os.write(f9);
            Log.i(log, "f9 = " + f9);
            os.write(f10);
            Log.i(log, "f10 = " + f10);
            os.write(f11);
            Log.i(log, "f11 = " + f11);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
