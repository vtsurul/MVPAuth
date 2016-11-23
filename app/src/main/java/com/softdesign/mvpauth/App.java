package com.softdesign.mvpauth;

import android.app.Application;
import android.content.Context;

import com.softdesign.mvpauth.di.components.AppComponent;
import com.softdesign.mvpauth.di.components.DaggerAppComponent;
import com.softdesign.mvpauth.di.modules.AppModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class App extends Application {

    private static AppComponent sAppComponent;

//    public static RefWatcher getRefWatcher() {
//        App application = (App) sAppComponent.getContext();
//        return application.refWatcher;
//    }
//
//    private RefWatcher refWatcher;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        refWatcher = LeakCanary.install(this);
//        // Normal app init code...

        createComponent();

    }

    private void createComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }
}
