package com.example.user1801.flowerguard.localDatabase;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.user1801.flowerguard.firebaseThing.JavaBeanSetPerson;

public class UserInformationSharedPreferences {
    SharedPreferences sharedPreferences;
    JavaBeanSetPerson data;
    public void setSharedPreferences(SharedPreferences sharedPreferences, @NonNull JavaBeanSetPerson data) {
        this.sharedPreferences = sharedPreferences;
        if (!TextUtils.isEmpty((CharSequence) data)) {
            this.data = data;
            sharedPreferences.edit().putString("userName", data.getName());
            sharedPreferences.edit().putString("userPhone", data.getPhone());
            sharedPreferences.edit().putString("userAddress", data.getAddress());
            sharedPreferences.edit().putString("userEmail", data.getEmail());
            sharedPreferences.edit().commit();
        }
    }


}
