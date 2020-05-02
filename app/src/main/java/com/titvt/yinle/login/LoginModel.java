package com.titvt.yinle.login;

import android.content.Context;

public class LoginModel {
    private Context context;

    LoginModel(Context context) {
        this.context = context;
    }

    public void setUid(int uid) {
        context.getSharedPreferences("YinLe", Context.MODE_PRIVATE).edit().putInt("uid", uid).apply();
    }
}
