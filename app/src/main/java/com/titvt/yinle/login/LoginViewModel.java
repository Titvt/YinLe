package com.titvt.yinle.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository loginRepository;
    private MutableLiveData<String> uid;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository(application);
        uid = new MutableLiveData<>("318082831");
    }

    public MutableLiveData<String> getUid() {
        return uid;
    }

    void login() {
        if (uid.getValue() != null)
            loginRepository.setUid(Integer.parseInt(uid.getValue()));
    }
}
