package com.mabnets.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


/**
 * Created by Maxwell on 12/8/2018.
 */

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME="com.maxwell.app";
    private static final String KEY_USERNAME="keyusername";
    private static final String KEY_EMAIL="keyemail";
    private static final String KEY_PHONE="keyphone";
    private static final String KEY_ID="keyid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context){
        mCtx=context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance==null){
            mInstance=new SharedPrefManager(context);
        }
        return mInstance;
    }
    //method to let user login and store data

    public void userLogin(User user){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putInt(KEY_ID,user.getId());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.apply();
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHONE,null)!=null;
    }
    public User getUser(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(

                sharedPreferences.getInt(KEY_ID,-1),
                sharedPreferences.getString(KEY_PHONE,null)


        );
    }
    public void logout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));

    }


}
