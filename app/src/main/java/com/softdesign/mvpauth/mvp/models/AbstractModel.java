package com.softdesign.mvpauth.mvp.models;

import com.softdesign.mvpauth.data.managers.DataManager;
import com.softdesign.mvpauth.di.DaggerService;
import com.softdesign.mvpauth.di.components.DaggerModelComponent;
import com.softdesign.mvpauth.di.components.DataManagerComponent;
import com.softdesign.mvpauth.di.components.ModelComponent;
import com.softdesign.mvpauth.di.modules.ModelModule;

import javax.inject.Inject;

public abstract class AbstractModel {

    @Inject
    DataManager mDataManager;

    public AbstractModel() {
        ModelComponent component = DaggerService.getComponent(ModelComponent.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(DataManagerComponent.class, component);
        }
        component.inject(this);
    }

    private ModelComponent createDaggerComponent() {
        return DaggerModelComponent.builder()
                .modelModule(new ModelModule())
                .build();
    }
}
