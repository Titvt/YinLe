package com.titvt.yinle.login;

import android.content.Context;

public class LoginRepository {
    private LoginModel loginModel;

    LoginRepository(Context context) {
        loginModel = new LoginModel(context);
    }

    public void setUid(int uid) {
        loginModel.setUid(uid);
    }
}
