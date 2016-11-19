package com.softdesign.mvpauth;

import android.app.Application;

import com.softdesign.mvpauth.di.components.AppComponent;
import com.softdesign.mvpauth.di.components.DaggerAppComponent;
import com.softdesign.mvpauth.di.modules.AppModule;

public class App extends Application {

    private static AppComponent sAppComponent;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createComponent();
    }

    private void createComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }
}
