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
import com.titvt.yinle.databinding.LibraryRecyclerViewItemBinding;
import com.titvt.yinle.main.MainViewModel;
import com.titvt.yinle.util.edilg.Edilg;

public class LibraryRecyclerViewAdapter extends
        PagedListAdapter<AlbumInfo, LibraryRecyclerViewAdapter.LibraryRecyclerViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private MainViewModel mainViewModel;

    public LibraryRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<AlbumInfo> diffCallback,
                                      Context context, LayoutInflater layoutInflater, MainViewModel mainViewModel) {
        super(diffCallback);
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public LibraryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LibraryRecyclerViewHolder(DataBindingUtil
                .inflate(layoutInflater, R.layout.library_recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryRecyclerViewHolder holder, int position) {
        AlbumInfo albumInfo = getItem(position);
        holder.libraryRecyclerViewItemBinding.setViewModel(mainViewModel);
        holder.libraryRecyclerViewItemBinding.setAlbumInfo(albumInfo);
        if (albumInfo != null) {
            holder.libraryRecyclerViewItemBinding.cover.setTag(albumInfo.getCoverImgUrl());
            Edilg.with(context).load(albumInfo.getCoverImgUrl())
                    .cornerRadius(30).into(holder.libraryRecyclerViewItemBinding.cover);
        }
    }

    static class LibraryRecyclerViewHolder extends RecyclerView.ViewHolder {
        private LibraryRecyclerViewItemBinding libraryRecyclerViewItemBinding;

        LibraryRecyclerViewHolder(LibraryRecyclerViewItemBinding libraryRecyclerViewItemBinding) {
            super(libraryRecyclerViewItemBinding.getRoot().getRootView());
            this.libraryRecyclerViewItemBinding = libraryRecyclerViewItemBinding;
        }
    }
}
