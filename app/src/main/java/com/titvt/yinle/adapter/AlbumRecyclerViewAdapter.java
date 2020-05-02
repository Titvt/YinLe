package com.titvt.yinle.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.titvt.yinle.R;
import com.titvt.yinle.bean.SongInfo;
import com.titvt.yinle.databinding.AlbumRecyclerViewItemBinding;
import com.titvt.yinle.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumRecyclerViewAdapter extends RecyclerView.Adapter<AlbumRecyclerViewAdapter.AlbumRecyclerViewHolder> {
    private MainViewModel mainViewModel;
    private LayoutInflater layoutInflater;
    private List<SongInfo> songInfos;
    private long previousPlaying;

    public AlbumRecyclerViewAdapter(MainViewModel mainViewModel, LayoutInflater layoutInflater, LifecycleOwner lifecycleOwner) {
        this.mainViewModel = mainViewModel;
        this.layoutInflater = layoutInflater;
        songInfos = new ArrayList<>();
        mainViewModel.getAlbumDetail().observe(lifecycleOwner, albumDetail -> {
            songInfos = albumDetail.getSongInfos();
            notifyDataSetChanged();
        });
        mainViewModel.getCurrentPlaying().observe(lifecycleOwner, currentPlaying -> {
            for (int i = 0; i < songInfos.size(); i++)
                if (songInfos.get(i).getId() == currentPlaying.getSongInfo().getId()
                        || songInfos.get(i).getId() == previousPlaying)
                    notifyItemChanged(i);
            previousPlaying = currentPlaying.getSongInfo().getId();
        });
    }

    @NonNull
    @Override
    public AlbumRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumRecyclerViewHolder(DataBindingUtil
                .inflate(layoutInflater, R.layout.album_recycler_view_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return songInfos.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumRecyclerViewHolder holder, int position) {
        holder.albumRecyclerViewItemBinding.setViewModel(mainViewModel);
        holder.albumRecyclerViewItemBinding.setSongInfo(songInfos.get(position));
        holder.albumRecyclerViewItemBinding.setIndex(position + 1);
    }

    static class AlbumRecyclerViewHolder extends RecyclerView.ViewHolder {
        private AlbumRecyclerViewItemBinding albumRecyclerViewItemBinding;

        AlbumRecyclerViewHolder(AlbumRecyclerViewItemBinding albumRecyclerViewItemBinding) {
            super(albumRecyclerViewItemBinding.getRoot().getRootView());
            this.albumRecyclerViewItemBinding = albumRecyclerViewItemBinding;
        }
    }
}
