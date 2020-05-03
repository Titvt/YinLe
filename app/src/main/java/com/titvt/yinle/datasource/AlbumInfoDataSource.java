package com.titvt.yinle.datasource;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.titvt.yinle.bean.AlbumInfo;
import com.titvt.yinle.util.httpss.Httpss;
import com.titvt.yinle.util.httpss.HttpssCallback;
import com.titvt.yinle.util.jsoff.JSOFF;

import java.util.ArrayList;
import java.util.List;

public class AlbumInfoDataSource extends PositionalDataSource<AlbumInfo> {
    private int uid;

    AlbumInfoDataSource(int uid) {
        this.uid = uid;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<AlbumInfo> callback) {
        new Httpss("http://47.99.165.194/user/playlist?uid=" + uid + "&limit=39").setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                List<AlbumInfo> albumInfoList = new ArrayList<>();
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                ArrayList<JSOFF> playlist = jsoff.getJSOFFArray("playlist");
                for (JSOFF item : playlist) {
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
        new Httpss("http://47.99.165.194/user/playlist?uid=" + uid + "&limit=" + (params.startPosition + 19))
                .setCallback(new HttpssCallback() {
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
