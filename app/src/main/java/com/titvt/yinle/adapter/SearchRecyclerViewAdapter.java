package com.titvt.yinle.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.titvt.yinle.R;
import com.titvt.yinle.bean.SongInfo;
import com.titvt.yinle.databinding.SearchRecyclerViewItemBinding;
import com.titvt.yinle.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchRecyclerViewHolder> {
    private List<SongInfo> searchSongs;
    private LayoutInflater layoutInflater;
    private MainViewModel mainViewModel;

    public SearchRecyclerViewAdapter(LayoutInflater layoutInflater, MainViewModel mainViewModel) {
        searchSongs = new ArrayList<>();
        this.layoutInflater = layoutInflater;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public SearchRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchRecyclerViewHolder(DataBindingUtil
                .inflate(layoutInflater, R.layout.search_recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewHolder holder, int position) {
        holder.searchRecyclerViewItemBinding.setViewModel(mainViewModel);
        holder.searchRecyclerViewItemBinding.setSongInfo(searchSongs.get(position));
        holder.searchRecyclerViewItemBinding.setIndex(position + 1);
    }

    @Override
    public int getItemCount() {
        return searchSongs.size();
    }

    public void setSearchSongs(List<SongInfo> searchSongs) {
        this.searchSongs = searchSongs;
    }

    static class SearchRecyclerViewHolder extends RecyclerView.ViewHolder {
        private SearchRecyclerViewItemBinding searchRecyclerViewItemBinding;

        SearchRecyclerViewHolder(SearchRecyclerViewItemBinding searchRecyclerViewItemBinding) {
            super(searchRecyclerViewItemBinding.getRoot().getRootView());
            this.searchRecyclerViewItemBinding = searchRecyclerViewItemBinding;
        }
    }
}
