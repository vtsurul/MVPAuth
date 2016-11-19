package com.softdesign.mvpauth.di.components;

import com.softdesign.mvpauth.data.managers.DataManager;
import com.softdesign.mvpauth.di.modules.LocalModule;
import com.softdesign.mvpauth.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {NetworkModule.class, LocalModule.class})
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
