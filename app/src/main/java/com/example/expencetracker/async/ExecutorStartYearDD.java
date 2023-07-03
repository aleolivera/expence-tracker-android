package com.example.expencetracker.async;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;

import com.example.expencetracker.data.DataTransaction;
import com.example.expencetracker.interfaces.IAsync;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorStartYearDD implements IAsync<Integer> {
    private final Context context;
    private final ExecutorService executor;
    private final Handler handler;
    private final View rootView;
    private DataTransaction data;
    private ArrayList<Integer> years;
    private AutoCompleteTextView ddYears;


    public ExecutorStartYearDD(
            @NonNull Context context,
            @NonNull View view ,
            @NonNull AutoCompleteTextView ddYears){
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
        this.data = new DataTransaction(context);
        this.rootView = view;
        this.years = new ArrayList<>();
        this.ddYears = ddYears;
    }
    @Override
    public void runAsync() {
        executor.execute(()-> {
            Integer response = task();
            handler.post(() -> post(response));
        });
    }

    @Override
    public Integer task() {
        years = data.readDistinctYears();
        return (years.size() > 0)? 1 : -1;
    }

    @Override
    public void post(Integer response) {
        final int ERROR = -1;
        if(response == ERROR){
            Snackbar.make(rootView,"No transactions to filter", Snackbar.LENGTH_SHORT).show();
            ddYears.setVisibility(View.GONE);
        }
        else{
            ArrayAdapter<Integer>adapter =
                    new ArrayAdapter<>(
                            context,
                            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                            years
                    );
            ddYears.setAdapter(adapter);
            String selected = adapter.getItem(0).toString();
            ddYears.setText(selected,false);
            ddYears.setVisibility(View.VISIBLE);
        }
    }
}
