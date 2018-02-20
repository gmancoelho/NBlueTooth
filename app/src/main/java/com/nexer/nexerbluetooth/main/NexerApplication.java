package com.nexer.nexerbluetooth.main;

/**
 * Created by guilhermecoelho on 1/30/18.
 */

import android.app.Application;
import com.facebook.stetho.Stetho;
import timber.log.BuildConfig;
import timber.log.Timber;


public class NexerApplication extends Application {

    private static NexerApplication mInstance;

    @Override
    public void onCreate() {

        super.onCreate();

        mInstance = (NexerApplication) getApplicationContext();

        setupTimber();
        setupSteho();
    }

    public static NexerApplication getInstance() {
        return mInstance;
    }


    private void setupSteho() {

        if (BuildConfig.DEBUG) {

            Stetho.initializeWithDefaults(this);

        }
    }

    private void setupTimber() {

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
