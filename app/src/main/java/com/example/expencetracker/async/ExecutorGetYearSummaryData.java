package com.example.expencetracker.async;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.expencetracker.data.DataTransaction;
import com.example.expencetracker.entities.Transaction;
import com.example.expencetracker.interfaces.IAsync;
import com.example.expencetracker.ui.summary.YearSummaryViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorGetYearSummaryData implements IAsync<Integer> {
    private final ExecutorService executor;
    private final Handler handler;
    private final View rootView;
    private final YearSummaryViewModel model;
    private final List<List<Transaction>> listOfLists;
    private final DataTransaction data;
    private final int year;

    public ExecutorGetYearSummaryData(@NonNull Context context, @NonNull View view, @NonNull YearSummaryViewModel model, int year){
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
        this.rootView = view;
        this.model = model;
        this.listOfLists = new ArrayList<>();
        this.data = new DataTransaction(context);
        this.year = year;
    }

    @Override
    public void runAsync() {
        executor.execute(
                ()->{
                    int response = task();
                    handler.post(
                            ()->post(response)
                    );
                }
        );
    }

    @Override
    public Integer task() {
        for(int month = 1 ; month < 13 ; month++){
            List<Transaction> list = data.readAllByMonthAndYear(month,year);
            listOfLists.add(list);
        }
        return listOfLists.size();
    }

    @Override
    public void post(Integer listSize) {
        if(listSize > 0)
            model.setData(listOfLists);
        else{
            model.setData(new ArrayList<>());
            Snackbar.make(rootView,"No data found",Snackbar.LENGTH_SHORT).show();
        }
    }
}
