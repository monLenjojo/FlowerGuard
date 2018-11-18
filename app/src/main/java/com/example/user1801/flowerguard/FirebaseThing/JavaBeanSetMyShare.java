package com.example.user1801.flowerguard.firebaseThing;

public class JavaBeanSetMyShare {
    private String shareTo;
    private String deviceID;
    public JavaBeanSetMyShare() {
        super();
    }

    public JavaBeanSetMyShare(String shareTo, String deviceID) {
        this.shareTo = shareTo;
        this.deviceID = deviceID;
    }

    public String getShareTo() {
        return shareTo;
    }

    public void setShareTo(String shareTo) {
        this.shareTo = shareTo;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
