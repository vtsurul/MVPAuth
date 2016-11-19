package com.softdesign.mvpauth.mvp.models;

public class AuthModel  extends AbstractModel {

    public AuthModel() {

    }


    public boolean isAuthUser() {

        // TODO: 02.11.2016 search token in sharedpreferences
        return false;
    }


    public void loginUser(String email, String password) {
        // TODO: 02.11.2016 send data to server for auth
    }

}
