package com.softdesign.mvpauth.di.components;

import com.softdesign.mvpauth.di.modules.ModelModule;
import com.softdesign.mvpauth.mvp.models.AbstractModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(AbstractModel abstractModel);
}
