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
import android.widget.TextView;

import com.example.expencetracker.R;
import com.example.expencetracker.adapter.CardTransactionSummaryAdapter;
import com.example.expencetracker.async.ExecutorGetSummaryData;
import com.example.expencetracker.dialog.FilterMonthTransactionDialog;
import com.example.expencetracker.dialog.FilterMonthTransactionViewModel;
import com.example.expencetracker.entities.Transaction;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MonthSummaryFragment extends Fragment{
    public static final String NAME = "Summary";
    private LocalDate currentDate;

    private MonthSummaryViewModel model;
    private FilterMonthTransactionViewModel dialogModel;
    private RecyclerView rvMonthSummary;
    private TextView tvdate;

    private View view;

    public MonthSummaryFragment() { }

    public static MonthSummaryFragment newInstance() {
        MonthSummaryFragment fragment = new MonthSummaryFragment();
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
        view = inflater.inflate(R.layout.fragment_summary_month, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startViewModels();
        currentDate = LocalDate.now();

        tvdate = view.findViewById(R.id.tvDateMonthSummary);
        rvMonthSummary = view.findViewById(R.id.rvMonthSummary);

        ExtendedFloatingActionButton efab = view.findViewById(R.id.extFabSummary);
        efab.setOnClickListener(this::openFilterDialog);
    }

    private void startViewModels() {
        model = new ViewModelProvider(requireActivity()).get(MonthSummaryViewModel.class);
        model.getLiveData().observe(getViewLifecycleOwner(), this::updateUI);

        dialogModel = new ViewModelProvider(this).get(FilterMonthTransactionViewModel.class);
        dialogModel.getLiveData().observe(getViewLifecycleOwner(), this::filterTransaction);
    }

    private void openFilterDialog(View view) {
        FilterMonthTransactionDialog dialog = new FilterMonthTransactionDialog(dialogModel);
        dialog.show(getChildFragmentManager(),null);
    }

    private void updateUI(List<Transaction> transactions){
        if(dialogModel.getData() != null) {
            int month = dialogModel.getData().get(dialogModel.MONTH_INDEX);
            int year =  dialogModel.getData().get(dialogModel.YEAR_INDEX);
            currentDate = LocalDate.of(year,month, 1);
        }

        setRecyclerView();

        String date = getMonthName(currentDate.getMonthValue()) + " " + currentDate.getYear();
        tvdate.setText(date);

        if(model.size() < 1)
            Snackbar.make(rvMonthSummary,"No transactions this month",Snackbar.LENGTH_SHORT).show();

    }

    private void setRecyclerView() {
        Context context = requireContext();

        rvMonthSummary.setLayoutManager(new LinearLayoutManager(context));
        CardTransactionSummaryAdapter adapter =
                        new CardTransactionSummaryAdapter(
                                context,model,R.layout.card_transaction,NAME
                        );
        rvMonthSummary.setAdapter(adapter);
    }

    private String getMonthName(int month) {
        switch (month){
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

    public void filterTransaction(ArrayList<Integer> monthYear) {
        ExecutorGetSummaryData task =
                new ExecutorGetSummaryData(
                        requireContext(),
                        view,
                        model,
                        monthYear.get(dialogModel.MONTH_INDEX),
                        monthYear.get(dialogModel.YEAR_INDEX)
                );
        task.runAsync();
    }
}