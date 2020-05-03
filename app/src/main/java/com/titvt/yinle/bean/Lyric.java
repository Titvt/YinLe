package com.titvt.yinle.bean;

import java.util.List;

public class Lyric {
    private List<Integer> times;
    private List<String> lyrics;

    public Lyric(List<Integer> times, List<String> lyrics) {
        this.times = times;
        this.lyrics = lyrics;
    }

    public List<Integer> getTimes() {
        return times;
    }

    public List<String> getLyrics() {
        return lyrics;
    }
}
