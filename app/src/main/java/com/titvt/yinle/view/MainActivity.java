package com.titvt.yinle.view;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.titvt.yinle.R;
import com.titvt.yinle.databinding.ActivityMainBinding;
import com.titvt.yinle.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private FragmentManager fragmentManager;
    private int fragmentFlag;
    private Fragment currentFragment;
    private Fragment previousFragment;
    private HomeFragment homeFragment;
    private LibraryFragment libraryFragment;
    private ProfileFragment profileFragment;
    private AlbumFragment albumFragment;
    private PlayFragment playFragment;

    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_LIBRARY = 1 << 1;
    private static final int FRAGMENT_PROFILE = 1 << 2;
    private static final int FRAGMENT_ALBUM = 1 << 3;
    private static final int FRAGMENT_PLAY = 1 << 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        activityMainBinding.setViewModel(mainViewModel);
        mainViewModel.getPageStatus().observe(this, pageStatus -> {
            switch (pageStatus) {
                case -1:
                    if (currentFragment == albumFragment || currentFragment == playFragment) {
                        fragmentManager.beginTransaction().hide(currentFragment).show(previousFragment).commit();
                        currentFragment = previousFragment;
                    }
                    break;
                case 1:
                    switchToAlbum();
                    break;
                case 2:
                    switchToPlay();
            }
        });
        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        libraryFragment = new LibraryFragment();
        profileFragment = new ProfileFragment();
        albumFragment = new AlbumFragment();
        playFragment = new PlayFragment();
        activityMainBinding.home.setOnClickListener(v -> switchToHome());
        activityMainBinding.library.setOnClickListener(v -> switchToLibrary());
        activityMainBinding.profile.setOnClickListener(v -> switchToProfile());
        switchToHome();
    }

    void switchFragment(Fragment fragment, int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (currentFragment != fragment) {
            if (currentFragment != null) {
                fragmentTransaction = fragmentTransaction.hide(currentFragment);
                if (currentFragment == homeFragment || currentFragment == libraryFragment || currentFragment == profileFragment)
                    previousFragment = currentFragment;
            }
            if ((fragmentFlag & index) != 0)
                fragmentTransaction = fragmentTransaction.show(fragment);
            else {
                fragmentTransaction = fragmentTransaction.add(R.id.fragment, fragment);
                fragmentFlag |= index;
            }
            currentFragment = fragment;
        }
        fragmentTransaction.commit();
    }

    public void switchToHome() {
        switchFragment(homeFragment, FRAGMENT_HOME);
        ((ImageView) activityMainBinding.home.getChildAt(0)).setImageResource(R.mipmap.flame_blue);
        ((ImageView) activityMainBinding.library.getChildAt(0)).setImageResource(R.mipmap.library);
        ((ImageView) activityMainBinding.profile.getChildAt(0)).setImageResource(R.mipmap.account);
    }

    public void switchToLibrary() {
        switchFragment(libraryFragment, FRAGMENT_LIBRARY);
        ((ImageView) activityMainBinding.home.getChildAt(0)).setImageResource(R.mipmap.flame);
        ((ImageView) activityMainBinding.library.getChildAt(0)).setImageResource(R.mipmap.library_blue);
        ((ImageView) activityMainBinding.profile.getChildAt(0)).setImageResource(R.mipmap.account);
    }

    public void switchToProfile() {
        switchFragment(profileFragment, FRAGMENT_PROFILE);
        ((ImageView) activityMainBinding.home.getChildAt(0)).setImageResource(R.mipmap.flame);
        ((ImageView) activityMainBinding.library.getChildAt(0)).setImageResource(R.mipmap.library);
        ((ImageView) activityMainBinding.profile.getChildAt(0)).setImageResource(R.mipmap.account_blue);
    }

    public void switchToAlbum() {
        switchFragment(albumFragment, FRAGMENT_ALBUM);
    }

    public void switchToPlay() {
        switchFragment(playFragment, FRAGMENT_PLAY);
    }

    @Override
    public void onBackPressed() {
        if (currentFragment == albumFragment || currentFragment == playFragment) {
            fragmentManager.beginTransaction().hide(currentFragment).show(previousFragment).commit();
            currentFragment = previousFragment;
        } else
            moveTaskToBack(true);
    }
}
