package com.example.expencetracker.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.expencetracker.ui.summary.MonthSummaryFragment;
import com.example.expencetracker.ui.summary.YearSummaryFragment;

public class ViewPagerSummaryAdapter extends FragmentStateAdapter {

    public ViewPagerSummaryAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0)
            return MonthSummaryFragment.newInstance();
        else
            return YearSummaryFragment.newInstance();
    }

    @Override
    public int getItemCount() { return 2; }

}
