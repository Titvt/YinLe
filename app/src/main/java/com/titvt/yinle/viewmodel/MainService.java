package com.titvt.yinle.viewmodel;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MainService extends Service implements IMainService, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private MainBinder mainBinder;
    private MediaPlayer mediaPlayer;
    private String url;

    @Override
    public void onCreate() {
        super.onCreate();
        mainBinder = new MainBinder(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mainBinder;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    @Override
    public void play(String url) {
        if (url.equals(this.url)) {
            mediaPlayer.start();
        } else {
            this.url = url;
            try {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getCurrentTime() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getRemainTime() {
        return mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mainBinder.getMainViewModel().playNext();
    }

    @Override
    public void setProgress(int progress) {
        mediaPlayer.seekTo(progress);
    }
}
