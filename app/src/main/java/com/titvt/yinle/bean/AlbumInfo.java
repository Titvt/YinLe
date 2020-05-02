package com.titvt.yinle.bean;

public class AlbumInfo {
    private long id;
    private String coverImgUrl;
    private String name;
    private String nickname;
    private long trackCount;

    public AlbumInfo(){

    }

    public AlbumInfo(long id, String coverImgUrl, String name, String nickname, long trackCount) {
        this.id = id;
        this.coverImgUrl = coverImgUrl;
        this.name = name;
        this.nickname = nickname;
        this.trackCount = trackCount;
    }

    public long getId() {
        return id;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public long getTrackCount() {
        return trackCount;
    }
}
