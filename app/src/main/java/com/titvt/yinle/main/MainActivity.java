package com.titvt.yinle.main;

import android.content.Intent;
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
import com.titvt.yinle.fragment.AlbumFragment;
import com.titvt.yinle.fragment.HomeFragment;
import com.titvt.yinle.fragment.LibraryFragment;
import com.titvt.yinle.fragment.PlayFragment;
import com.titvt.yinle.fragment.ProfileFragment;
import com.titvt.yinle.fragment.SearchFragment;
import com.titvt.yinle.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_LIBRARY = 1 << 1;
    private static final int FRAGMENT_PROFILE = 1 << 2;
    private static final int FRAGMENT_ALBUM = 1 << 3;
    private static final int FRAGMENT_PLAY = 1 << 4;
    private static final int FRAGMENT_SEARCH = 1 << 5;
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
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, LoginActivity.class));
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        activityMainBinding.setViewModel(mainViewModel);
        mainViewModel.getPageStatus().observe(this, pageStatus -> {
            switch (pageStatus) {
                case 1:
                    switchToAlbum();
                    break;
                case 2:
                    switchToPlay();
                    break;
                case 3:
                    switchToSearch();
                    break;
                case -1:
                    if (currentFragment == albumFragment || currentFragment == playFragment
                            || currentFragment == searchFragment) {
                        fragmentManager.beginTransaction().hide(currentFragment).show(previousFragment).commit();
                        currentFragment = previousFragment;
                    }
            }
        });
        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        libraryFragment = new LibraryFragment();
        profileFragment = new ProfileFragment();
        albumFragment = new AlbumFragment();
        playFragment = new PlayFragment();
        searchFragment = new SearchFragment();
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
                if (currentFragment == homeFragment || currentFragment == libraryFragment
                        || currentFragment == profileFragment)
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

    public void switchToSearch() {
        switchFragment(searchFragment, FRAGMENT_SEARCH);
    }

    @Override
    public void onBackPressed() {
        if (currentFragment == albumFragment || currentFragment == playFragment || currentFragment == searchFragment) {
            fragmentManager.beginTransaction().hide(currentFragment).show(previousFragment).commit();
            currentFragment = previousFragment;
        } else
            moveTaskToBack(true);
    }
}
