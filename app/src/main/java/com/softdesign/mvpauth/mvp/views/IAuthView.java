package com.softdesign.mvpauth.mvp.views;

import android.support.annotation.Nullable;

import com.softdesign.mvpauth.mvp.presenters.IAuthPresenter;
import com.softdesign.mvpauth.ui.custom_views.AuthPanel;

public interface IAuthView {

    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();

    IAuthPresenter getPresenter();

    void showLoginBtn();
    void hideLoginBtn();

    //void testShowLoginCard();

    @Nullable
    AuthPanel getAuthPanel();
}
