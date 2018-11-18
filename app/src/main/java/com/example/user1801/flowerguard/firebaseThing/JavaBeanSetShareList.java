package com.example.user1801.flowerguard.firebaseThing;

public class JavaBeanSetShareList {
    private String key;
    private String mac;
    private String owner;
    private String deviceName;
    private String deviceID;

    public JavaBeanSetShareList() {
        super();
    }

    public JavaBeanSetShareList(String key, String mac, String owner, String deviceName, String deviceID) {
        this.key = key;
        this.mac = mac;
        this.owner = owner;
        this.deviceName = deviceName;
        this.deviceID = deviceID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
}
