package com.titvt.yinle.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.titvt.yinle.R;
import com.titvt.yinle.bean.SongInfo;
import com.titvt.yinle.databinding.FragmentAlbumBinding;
import com.titvt.yinle.util.edilg.Edilg;
import com.titvt.yinle.viewmodel.MainViewModel;

public class AlbumFragment extends Fragment {

    public AlbumFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAlbumBinding fragmentAlbumBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false);
        ViewModelStoreOwner viewModelStoreOwner = getActivity();
        if (viewModelStoreOwner == null)
            viewModelStoreOwner = this;
        MainViewModel mainViewModel = new ViewModelProvider(viewModelStoreOwner).get(MainViewModel.class);
        fragmentAlbumBinding.setViewModel(mainViewModel);
        mainViewModel.getAlbumDetail().observe(getActivity(), albumDetail -> {
            fragmentAlbumBinding.setAlbumDetail(albumDetail);
            Edilg.with(getActivity()).load(albumDetail.getAlbumInfo().getCoverImgUrl()).into(fragmentAlbumBinding.cover);
        });
        fragmentAlbumBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentAlbumBinding.recyclerView.setAdapter(new AlbumRecyclerViewAdapter(mainViewModel, getLayoutInflater(), getActivity()));
        fragmentAlbumBinding.setSongInfo(new SongInfo(0, "音乐（lè），享受白嫖的快感", 0, 0, "", "我再强调一次，第二个字读快乐的乐！"));
        mainViewModel.getCurrentPlaying().observe((LifecycleOwner) viewModelStoreOwner, songDetail -> fragmentAlbumBinding.setSongInfo(songDetail.getSongInfo()));
        mainViewModel.getIsPlaying().observe((LifecycleOwner) viewModelStoreOwner, isPlaying -> fragmentAlbumBinding.play.setImageResource(!isPlaying ? R.mipmap.play_blue : R.mipmap.pause));
        mainViewModel.getIsShaffle().observe((LifecycleOwner) viewModelStoreOwner, isShaffle -> fragmentAlbumBinding.shaffle.setImageResource(isShaffle ? R.mipmap.shaffle : R.mipmap.repeat));
        return fragmentAlbumBinding.getRoot().getRootView();
    }
}
