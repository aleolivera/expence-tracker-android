package com.example.expencetracker.ui.summary;

import android.content.Context;
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
import android.widget.AutoCompleteTextView;

import com.example.expencetracker.R;
import com.example.expencetracker.adapter.CardMonthSummaryAdapter;
import com.example.expencetracker.async.ExecutorGetYearSummaryData;
import com.example.expencetracker.async.ExecutorStartYearDD;
import com.example.expencetracker.entities.Transaction;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.List;

public class YearSummaryFragment extends Fragment {
    private RecyclerView recyclerView;
    private AutoCompleteTextView ddYears;
    private YearSummaryViewModel model;

    public YearSummaryFragment() { }

    public static YearSummaryFragment newInstance() {
        YearSummaryFragment fragment = new YearSummaryFragment();

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
        return inflater.inflate(R.layout.fragment_summary_year, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startViewModel(view, LocalDate.now().getYear());
        recyclerView = view.findViewById(R.id.rvYearSummary);
        ddYears = view.findViewById(R.id.ddYearSummaryYear);

        ddYears.setOnItemClickListener(
                (parent, v, position, id) -> {
                    int year = Integer.parseInt(ddYears.getText().toString());
                    startViewModel(v,year);
        });
        setDropDown();
    }

    private void setDropDown() {
        ExecutorStartYearDD task = new ExecutorStartYearDD(requireContext(), recyclerView, ddYears);
        task.runAsync();
    }

    private void setRecyclerView(List<List<Transaction>> lists) {
        if(lists != null && !areListsEmpty(lists)){
            Context context = requireContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(
                    new CardMonthSummaryAdapter(
                            context,
                            R.layout.card_month_summary,
                            model.onlyMonthWithTransactions()
                    )
            );
        }
        else
            Snackbar.make(recyclerView,"No transactions this year",Snackbar.LENGTH_SHORT).show();
    }

    private boolean areListsEmpty(List<List<Transaction>> lists) {
        for (List<Transaction> item : lists) {
            if(item.size() > 0)
                return false;
        }
        return true;
    }

    private void startViewModel(View view, int year){
        model = new ViewModelProvider(this).get(YearSummaryViewModel.class);
        model.getLiveData().observe(getViewLifecycleOwner(), this::setRecyclerView);

        ExecutorGetYearSummaryData task = new ExecutorGetYearSummaryData(requireContext(),view,model,year);
        task.runAsync();
    }

}