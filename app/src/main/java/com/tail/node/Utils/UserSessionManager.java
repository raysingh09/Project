package com.tail.node.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.tail.node.Welcome;

import java.util.HashMap;

public class UserSessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREFER_NAME = "TailNode";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_NAME = "name";
    public static final String KEY_NUMBER = "number";


    @SuppressLint("CommitPrefEdits")
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String userName, String phoneNumber){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_NAME, userName);
        editor.putString(KEY_NUMBER, phoneNumber);
        editor.commit();
    }

    public boolean checkLogin(){
        if(!this.isUserLoggedIn()){
            Intent i = new Intent(_context, Welcome.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
            return true;
        }
        return false;
    }

    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}
