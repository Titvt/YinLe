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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.titvt.yinle.R;
import com.titvt.yinle.adapter.SearchRecyclerViewAdapter;
import com.titvt.yinle.databinding.FragmentSearchBinding;
import com.titvt.yinle.main.MainViewModel;

public class SearchFragment extends Fragment {

    public SearchFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSearchBinding fragmentSearchBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_search, container, false);
        ViewModelStoreOwner viewModelStoreOwner = getActivity();
        if (viewModelStoreOwner == null)
            viewModelStoreOwner = this;
        MainViewModel mainViewModel = new ViewModelProvider(viewModelStoreOwner).get(MainViewModel.class);
        fragmentSearchBinding.setViewModel(mainViewModel);
        fragmentSearchBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SearchRecyclerViewAdapter searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(getLayoutInflater(), mainViewModel);
        fragmentSearchBinding.recyclerView.setAdapter(searchRecyclerViewAdapter);
        mainViewModel.getSearchSongs().observe(getActivity(), searchSongs -> {
            searchRecyclerViewAdapter.setSearchSongs(searchSongs);
            searchRecyclerViewAdapter.notifyDataSetChanged();
        });
        return fragmentSearchBinding.getRoot().getRootView();
    }
}
