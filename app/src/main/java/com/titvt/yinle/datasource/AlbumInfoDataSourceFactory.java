package com.titvt.yinle.datasource;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.titvt.yinle.bean.AlbumInfo;

public class AlbumInfoDataSourceFactory extends DataSource.Factory<Integer, AlbumInfo> {
    private int uid;

    public AlbumInfoDataSourceFactory(int uid) {
        this.uid = uid;
    }

    @NonNull
    @Override
    public DataSource<Integer, AlbumInfo> create() {
        return new AlbumInfoDataSource(uid);
    }
}
