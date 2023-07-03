package com.example.expencetracker.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expencetracker.R;
import com.example.expencetracker.adapter.ViewPagerAdapter;
import com.example.expencetracker.async.ExecutorGetHomeData;
import com.example.expencetracker.dialog.NewTransactionDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeHostFragment extends Fragment {

    private HomeViewModel model;
    public static final String NAME = "Home";
    public HomeHostFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        return inflater.inflate(R.layout.fragment_home_host, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startViewModel(view);
        startViewPager(view);
        startFab(view);
    }

    private void startFab(View view) {
        FloatingActionButton fab;
        fab = view.findViewById(R.id.fabHome);
        fab.setOnClickListener(v -> onClickFab());
    }

    private void startViewPager(View v){
        ViewPager2 viewPager;
        viewPager = v.findViewById(R.id.vpHome);
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(),getLifecycle()));
    }

    private void onClickFab(){
        NewTransactionDialog dialog = new NewTransactionDialog(NAME);
        dialog.show(getChildFragmentManager(),null);
    }

    private void startViewModel(View v) {
        ExecutorGetHomeData task = new ExecutorGetHomeData(requireContext(),v,model);
        task.runAsync();
    }
}