package com.softdesign.mvpauth.mvp.views;

public interface IRootView extends IView {

    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();

}
