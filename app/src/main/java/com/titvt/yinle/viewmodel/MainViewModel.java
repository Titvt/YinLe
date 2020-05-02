package com.titvt.yinle.viewmodel;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.titvt.yinle.R;
import com.titvt.yinle.bean.AlbumDetail;
import com.titvt.yinle.bean.AlbumInfo;
import com.titvt.yinle.bean.SongDetail;
import com.titvt.yinle.bean.SongInfo;
import com.titvt.yinle.bean.UserInfo;
import com.titvt.yinle.model.AlbumInfoDataSourceFactory;
import com.titvt.yinle.model.AlbumTopDataSourceFactory;
import com.titvt.yinle.repository.MainRepository;

import java.util.List;
import java.util.Random;

public class MainViewModel extends AndroidViewModel implements ServiceConnection {
    private MainRepository mainRepository;
    private IMainService mainService;
    private MutableLiveData<Integer> pageStatus;
    private MutableLiveData<UserInfo> userInfo;
    private LiveData<PagedList<AlbumInfo>> albumInfos;
    private MutableLiveData<AlbumDetail> albumDetail;
    private MutableLiveData<SongDetail> currentPlaying;
    private MutableLiveData<AlbumDetail> currentAlbum;
    private MutableLiveData<Boolean> isShaffle;
    private MutableLiveData<List<AlbumInfo>> banners;
    private LiveData<PagedList<AlbumInfo>> albumTops;
    private MutableLiveData<Boolean> isPlaying;
    private long recentViewedAlbum;
    private long previousAlbum;
    private MutableLiveData<Boolean> monitor;
    private boolean isSongExist;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application, this);
        application.bindService(new Intent(application, MainService.class), this, Context.BIND_AUTO_CREATE);
        pageStatus = new MutableLiveData<>();
        userInfo = mainRepository.getUserInfo();
        albumInfos = new LivePagedListBuilder<>(new AlbumInfoDataSourceFactory(mainRepository.getUid()), new PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(40)
                .setMaxSize(Integer.MAX_VALUE)
                .build()).build();
        albumDetail = mainRepository.getAlbumDetail();
        currentPlaying = mainRepository.getCurrentPlaying();
        currentAlbum = mainRepository.getCurrentAlbum();
        isShaffle = new MutableLiveData<>(false);
        banners = mainRepository.getBanners();
        albumTops = new LivePagedListBuilder<>(new AlbumTopDataSourceFactory(), new PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(40)
                .setMaxSize(Integer.MAX_VALUE)
                .build()).build();
        isPlaying = new MutableLiveData<>(false);
        monitor = new MutableLiveData<Boolean>(false);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (monitor.getValue() != null)
                        monitor.postValue(!monitor.getValue());
                    try {
                        sleep(1000);
                    } catch (Exception ignored) {
                    }
                }
            }
        }.start();
    }

    public MutableLiveData<Integer> getPageStatus() {
        return pageStatus;
    }

    public MutableLiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    public LiveData<PagedList<AlbumInfo>> getAlbumInfos() {
        return albumInfos;
    }

    public MutableLiveData<AlbumDetail> getAlbumDetail() {
        return albumDetail;
    }

    public void pageBack() {
        pageStatus.postValue(-1);
    }

    public void playSong(long id) {
        isSongExist = true;
        if (currentPlaying.getValue() == null || currentPlaying.getValue().getSongInfo().getId() != id)
            mainRepository.playSong(id);
    }

    public void playAlbumSong(long id) {
        isSongExist = true;
        if (recentViewedAlbum == previousAlbum)
            playSong(id);
        else {
            mainRepository.updateCurrentAlbumAndPlay(recentViewedAlbum, id);
            previousAlbum = recentViewedAlbum;
        }
    }

    public void playAlbum() {
        isSongExist = true;
        if (currentAlbum.getValue() != null && isShaffle.getValue() != null && currentPlaying.getValue() != null && currentAlbum.getValue().getSongInfos().size() > 0) {
            if (isShaffle.getValue()) {
                int index;
                do
                    index = new Random().nextInt(currentAlbum.getValue().getSongInfos().size());
                while (index == currentPlaying.getValue().getSongInfo().getId());
                playSong(currentAlbum.getValue().getSongInfos().get(index).getId());
            } else {
                boolean flag = false;
                for (SongInfo songInfo : currentAlbum.getValue().getSongInfos()) {
                    if (flag) {
                        playSong(songInfo.getId());
                        return;
                    }
                    if (songInfo.getId() == currentPlaying.getValue().getSongInfo().getId()) {
                        flag = true;
                    }
                }
                playSong(currentAlbum.getValue().getSongInfos().get(0).getId());
            }
        }
    }

    public void firstPlayAlbum(long id) {
        isSongExist = true;
        if (currentAlbum.getValue() == null || currentAlbum.getValue().getAlbumInfo().getId() != id)
            mainRepository.updateCurrentAlbum(id);
    }

    public void viewAlbum(long id) {
        mainRepository.updateAlbumDetail(id);
        recentViewedAlbum = id;
        pageStatus.postValue(1);
    }

    public void viewSongAlbum() {
        mainRepository.updateAlbumDetail(previousAlbum);
        recentViewedAlbum = previousAlbum;
        pageStatus.postValue(1);
    }

    public MutableLiveData<SongDetail> getCurrentPlaying() {
        return currentPlaying;
    }

    public int getItemColor(long id) {
        if (currentPlaying.getValue() != null && currentPlaying.getValue().getSongInfo().getId() == id)
            return getApplication().getResources().getColor(R.color.blueBackground);
        else
            return getApplication().getResources().getColor(R.color.blackText);
    }

    public int getItemSubColor(long id) {
        if (currentPlaying.getValue() != null && currentPlaying.getValue().getSongInfo().getId() == id)
            return getApplication().getResources().getColor(R.color.blueBackground);
        else
            return getApplication().getResources().getColor(R.color.lightGreyText);
    }

    public String getFormatTime(long dt) {
        int minute = (int) dt / 60000;
        int second = ((int) dt - minute * 60000) / 1000;
        return (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second;
    }

    public MutableLiveData<List<AlbumInfo>> getBanners() {
        return banners;
    }

    public void viewAlbumBanner(long id) {
        mainRepository.updateAlbumBanner(id);
        pageStatus.postValue(1);
    }

    public LiveData<PagedList<AlbumInfo>> getAlbumTops() {
        return albumTops;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mainService = ((MainBinder) service).getMainService();
        ((MainBinder) service).setMainViewModel(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public void playSongCallback() {
        if (mainService != null && currentPlaying.getValue() != null) {
            mainService.play(currentPlaying.getValue().getUrl());
            isPlaying.postValue(true);
        }
    }

    private void pauseSong() {
        mainService.pause();
        isPlaying.postValue(false);
    }

    public String getCurrentTime() {
        return getFormatTime(mainService.getCurrentTime());
    }

    public String getRemainTime() {
        return getFormatTime(mainService.getRemainTime());
    }

    public void playNext() {
        if (currentAlbum.getValue() == null)
            return;
        if (isShaffle.getValue() != null && isShaffle.getValue()) {
            playAlbum();
        } else {
            boolean flag = false;
            for (SongInfo songInfo : currentAlbum.getValue().getSongInfos()) {
                if (flag) {
                    playSong(songInfo.getId());
                    return;
                }
                if (songInfo.getId() == currentPlaying.getValue().getSongInfo().getId())
                    flag = true;
            }
            playSong(currentAlbum.getValue().getSongInfos().get(0).getId());
        }
    }

    public void playPrevious() {
        if (isShaffle.getValue() != null && isShaffle.getValue()) {
            playAlbum();
        } else {
            SongInfo previous = currentAlbum.getValue().getSongInfos().get(currentAlbum.getValue().getSongInfos().size() - 1);
            for (SongInfo songInfo : currentAlbum.getValue().getSongInfos()) {
                if (songInfo.getId() != currentPlaying.getValue().getSongInfo().getId())
                    previous = songInfo;
                else {
                    playSong(previous.getId());
                    return;
                }
            }
        }
    }

    public void enterSong() {
        if (isSongExist)
            pageStatus.postValue(2);
    }

    public void switchStatus() {
        if (isPlaying.getValue() != null && isPlaying.getValue())
            pauseSong();
        else
            playSongCallback();
    }

    public MutableLiveData<Boolean> getIsPlaying() {
        return isPlaying;
    }

    public MutableLiveData<Boolean> getIsShaffle() {
        return isShaffle;
    }

    public void switchShaffle() {
        isShaffle.setValue(true);
    }

    public void switchNoShaffle() {
        isShaffle.setValue(false);
    }

    public void reverseShaffle() {
        if (isShaffle.getValue() != null)
            isShaffle.setValue(!isShaffle.getValue());
    }

    public MutableLiveData<Boolean> getMonitor() {
        return monitor;
    }

    public int getProgress() {
        return mainService.getCurrentTime();
    }

    public void setProgress(int progress) {
        mainService.setProgress(progress);
    }
}
