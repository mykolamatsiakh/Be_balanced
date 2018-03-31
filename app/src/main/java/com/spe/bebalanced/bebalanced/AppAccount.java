package com.spe.bebalanced.bebalanced;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mykola on 02.09.17.
 */

public class AppAccount {

    private static AppAccount sInstance;

    public static AppAccount getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AppAccount(context);
        }
        return sInstance;
    }

    private static final String ACCOUNT_PREF = "ACCOUNT_PREF";
    private static final String KEY_NAME = "NAME";

    private final SharedPreferences mSharedPreferences;


    AppAccount(Context context) {
        mSharedPreferences = context
                .getApplicationContext()
                .getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
    }


    public void setName(String name) {
        mSharedPreferences.edit()
                .putString(KEY_NAME, name)
                .apply();
    }

    public String getName() {
        return mSharedPreferences.getString(KEY_NAME, "");
    }

}
