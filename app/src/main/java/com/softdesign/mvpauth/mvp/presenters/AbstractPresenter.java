package com.softdesign.mvpauth.mvp.presenters;

import android.support.annotation.Nullable;

import com.softdesign.mvpauth.mvp.views.IView;

public abstract class AbstractPresenter<T extends IView> {

    private T mView;


    public void takeView(T view) {
        mView = view;
    }


    public void dropView() {
        mView = null;
    }


    public abstract void initView();


    @Nullable
    public T getView() {
        return mView;
    }
}
