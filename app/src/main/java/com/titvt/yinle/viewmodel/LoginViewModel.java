package com.titvt.yinle.viewmodel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.titvt.yinle.view.MainActivity;

public class LoginViewModel extends AndroidViewModel {
    private Application application;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void onClickLogin() {
        Intent intent = new Intent(application, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        application.startActivity(intent);
    }
}
