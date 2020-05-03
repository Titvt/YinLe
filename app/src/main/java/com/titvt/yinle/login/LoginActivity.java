package com.titvt.yinle.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.titvt.yinle.R;
import com.titvt.yinle.databinding.ActivityLoginBinding;
import com.titvt.yinle.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        activityLoginBinding.setUid(loginViewModel.getUid());
        activityLoginBinding.login.setOnClickListener(v -> {
            loginViewModel.login();
            startActivity(new Intent(this, MainActivity.class).putExtra("firstStart", false));
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
