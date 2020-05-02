package com.titvt.yinle.bean;

public class SongInfo {
    private long id;
    private String name;
    private long dt;
    private long fee;
    private String picUrl;
    private String ar;

    public SongInfo(){

    }

    public SongInfo(long id, String name, long dt, long fee, String picUrl, String ar) {
        this.id = id;
        this.name = name;
        this.dt = dt;
        this.fee = fee;
        this.picUrl = picUrl;
        this.ar = ar;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getDt() {
        return dt;
    }

    public long getFee() {
        return fee;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getAr() {
        return ar;
    }
}
