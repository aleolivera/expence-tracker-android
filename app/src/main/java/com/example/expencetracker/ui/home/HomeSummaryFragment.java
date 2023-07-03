package com.example.expencetracker.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expencetracker.R;
import com.example.expencetracker.adapter.CardTransactionSummaryAdapter;
import com.example.expencetracker.entities.Transaction;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.List;

public class HomeSummaryFragment extends Fragment {
    private HomeViewModel model;
    private RecyclerView recyclerView;
    public static final String NAME = "Home";

    public HomeSummaryFragment() { }

    public static HomeSummaryFragment newInstance() {
        HomeSummaryFragment fragment = new HomeSummaryFragment();
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
        return inflater.inflate(R.layout.fragment_home_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        model.getLiveData().observe(getViewLifecycleOwner(), this::setRecyclerView);

        recyclerView = view.findViewById(R.id.rvHomeSummary);
        TextView tvDate = view.findViewById(R.id.tvDateHomeSummary);

        String date = getMonthName() + " " + LocalDate.now().getYear();
        tvDate.setText(date);

        if(model.size() < 1)
            Snackbar.make(recyclerView,"No transactions this month",Snackbar.LENGTH_SHORT).show();
    }

    private void setRecyclerView(List<Transaction> transactions) {
        if(transactions.size() != 0){
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(
                    new CardTransactionSummaryAdapter(
                            requireContext(),
                            model,
                            R.layout.card_transaction,
                            NAME
                    )
            );
        }
    }

    private String getMonthName() {
        switch (LocalDate.now().getMonthValue()){
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            default: return "December";
        }
    }
}