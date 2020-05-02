package com.titvt.yinle.main;

import android.content.Context;

public class MainModel {
    private Context context;

    MainModel(Context context) {
        this.context = context;
    }

    public int getUid() {
        return context.getSharedPreferences("YinLe", Context.MODE_PRIVATE).getInt("uid", 0);
    }
}
