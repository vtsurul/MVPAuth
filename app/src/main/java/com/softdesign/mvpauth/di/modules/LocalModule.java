package com.softdesign.mvpauth.di.modules;

import android.content.Context;

import com.softdesign.mvpauth.data.managers.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule {

    @Provides
    @Singleton
    PreferencesManager providePreferencesManager(Context context) {
        return new PreferencesManager(context);
    }
}
