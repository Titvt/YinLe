package com.titvt.yinle.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.titvt.yinle.R;
import com.titvt.yinle.bean.AlbumInfo;
import com.titvt.yinle.databinding.HomeRecyclerViewBannerBinding;
import com.titvt.yinle.util.edilg.Edilg;
import com.titvt.yinle.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class BannerRecyclerViewAdapter extends RecyclerView.Adapter<BannerRecyclerViewAdapter.BannerRecyclerViewHolder> {
    private Context context;
    private MainViewModel mainViewModel;
    private LayoutInflater layoutInflater;
    private List<AlbumInfo> albumInfos;

    BannerRecyclerViewAdapter(Context context, MainViewModel mainViewModel, LayoutInflater layoutInflater, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.mainViewModel = mainViewModel;
        this.layoutInflater = layoutInflater;
        albumInfos = new ArrayList<>();
        mainViewModel.getBanners().observe(lifecycleOwner, banners -> {
            albumInfos = banners;
            notifyDataSetChanged();
        });
    }

    @NonNull
    @Override
    public BannerRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BannerRecyclerViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.home_recycler_view_banner, parent, false));
    }

    @Override
    public int getItemCount() {
        return albumInfos.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BannerRecyclerViewHolder holder, int position) {
        holder.homeRecyclerViewBannerBinding.setViewModel(mainViewModel);
        holder.homeRecyclerViewBannerBinding.setAlbumInfo(albumInfos.get(position));
        Edilg.with(context).load(albumInfos.get(position).getCoverImgUrl()).cornerRadius(60).into(holder.homeRecyclerViewBannerBinding.cover);
    }

    static class BannerRecyclerViewHolder extends RecyclerView.ViewHolder {
        private HomeRecyclerViewBannerBinding homeRecyclerViewBannerBinding;

        BannerRecyclerViewHolder(HomeRecyclerViewBannerBinding homeRecyclerViewBannerBinding) {
            super(homeRecyclerViewBannerBinding.getRoot().getRootView());
            this.homeRecyclerViewBannerBinding = homeRecyclerViewBannerBinding;
        }
    }
}

