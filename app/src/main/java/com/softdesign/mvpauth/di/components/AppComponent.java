package com.softdesign.mvpauth.di.components;


import android.content.Context;

import com.softdesign.mvpauth.di.modules.AppModule;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
