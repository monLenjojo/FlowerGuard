package com.example.user1801.flowerguard.localDatabase;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.user1801.flowerguard.firebaseThing.JavaBeanSetPerson;

public class UserInformationSharedPreferences {
    SharedPreferences sharedPreferences;

    public UserInformationSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setInformation(JavaBeanSetPerson data) {
        if (!TextUtils.isEmpty((CharSequence) data)) {
            sharedPreferences.edit().putString("userName", data.getName());
            sharedPreferences.edit().putString("userPhone", data.getPhone());
            sharedPreferences.edit().putString("userAddress", data.getAddress());
            sharedPreferences.edit().putString("userEmail", data.getEmail());
            sharedPreferences.edit().commit();
        }
    }

    public void setName(String name){
        sharedPreferences.edit().putString("userName",name);
        sharedPreferences.edit().commit();
    }

    public void setPhone(String phone){
        sharedPreferences.edit().putString("userPhone",phone);
        sharedPreferences.edit().commit();
    }

    public void setAddress(String address){
        sharedPreferences.edit().putString("userAddress",address);
        sharedPreferences.edit().commit();
    }

    public void setEmail(String email){
        sharedPreferences.edit().putString("userEmail",email);
        sharedPreferences.edit().commit();
    }
}
