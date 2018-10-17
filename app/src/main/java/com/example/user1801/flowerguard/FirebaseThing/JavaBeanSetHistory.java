package com.example.user1801.flowerguard.FirebaseThing;

public class JavaBeanSetHistory {
    String who;
    String uid;
    String email;
    String state;
    String deviceName;
    String deviceKey;
    String openTime;
    String closeTime;

    public JavaBeanSetHistory() {
        super();
    }

    public JavaBeanSetHistory(String who, String uid, String email, String state, String deviceName, String deviceKey, String openTime, String closeTime) {
        this.who = who;
        this.uid = uid;
        this.email = email;
        this.state = state;
        this.deviceName = deviceName;
        this.deviceKey = deviceKey;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
}
