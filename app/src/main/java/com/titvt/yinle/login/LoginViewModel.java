package com.titvt.yinle.login;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.titvt.yinle.main.MainActivity;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository loginRepository;
    private Application application;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository(application);
        this.application = application;
    }

    public void login(int uid) {
        loginRepository.setUid(uid);
        Intent intent = new Intent(application, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        application.startActivity(intent);
    }
}
