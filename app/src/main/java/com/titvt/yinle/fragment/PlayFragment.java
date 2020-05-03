package com.titvt.yinle.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.titvt.yinle.R;
import com.titvt.yinle.databinding.FragmentPlayBinding;
import com.titvt.yinle.main.MainViewModel;
import com.titvt.yinle.util.edilg.Edilg;

public class PlayFragment extends Fragment {

    public PlayFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPlayBinding fragmentPlayBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_play, container, false);
        ViewModelStoreOwner viewModelStoreOwner = getActivity();
        if (viewModelStoreOwner == null)
            viewModelStoreOwner = this;
        MainViewModel mainViewModel = new ViewModelProvider(viewModelStoreOwner).get(MainViewModel.class);
        fragmentPlayBinding.setViewModel(mainViewModel);
        mainViewModel.getIsPlaying().observe(getActivity(), isPlaying ->
                fragmentPlayBinding.play.setImageResource(!isPlaying ? R.mipmap.play_blue : R.mipmap.pause));
        mainViewModel.getIsShaffle().observe(getActivity(), isShaffle -> {
            fragmentPlayBinding.repeat.setImageResource(isShaffle ? R.mipmap.repeat : R.mipmap.repeat_blue);
            fragmentPlayBinding.shaffle.setImageResource(isShaffle ? R.mipmap.shaffle_blue : R.mipmap.shaffle);
        });
        mainViewModel.getMonitor().observe(getActivity(), v -> {
            fragmentPlayBinding.timeLeft.setText(mainViewModel.getCurrentTime());
            fragmentPlayBinding.timeRight.setText(mainViewModel.getRemainTime());
            fragmentPlayBinding.seekBar.setProgress(mainViewModel.getProgress());
            String[] lyrics = mainViewModel.getLyrics();
            fragmentPlayBinding.lyricCurrent.setText(lyrics[0]);
            fragmentPlayBinding.lyricNext.setText(lyrics[1]);
        });
        mainViewModel.getCurrentPlaying().observe(getActivity(), songDetail -> {
            fragmentPlayBinding.setSongInfo(songDetail.getSongInfo());
            fragmentPlayBinding.seekBar.setMax((int) songDetail.getSongInfo().getDt());
            fragmentPlayBinding.seekBar.setProgress(0);
            fragmentPlayBinding.cover.setTag(songDetail.getSongInfo().getPicUrl());
            Edilg.with(getActivity()).load(songDetail.getSongInfo().getPicUrl())
                    .cornerRadius(60).into(fragmentPlayBinding.cover);
        });
        fragmentPlayBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mainViewModel.setProgress(progress);
                    fragmentPlayBinding.timeLeft.setText(mainViewModel.getCurrentTime());
                    fragmentPlayBinding.timeRight.setText(mainViewModel.getRemainTime());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return fragmentPlayBinding.getRoot().getRootView();
    }
}
