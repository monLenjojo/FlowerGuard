package com.example.user1801.flowerguard.firebaseThing;

public class jBeanSetDevice {
    String deviceName;
    String deviceKey;
    String module;
    String owner;

    public jBeanSetDevice() {
        super();
    }

    public jBeanSetDevice(String deviceName, String deviceKey, String module, String owner) {
        this.deviceName = deviceName;
        this.deviceKey = deviceKey;
        this.module = module;
        this.owner = owner;
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
