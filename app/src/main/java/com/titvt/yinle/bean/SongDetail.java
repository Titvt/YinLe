package com.titvt.yinle.bean;

public class SongDetail {
    private SongInfo songInfo;
    private String url;

    public SongDetail(SongInfo songInfo, String url) {
        this.songInfo = songInfo;
        this.url = url;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public String getUrl() {
        return url;
    }
}
