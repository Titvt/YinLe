package com.titvt.yinle.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.titvt.yinle.R;
import com.titvt.yinle.adapter.BannerRecyclerViewAdapter;
import com.titvt.yinle.adapter.HomeRecyclerViewAdapter;
import com.titvt.yinle.bean.AlbumInfo;
import com.titvt.yinle.bean.SongInfo;
import com.titvt.yinle.databinding.FragmentHomeBinding;
import com.titvt.yinle.main.MainViewModel;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding fragmentHomeBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_home, container, false);
        ViewModelStoreOwner viewModelStoreOwner = getActivity();
        if (viewModelStoreOwner == null)
            viewModelStoreOwner = this;
        MainViewModel mainViewModel = new ViewModelProvider(viewModelStoreOwner).get(MainViewModel.class);
        fragmentHomeBinding.setViewModel(mainViewModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        fragmentHomeBinding.banner.setLayoutManager(linearLayoutManager);
        fragmentHomeBinding.banner.setAdapter(new BannerRecyclerViewAdapter(
                getActivity(), mainViewModel, getLayoutInflater(), getActivity()));
        fragmentHomeBinding.recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(new DiffUtil.ItemCallback<AlbumInfo>() {
            @Override
            public boolean areItemsTheSame(@NonNull AlbumInfo oldItem, @NonNull AlbumInfo newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull AlbumInfo oldItem, @NonNull AlbumInfo newItem) {
                return oldItem.getId() == newItem.getId();
            }
        }, getActivity(), getLayoutInflater(), mainViewModel);
        fragmentHomeBinding.recyclerView.setAdapter(homeRecyclerViewAdapter);
        fragmentHomeBinding.setSongInfo(new SongInfo(0, "音乐（lè），享受白嫖的快感",
                0, 0, "", "我再强调一次，第二个字读快乐的乐！"));
        mainViewModel.getAlbumTops().observe(getActivity(), homeRecyclerViewAdapter::submitList);
        mainViewModel.getCurrentPlaying().observe(getActivity(), songDetail ->
                fragmentHomeBinding.setSongInfo(songDetail.getSongInfo()));
        mainViewModel.getIsPlaying().observe(getActivity(), isPlaying ->
                fragmentHomeBinding.play.setImageResource(!isPlaying ? R.mipmap.play_blue : R.mipmap.pause));
        return fragmentHomeBinding.getRoot().getRootView();
    }
}
