package com.spe.bebalanced.bebalanced;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;



/**
 * Created by Mykola Matsiakh on 06.09.17.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.background);
        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            //  Create a new boolean and preference and set it to true

            @Override
            public void run() {
//                checkFirstRun();
                finish();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, SPLASH_TIME_OUT);

    }

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        int currentVersionCode = BuildConfig.VERSION_CODE;
//
//        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);
//        if (currentVersionCode == savedVersionCode) {
//
//            ((CleanCityApplication) this.getApplication()).setFirstStart(0);
//            Intent i = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(i);
//            return;
//
//        } else if (savedVersionCode == DOESNT_EXIST) {
//
//            ((CleanCityApplication) this.getApplication()).setFirstStart(1);
//            Intent i = new Intent(SplashActivity.this, IntroActivity.class);
//            startActivity(i);
//
//        } else if (currentVersionCode > savedVersionCode) {
//            ((CleanCityApplication) this.getApplication()).setFirstStart(1);
//            Intent i = new Intent(SplashActivity.this, IntroActivity.class);
//            startActivity(i);
//        }
//        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
//    }
    }
}

