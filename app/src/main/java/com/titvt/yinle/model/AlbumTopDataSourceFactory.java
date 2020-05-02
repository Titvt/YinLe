package com.titvt.yinle.model;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.titvt.yinle.bean.AlbumInfo;

public class AlbumTopDataSourceFactory extends DataSource.Factory<Integer, AlbumInfo> {
    @NonNull
    @Override
    public DataSource<Integer, AlbumInfo> create() {
        return new AlbumTopDataSource();
    }
}
