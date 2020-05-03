package com.titvt.yinle.main;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.titvt.yinle.bean.AlbumDetail;
import com.titvt.yinle.bean.AlbumInfo;
import com.titvt.yinle.bean.Lyric;
import com.titvt.yinle.bean.SongDetail;
import com.titvt.yinle.bean.SongInfo;
import com.titvt.yinle.bean.UserInfo;
import com.titvt.yinle.util.httpss.Httpss;
import com.titvt.yinle.util.httpss.HttpssCallback;
import com.titvt.yinle.util.jsoff.JSOFF;

import java.util.ArrayList;
import java.util.List;

public class MainRepository {
    private MainModel mainModel;
    private Context context;
    private MainViewModel mainViewModel;
    private MutableLiveData<UserInfo> userInfo;
    private MutableLiveData<AlbumDetail> albumDetail;
    private MutableLiveData<AlbumDetail> currentAlbum;
    private MutableLiveData<List<AlbumInfo>> banners;
    private MutableLiveData<SongDetail> currentPlaying;
    private MutableLiveData<Lyric> currentLyric;
    private MutableLiveData<List<SongInfo>> searchSongs;

    MainRepository(Context context, MainViewModel mainViewModel) {
        mainModel = new MainModel(context);
        this.context = context;
        this.mainViewModel = mainViewModel;
        userInfo = new MutableLiveData<>();
        albumDetail = new MutableLiveData<>();
        currentAlbum = new MutableLiveData<>();
        banners = new MutableLiveData<>();
        currentPlaying = new MutableLiveData<>();
        currentLyric = new MutableLiveData<>();
        searchSongs = new MutableLiveData<>(new ArrayList<>());
        updateUserInfo();
        updateBanners();
    }

    public long getUid() {
        return mainModel.getUid();
    }

    private void updateUserInfo() {
        new Httpss("http://47.99.165.194/user/detail?uid=" + mainModel.getUid()).setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                JSOFF profile = jsoff.getJSOFF("profile");
                String backgroundUrl = profile.getString("backgroundUrl");
                String nickname = profile.getString("nickname");
                long city = profile.getBigInteger("city").longValue();
                long followeds = profile.getBigInteger("followeds").longValue();
                long playlistCount = profile.getBigInteger("playlistCount").longValue();
                long follows = profile.getBigInteger("follows").longValue();
                userInfo.setValue(new UserInfo(backgroundUrl, nickname, city, followeds, playlistCount, follows));
            }

            @Override
            public void onHttpssFail(int responseCode) {

            }

