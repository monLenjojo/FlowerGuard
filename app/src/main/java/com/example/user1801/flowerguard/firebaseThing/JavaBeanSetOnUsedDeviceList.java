package com.example.user1801.flowerguard.firebaseThing;

public class JavaBeanSetOnUsedDeviceList {
    private String key;
    private String mac;

    public JavaBeanSetOnUsedDeviceList() {
        super();
    }

    public JavaBeanSetOnUsedDeviceList(String key, String mac) {
        this.key = key;
        this.mac = mac;
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
}
