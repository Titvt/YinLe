package com.titvt.yinle.service;

import android.os.Binder;

import com.titvt.yinle.main.MainViewModel;

public class MainBinder extends Binder {
    private IMainService mainService;
    private MainViewModel mainViewModel;

    MainBinder(IMainService mainService) {
        this.mainService = mainService;
    }

    public IMainService getMainService() {
        return mainService;
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }
}