            @Override
            public void onHttpssError() {

            }
        }).request();
    }

    MutableLiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    void updateAlbumDetail(long id) {
        albumDetail.postValue(new AlbumDetail(new AlbumInfo(), new ArrayList<>()));
        new Httpss("http://47.99.165.194/playlist/detail?id=" + id).setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                JSOFF playlist = jsoff.getJSOFF("playlist");
                long id = playlist.getBigInteger("id").longValue();
                String coverImgUrl = playlist.getString("coverImgUrl");
                String name = playlist.getString("name");
                String nickname = playlist.getJSOFF("creator").getString("nickname");
                long trackCount = playlist.getBigInteger("trackCount").longValue();
                List<SongInfo> songInfos = new ArrayList<>();
                ArrayList<JSOFF> tracks = playlist.getJSOFFArray("tracks");
                for (JSOFF item : tracks) {
                    long id2 = item.getBigInteger("id").longValue();
                    String name2 = item.getString("name");
                    long dt = item.getBigInteger("dt").longValue();
                    long fee = item.getBigInteger("fee").longValue();
                    String picUrl = item.getJSOFF("al").getString("picUrl");
                    String ar = item.getJSOFFArray("ar").get(0).getString("name");
                    songInfos.add(new SongInfo(id2, name2, dt, fee, picUrl, ar));
                }
                albumDetail.setValue(new AlbumDetail(new AlbumInfo(id, coverImgUrl, name, nickname, trackCount), songInfos));
            }

            @Override
            public void onHttpssFail(int responseCode) {

            }

            @Override
            public void onHttpssError() {

            }
        }).request();
    }

    MutableLiveData<AlbumDetail> getAlbumDetail() {
        return albumDetail;
    }

    void updateCurrentAlbum(long id) {
        new Httpss("http://47.99.165.194/playlist/detail?id=" + id).setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                JSOFF playlist = jsoff.getJSOFF("playlist");
                long id = playlist.getBigInteger("id").longValue();
                String coverImgUrl = playlist.getString("coverImgUrl");
                String name = playlist.getString("name");
                String nickname = playlist.getJSOFF("creator").getString("nickname");
                long trackCount = playlist.getBigInteger("trackCount").longValue();
                List<SongInfo> songInfos = new ArrayList<>();
                ArrayList<JSOFF> tracks = playlist.getJSOFFArray("tracks");
                for (JSOFF item : tracks) {
                    long id2 = item.getBigInteger("id").longValue();
                    String name2 = item.getString("name");
                    long dt = item.getBigInteger("dt").longValue();
                    long fee = item.getBigInteger("fee").longValue();
                    String picUrl = item.getJSOFF("al").getString("picUrl");
                    String ar = item.getJSOFFArray("ar").get(0).getString("name");
                    songInfos.add(new SongInfo(id2, name2, dt, fee, picUrl, ar));
                }
                currentAlbum.setValue(new AlbumDetail(new AlbumInfo(id, coverImgUrl, name, nickname, trackCount), songInfos));
                mainViewModel.playAlbum();
            }

            @Override
            public void onHttpssFail(int responseCode) {

            }

            @Override
            public void onHttpssError() {

            }
        }).request();
    }

    void updateCurrentAlbumAndPlay(long id, long song) {
        new Httpss("http://47.99.165.194/playlist/detail?id=" + id).setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                JSOFF playlist = jsoff.getJSOFF("playlist");
                long id = playlist.getBigInteger("id").longValue();
                String coverImgUrl = playlist.getString("coverImgUrl");
                String name = playlist.getString("name");
                String nickname = playlist.getJSOFF("creator").getString("nickname");
                long trackCount = playlist.getBigInteger("trackCount").longValue();
                List<SongInfo> songInfos = new ArrayList<>();
                ArrayList<JSOFF> tracks = playlist.getJSOFFArray("tracks");
                for (JSOFF item : tracks) {
                    long id2 = item.getBigInteger("id").longValue();
                    String name2 = item.getString("name");
                    long dt = item.getBigInteger("dt").longValue();
                    long fee = item.getBigInteger("fee").longValue();
                    String picUrl = item.getJSOFF("al").getString("picUrl");
                    String ar = item.getJSOFFArray("ar").get(0).getString("name");
                    songInfos.add(new SongInfo(id2, name2, dt, fee, picUrl, ar));
                }
                currentAlbum.setValue(new AlbumDetail(new AlbumInfo(id, coverImgUrl, name, nickname, trackCount), songInfos));
                mainViewModel.playSong(song);
            }

            @Override
            public void onHttpssFail(int responseCode) {

            }

            @Override
            public void onHttpssError() {

            }
        }).request();
    }

    MutableLiveData<AlbumDetail> getCurrentAlbum() {
        return currentAlbum;
    }

    private void updateBanners() {
        new Httpss("http://47.99.165.194/album/newest").setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                List<AlbumInfo> albumInfoList = new ArrayList<>();
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                ArrayList<JSOFF> albums = jsoff.getJSOFFArray("albums");
                for (JSOFF item : albums) {
                    long id = item.getBigInteger("id").longValue();
                    String picUrl = item.getString("picUrl");
                    String name = item.getString("name");
                    String nickname = item.getJSOFF("artist").getString("name");
                    long size = item.getBigInteger("size").longValue();
                    albumInfoList.add(new AlbumInfo(id, picUrl, name, nickname, size));
                }
                banners.setValue(albumInfoList);
            }

            @Override
            public void onHttpssFail(int responseCode) {

            }

            @Override
            public void onHttpssError() {

            }
        }).request();
    }

    MutableLiveData<List<AlbumInfo>> getBanners() {
        return banners;
    }

    void updateAlbumBanner(long id) {
        albumDetail.postValue(new AlbumDetail(new AlbumInfo(), new ArrayList<>()));
        new Httpss("http://47.99.165.194/album?id=" + id).setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                JSOFF album = jsoff.getJSOFF("album");
                long id = album.getBigInteger("id").longValue();
                String picUrl = album.getString("picUrl");
                String name = album.getString("name");
                String nickname = album.getJSOFF("artist").getString("name");
                long size = album.getBigInteger("size").longValue();
                List<SongInfo> songInfos = new ArrayList<>();
                ArrayList<JSOFF> songs = jsoff.getJSOFFArray("songs");
                for (JSOFF item : songs) {
                    long id2 = item.getBigInteger("id").longValue();
                    String name2 = item.getString("name");
                    long dt = item.getBigInteger("dt").longValue();
                    long fee = item.getBigInteger("fee").longValue();
                    String picUrl2 = item.getJSOFF("al").getString("picUrl");
                    String ar = item.getJSOFFArray("ar").get(0).getString("name");
                    songInfos.add(new SongInfo(id2, name2, dt, fee, picUrl2, ar));
                }
                albumDetail.setValue(new AlbumDetail(new AlbumInfo(id, picUrl, name, nickname, size), songInfos));
            }

            @Override
            public void onHttpssFail(int responseCode) {

            }

            @Override
            public void onHttpssError() {

            }
        }).request();
    }

    private void updateCurrentPlaying(long id) {
        new Httpss("http://47.99.165.194/song/detail?ids=" + id).setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                JSOFF songs = jsoff.getJSOFFArray("songs").get(0);
                long id2 = songs.getBigInteger("id").longValue();
                String name = songs.getString("name");
                long dt = songs.getBigInteger("dt").longValue();
                long fee = songs.getBigInteger("fee").longValue();
                String picUrl = songs.getJSOFF("al").getString("picUrl");
                String ar = songs.getJSOFFArray("ar").get(0).getString("name");
                SongInfo songInfo = new SongInfo(id2, name, dt, fee, picUrl, ar);
                if (fee == 1)
                    Toast.makeText(context, "此歌曲为付费歌曲，白嫖失败", Toast.LENGTH_SHORT).show();
                else
                    new Httpss("http://47.99.165.194/song/url?id=" + id).setCallback(new HttpssCallback() {
                        @Override
                        public void onHttpssOK(byte[] data) {
                            String json = new String(data);
                            JSOFF jsoff = new JSOFF(json);
                            String url = jsoff.getJSOFFArray("data").get(0).getString("url");
                            currentPlaying.setValue(new SongDetail(songInfo, url));
                            new Httpss("http://47.99.165.194/lyric?id=" + id).setCallback(new HttpssCallback() {
                                @Override
                                public void onHttpssOK(byte[] data) {
                                    List<Integer> times = new ArrayList<>();
                                    List<String> lyrics = new ArrayList<>();
                                    String json = new String(data);
                                    json = json.replaceAll("\\\\n", "\n");
                                    JSOFF jsoff = new JSOFF(json);
                                    if (jsoff.getBoolean("nolyric") != null && jsoff.getBoolean("nolyric")) {
                                        times.add(0);
                                        lyrics.add("此歌曲无歌词");
                                    } else {
                                        String lyric = jsoff.getJSOFF("lrc").getString("lyric");
                                        lyric = lyric.substring(lyric.indexOf("[0"), lyric.length() - 1);
                                        String[] items = lyric.split("\n");
                                        for (String item : items) {
                                            String[] parts = item.substring(1).split("]");
                                            if (parts.length == 2) {
                                                String[] parts2 = parts[0].split(":");
                                                times.add(Integer.parseInt(parts2[0]) * 60000 +
                                                        (int) (Double.parseDouble(parts2[1]) * 1000));
                                                lyrics.add(parts[1]);
                                            }
                                        }
                                    }
                                    currentLyric.setValue(new Lyric(times, lyrics));
                                    mainViewModel.playSongCallback();
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
                        public void onHttpssFail(int responseCode) {

                        }

                        @Override
                        public void onHttpssError() {

                        }
                    }).request();
            }

            @Override
            public void onHttpssFail(int responseCode) {

            }

            @Override
            public void onHttpssError() {

            }
        }).request();
    }

    MutableLiveData<SongDetail> getCurrentPlaying() {
        return currentPlaying;
    }

    void playSong(long id) {
        updateCurrentPlaying(id);
    }

    MutableLiveData<Lyric> getCurrentLyric() {
        return currentLyric;
    }

    void updateSearchSongs(String keywords) {
        new Httpss("http://47.99.165.194/search?keywords=" + keywords + "&limit=100").setCallback(new HttpssCallback() {
            @Override
            public void onHttpssOK(byte[] data) {
                List<SongInfo> songInfoList = new ArrayList<>();
                String json = new String(data);
                JSOFF jsoff = new JSOFF(json);
                ArrayList<JSOFF> songs = jsoff.getJSOFF("result").getJSOFFArray("songs");
                for (JSOFF item : songs) {
                    long id = item.getBigInteger("id").longValue();
                    String name = item.getString("name");
                    String nickname = item.getJSOFFArray("artists").get(0).getString("name");
                    songInfoList.add(new SongInfo(id, name, 0, 0, "", nickname));
                }
                searchSongs.setValue(songInfoList);
            }

            @Override
            public void onHttpssFail(int responseCode) {

            }

            @Override
            public void onHttpssError() {

            }
        }).request();
    }

    MutableLiveData<List<SongInfo>> getSearchSongs() {
        return searchSongs;
    }
}
