package com.example.user1801.flowerguard.firebaseThing;

public class JavaBeanSetDevice {
    private String deviceID;
    private String deviceName;
    private String deviceModule;

    public JavaBeanSetDevice() {
        super();
    }

    public JavaBeanSetDevice(String deviceName, String deviceID, String deviceModule) {
        this.deviceName = deviceName;
        this.deviceID = deviceID;
        this.deviceModule = deviceModule;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceModule() {
        return deviceModule;
    }

    public void setDeviceModule(String deviceModule) {
        this.deviceModule = deviceModule;
    }
}
