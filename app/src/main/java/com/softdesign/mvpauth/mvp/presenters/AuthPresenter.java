package com.softdesign.mvpauth.mvp.presenters;

import android.util.Log;

import com.softdesign.mvpauth.di.DaggerService;
import com.softdesign.mvpauth.di.scopes.AuthScope;
import com.softdesign.mvpauth.mvp.models.AuthModel;
import com.softdesign.mvpauth.mvp.views.IAuthView;
import com.softdesign.mvpauth.ui.custom_views.AuthPanel;

import javax.inject.Inject;

import dagger.Provides;

public class AuthPresenter extends AbstractPresenter<IAuthView> implements IAuthPresenter {

    private static final String TAG = "AuthPresenter";

    @Inject
    AuthModel mAuthModel;


    public AuthPresenter() {
        mAuthModel = new AuthModel();

        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);

        Log.e(TAG, "AuthPresenter: inject complete");
    }


    @Override
    public void initView() {
        if(getView() != null) {
            if (checkUserAuth()) {
                getView().hideLoginBtn();
            } else {
                getView().showLoginBtn();
            }
        }
    }


    @Override
    public void clickOnLogin() {
        if (getView() != null && getView().getAuthPanel() != null) {
            if (getView().getAuthPanel().isIdle()) {
                getView().getAuthPanel().setCustomState(AuthPanel.LOGIN_STATE);
            } else {
                // TODO: 02.11.2016 auth user
                mAuthModel.loginUser(getView().getAuthPanel().getUserEmail(), getView().getAuthPanel().getUserPassword());
                getView().showMessage("request for user auth");
            }
        }
    }


    @Override
    public void clickOnFb() {
        if (getView() != null) {
            getView().showMessage("clickOnFb");
        }
    }


    @Override
    public void clickOnVk() {
        if (getView() != null) {
            getView().showMessage("clickOnVk");
        }
    }


    @Override
    public void clickOnTwitter() {
        if (getView() != null) {
            getView().showMessage("clickOnTwitter");
        }
    }


    @Override
    public void clickOnShowCatalog() {
        if (getView() != null) {
            // TODO: 16.11.2016 if update data complete start catalog Screen
            getView().showCatalogScreen();
        }

    }


    @Override
    public boolean checkUserAuth() {
        return mAuthModel.isAuthUser();
    }


    //region ============================== DI ==============================


    @dagger.Module
    public class Module {
        @Provides
        @AuthScope
        AuthModel provideAuthModel() {
            return new AuthModel();
        }
    }


    @dagger.Component(modules = Module.class)
    @AuthScope
    interface Component {
        void inject(AuthPresenter presenter);
    }


    private Component createDaggerComponent() {
        return DaggerAuthPresenter_Component.builder()
                .module(new Module())
                .build();
    }

    //endregion
}
