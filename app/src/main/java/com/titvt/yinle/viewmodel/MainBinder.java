package com.titvt.yinle.viewmodel;

import android.os.Binder;

class MainBinder extends Binder {
    private IMainService mainService;
    private MainViewModel mainViewModel;

    MainBinder(IMainService mainService) {
        this.mainService = mainService;
    }

    IMainService getMainService() {
        return mainService;
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }
}
