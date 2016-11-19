package com.softdesign.mvpauth.di.modules;

import com.softdesign.mvpauth.data.managers.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return new DataManager();
    }
}
