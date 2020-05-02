package com.titvt.yinle.bean;

import java.util.List;

public class AlbumDetail {
    private AlbumInfo albumInfo;
    private List<SongInfo> songInfos;

    public AlbumDetail(AlbumInfo albumInfo, List<SongInfo> songInfos) {
        this.albumInfo = albumInfo;
        this.songInfos = songInfos;
    }

    public AlbumInfo getAlbumInfo() {
        return albumInfo;
    }

    public List<SongInfo> getSongInfos() {
        return songInfos;
    }
}
