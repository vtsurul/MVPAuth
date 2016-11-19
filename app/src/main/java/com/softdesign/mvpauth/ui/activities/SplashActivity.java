package com.softdesign.mvpauth.ui.activities;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.softdesign.mvpauth.BuildConfig;
import com.softdesign.mvpauth.R;
import com.softdesign.mvpauth.di.DaggerService;
import com.softdesign.mvpauth.di.scopes.AuthScope;
import com.softdesign.mvpauth.mvp.presenters.AuthPresenter;
import com.softdesign.mvpauth.mvp.presenters.IAuthPresenter;
import com.softdesign.mvpauth.mvp.views.IAuthView;
import com.softdesign.mvpauth.ui.custom_views.AuthPanel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;

public class SplashActivity extends AppCompatActivity  implements IAuthView, View.OnClickListener{


    @Inject
    AuthPresenter mPresenter;

    @BindView(R.id.coordinator_container)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.auth_wrapper)
    AuthPanel mAuthPanel;

    @BindView(R.id.show_catalog_btn)
    Button mShowCatalogBtn;

    @BindView(R.id.login_btn)
    Button mLoginBtn;


    //region ==================== Life cycle ====================


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);

        mPresenter.takeView(this);
        mPresenter.initView();

        mLoginBtn.setOnClickListener(this);
        mShowCatalogBtn.setOnClickListener(this);

    }


    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        if (isFinishing()) {
            DaggerService.unregisterScope(AuthScope.class);
        }
        super.onDestroy();
    }


    //endregion


    //region ==================== IAuthView ====================


    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Извините что то пошло не так, попробуйте еще раз позже");
            // TODO: 02.11.2016 send error stacktrace to crachlytics
        }
    }


    @Override
    public void showLoad() {
        // TODO: 02.11.2016 show load progress
    }


    @Override
    public void hideLoad() {
        // TODO: 02.11.2016 hide load progress
    }


    @Override
    public IAuthPresenter getPresenter() {
        return mPresenter;
    }


    @Override
    public void showLoginBtn() {
        mLoginBtn.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideLoginBtn() {
        mLoginBtn.setVisibility(View.GONE);
    }


    @Override
    public AuthPanel getAuthPanel() {
        return mAuthPanel;
    }


    @Override
    public void showCatalogScreen() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        finish();
    }


    //endregion


    @Override
    public void onBackPressed() {

        if (!mAuthPanel.isIdle()) {
            mAuthPanel.setCustomState(AuthPanel.IDLE_STATE);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.show_catalog_btn:
                mPresenter.clickOnShowCatalog();
                break;
            case R.id.login_btn:
                mPresenter.clickOnLogin();
                break;
        }
    }


    //region ============================== DI ==============================


    @dagger.Module
    public class Module {

        @Provides
        @AuthScope
        AuthPresenter provideAuthPresenter() {
            return new AuthPresenter();
        }
    }


    @dagger.Component(modules = Module.class)
    @AuthScope
    interface Component {
        void inject(SplashActivity activity);
    }


    private Component createDaggerComponent() {
        return DaggerSplashActivity_Component.builder()
                .module(new Module())
                .build();
    }


    //endregion
}
