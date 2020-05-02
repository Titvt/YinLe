package com.titvt.yinle.view;

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

import com.titvt.yinle.R;
import com.titvt.yinle.bean.AlbumInfo;
import com.titvt.yinle.bean.SongInfo;
import com.titvt.yinle.databinding.FragmentLibraryBinding;
import com.titvt.yinle.viewmodel.MainViewModel;

public class LibraryFragment extends Fragment {

    public LibraryFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLibraryBinding fragmentLibraryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_library, container, false);
        ViewModelStoreOwner viewModelStoreOwner = getActivity();
        if (viewModelStoreOwner == null)
            viewModelStoreOwner = this;
        MainViewModel mainViewModel = new ViewModelProvider(viewModelStoreOwner).get(MainViewModel.class);
        fragmentLibraryBinding.setViewModel(mainViewModel);
        fragmentLibraryBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LibraryRecyclerViewAdapter libraryRecyclerViewAdapter = new LibraryRecyclerViewAdapter(new DiffUtil.ItemCallback<AlbumInfo>() {
            @Override
            public boolean areItemsTheSame(@NonNull AlbumInfo oldItem, @NonNull AlbumInfo newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull AlbumInfo oldItem, @NonNull AlbumInfo newItem) {
                return oldItem.getId() == newItem.getId();
            }
        }, getActivity(), getLayoutInflater(), mainViewModel);
        fragmentLibraryBinding.recyclerView.setAdapter(libraryRecyclerViewAdapter);
        fragmentLibraryBinding.setSongInfo(new SongInfo(0, "音乐（lè），享受白嫖的快感", 0, 0, "", "我再强调一次，第二个字读快乐的乐！"));
        mainViewModel.getAlbumInfos().observe(getActivity(), libraryRecyclerViewAdapter::submitList);
        mainViewModel.getCurrentPlaying().observe(getActivity(), songDetail -> fragmentLibraryBinding.setSongInfo(songDetail.getSongInfo()));
        mainViewModel.getIsPlaying().observe(getActivity(), isPlaying -> fragmentLibraryBinding.play.setImageResource(!isPlaying ? R.mipmap.play_blue : R.mipmap.pause));
        return fragmentLibraryBinding.getRoot().getRootView();
    }
}
