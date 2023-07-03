package com.example.expencetracker.ui.summary;

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
import com.example.expencetracker.adapter.ViewPagerSummaryAdapter;
import com.example.expencetracker.async.ExecutorGetSummaryData;

import java.time.LocalDate;

public class SummaryHostFragment extends Fragment {
    private MonthSummaryViewModel model;

    public SummaryHostFragment() { }

    public static SummaryHostFragment newInstance() {
        SummaryHostFragment fragment = new SummaryHostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        model = new ViewModelProvider(requireActivity()).get(MonthSummaryViewModel.class);
        return inflater.inflate(R.layout.fragment_summary_host, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startViewModel(view);
        startViewPager(view);
    }

    private void startViewPager(View view) {
        ViewPager2 vpSummary = view.findViewById(R.id.vpSummary);
        ViewPagerSummaryAdapter adapter =
                new ViewPagerSummaryAdapter(
                        getChildFragmentManager(),getLifecycle()
                );
        vpSummary.setAdapter(adapter);
    }

    private void startViewModel(View view) {
        ExecutorGetSummaryData task =
                new ExecutorGetSummaryData(
                        requireContext(),
                        view,
                        model,
                        LocalDate.now().getMonthValue(),
                        LocalDate.now().getYear()
                );
        task.runAsync();
    }
}