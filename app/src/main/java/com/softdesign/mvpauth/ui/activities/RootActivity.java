package com.softdesign.mvpauth.ui.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.softdesign.mvpauth.BuildConfig;
import com.softdesign.mvpauth.R;
import com.softdesign.mvpauth.di.DaggerService;
import com.softdesign.mvpauth.di.scopes.RootScope;
import com.softdesign.mvpauth.mvp.presenters.RootPresenter;
import com.softdesign.mvpauth.mvp.views.IRootView;
import com.softdesign.mvpauth.mvp.views.IView;
import com.softdesign.mvpauth.ui.fragments.AccountFragment;
import com.softdesign.mvpauth.ui.fragments.CatalogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;

public class RootActivity extends AppCompatActivity implements IRootView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.coordinator_container)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    FragmentManager mFragmentManager;

    @Inject
    RootPresenter mRootPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);

        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);

        initToolbar();
        initDrawer();
        mRootPresenter.takeView(this);
        mRootPresenter.initView();
        // TODO: 18.11.2016 init View 

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new CatalogFragment())
                    .commit();
        }

    }

    @Override
    protected void onDestroy() {
        mRootPresenter.dropView();
        super.onDestroy();
    }

    private void initDrawer() {
        setSupportActionBar(mToolbar);
    }


    private void initToolbar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_account:
                fragment = new AccountFragment();
                break;
            case R.id.nav_catalog:
                // TODO: 17.11.2016 show catalog
                fragment = new CatalogFragment();
                break;
            case R.id.nav_favorites:
                break;
            case R.id.nav_orders:
                break;
            case R.id.nav_notification:
                break;
        }
        if (fragment != null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //region ============================== IRootView ==============================


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
            showMessage(getString(R.string.error_message));
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


    //endregion

    //region ================================= DI =================================

    private Component createDaggerComponent() {
        return DaggerRootActivity_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @RootScope
        RootPresenter provideRootPresenter() {
            return new RootPresenter();
        }
    }

    @dagger.Component(modules = Module.class)
    @RootScope
    public interface Component {
        void inject(RootActivity activity);
        RootPresenter getRootPresenter();
    }

    //endregion
}
