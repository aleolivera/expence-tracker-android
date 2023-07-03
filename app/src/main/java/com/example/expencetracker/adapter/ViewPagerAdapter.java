package com.example.expencetracker.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.expencetracker.ui.home.HomeSummaryFragment;
import com.example.expencetracker.ui.home.HomeFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (position == 0) ?
                HomeFragment.newInstance() : HomeSummaryFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
