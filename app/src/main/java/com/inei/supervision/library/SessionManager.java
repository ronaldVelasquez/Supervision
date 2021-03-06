package com.inei.supervision.library;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.inei.supervision.activity.LoginActivity;
import com.inei.supervision.activity.MainActivity;

import java.util.HashMap;
import java.util.Objects;

public class SessionManager {
    public SharedPreferences sharedPreferences;
    public Editor editor;
    public Context context;

    public static final String PREF_NAME = "SupervisionInei";
    public static final String IS_LOGIN = "isLogin";
    public static final String KEY_DNI = "dni";
    public static final String KEY_NRO_DEVICE = "nroDevice";
    public static final String KEY_ID = "id";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String dni, String nroDevice, int id){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_DNI, dni);
        editor.putString(KEY_NRO_DEVICE, nroDevice);
        editor.putInt(KEY_ID, id);
        editor.commit();
    }

    public boolean checkLogin(){
        if(!this.isLoggedIn()){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    public HashMap<String, Object> getUserDetails(){
        HashMap<String, Object> user = new HashMap<String, Object>();
        user.put(KEY_DNI, sharedPreferences.getString(KEY_DNI, null));
        user.put(KEY_NRO_DEVICE, sharedPreferences.getString(KEY_NRO_DEVICE, null));
        user.put(KEY_ID, sharedPreferences.getInt(KEY_ID, 0));
        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
