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

import com.titvt.yinle.R;
import com.titvt.yinle.databinding.FragmentProfileBinding;
import com.titvt.yinle.main.MainViewModel;
import com.titvt.yinle.util.edilg.Edilg;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfileBinding fragmentProfileBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_profile, container, false);
        ViewModelStoreOwner viewModelStoreOwner = getActivity();
        if (viewModelStoreOwner == null)
            viewModelStoreOwner = this;
        MainViewModel mainViewModel = new ViewModelProvider(viewModelStoreOwner).get(MainViewModel.class);
        mainViewModel.getUserInfo().observe(getActivity(), userInfo -> {
            fragmentProfileBinding.setUserInfo(userInfo);
            Edilg.with(getActivity()).load(userInfo.getBackgroundUrl()).into(fragmentProfileBinding.background);
        });
        return fragmentProfileBinding.getRoot().getRootView();
    }
}
