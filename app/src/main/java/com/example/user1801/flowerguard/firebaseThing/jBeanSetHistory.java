package com.example.user1801.flowerguard.firebaseThing;

public class jBeanSetHistory {
    String who;
    String email;
    String state;

    public jBeanSetHistory(String who, String email, String state) {
        this.who = who;
        this.email = email;
        this.state = state;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
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
}
