package com.titvt.yinle.datasource;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.titvt.yinle.bean.AlbumInfo;
import com.titvt.yinle.util.httpss.Httpss;
import com.titvt.yinle.util.httpss.HttpssCallback;
import com.titvt.yinle.util.jsoff.JSOFF;

import java.util.ArrayList;
import java.util.List;

public class AlbumTopDataSource extends PositionalDataSource<AlbumInfo> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<AlbumInfo> callback) {
        new Httpss("http://47.99.165.194/top/playlist?limit=40").setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                List<AlbumInfo> albumInfoList = new ArrayList<>();
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                ArrayList<JSOFF> playlists = jsoff.getJSOFFArray("playlists");
                for (JSOFF item : playlists) {
                    long id = item.getBigInteger("id").longValue();
                    String coverImgUrl = item.getString("coverImgUrl");
                    String name = item.getString("name");
                    String nickname = item.getJSOFF("creator").getString("nickname");
                    long trackCount = item.getBigInteger("trackCount").longValue();
                    albumInfoList.add(new AlbumInfo(id, coverImgUrl, name, nickname, trackCount));
                }
                callback.onResult(albumInfoList, 0, Integer.MAX_VALUE);
            }

            @Override
            public void onHttpssFail(int responseCode) {

            }

            @Override
            public void onHttpssError() {

            }
        }).request();
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<AlbumInfo> callback) {
        new Httpss("http://47.99.165.194/top/playlist?limit=" + (params.startPosition + 20)).setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                List<AlbumInfo> albumInfoList = new ArrayList<>();
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                ArrayList<JSOFF> playlist = jsoff.getJSOFFArray("playlist");
                int offset = params.startPosition;
                for (JSOFF item : playlist) {
                    if (offset > 0) {
                        offset--;
                        continue;
                    }
                    long id = item.getBigInteger("id").longValue();
                    String coverImgUrl = item.getString("coverImgUrl");
                    String name = item.getString("name");
                    String nickname = item.getJSOFF("creator").getString("nickname");
                    long trackCount = item.getBigInteger("trackCount").longValue();
                    albumInfoList.add(new AlbumInfo(id, coverImgUrl, name, nickname, trackCount));
                }
                callback.onResult(albumInfoList);
            }

            @Override
            public void onHttpssFail(int responseCode) {

            }

            @Override
            public void onHttpssError() {

            }
        }).request();
    }
}