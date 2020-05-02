package com.titvt.yinle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.titvt.yinle.R;
import com.titvt.yinle.bean.AlbumInfo;
import com.titvt.yinle.databinding.HomeRecyclerViewItemBinding;
import com.titvt.yinle.main.MainViewModel;
import com.titvt.yinle.util.edilg.Edilg;

public class HomeRecyclerViewAdapter extends PagedListAdapter<AlbumInfo, HomeRecyclerViewAdapter.HomeRecyclerViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private MainViewModel mainViewModel;

    public HomeRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<AlbumInfo> diffCallback,
                                   Context context, LayoutInflater layoutInflater, MainViewModel mainViewModel) {
        super(diffCallback);
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public HomeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeRecyclerViewHolder(DataBindingUtil
                .inflate(layoutInflater, R.layout.home_recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewHolder holder, int position) {
        AlbumInfo albumInfo = getItem(position);
        holder.homeRecyclerViewItemBinding.setViewModel(mainViewModel);
        holder.homeRecyclerViewItemBinding.setAlbumInfo(albumInfo);
        if (albumInfo != null) {
            holder.homeRecyclerViewItemBinding.cover.setTag(albumInfo.getCoverImgUrl());
            Edilg.with(context).load(albumInfo.getCoverImgUrl()).into(holder.homeRecyclerViewItemBinding.cover);
        }
    }

    static class HomeRecyclerViewHolder extends RecyclerView.ViewHolder {
        private HomeRecyclerViewItemBinding homeRecyclerViewItemBinding;

        HomeRecyclerViewHolder(HomeRecyclerViewItemBinding homeRecyclerViewItemBinding) {
            super(homeRecyclerViewItemBinding.getRoot().getRootView());
            this.homeRecyclerViewItemBinding = homeRecyclerViewItemBinding;
        }
    }
}
