package com.titvt.yinle.service;

public interface IMainService {
    void play(String url);

    void pause();

    int getCurrentTime();

    int getRemainTime();

    void setProgress(int progress);
}
