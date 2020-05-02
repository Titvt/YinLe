package com.titvt.yinle.bean;

public class UserInfo {
    private String backgroundUrl;
    private String nickname;
    private long city;
    private long followeds;
    private long playlistCount;
    private long follows;

    public UserInfo(String backgroundUrl, String nickname, long city, long followeds, long playlistCount, long follows) {
        this.backgroundUrl = backgroundUrl;
        this.nickname = nickname;
        this.city = city;
        this.followeds = followeds;
        this.playlistCount = playlistCount;
        this.follows = follows;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public long getCity() {
        return city;
    }

    public long getFolloweds() {
        return followeds;
    }

    public long getPlaylistCount() {
        return playlistCount;
    }

    public long getFollows() {
        return follows;
    }
}
