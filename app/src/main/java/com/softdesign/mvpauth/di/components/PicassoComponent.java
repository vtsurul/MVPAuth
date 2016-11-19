package com.softdesign.mvpauth.di.components;

import com.softdesign.mvpauth.di.modules.PicassoCacheModule;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = PicassoCacheModule.class)
@Singleton
public interface PicassoComponent {
    Picasso getPicasso();
}
