package com.example.expencetracker.async;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.expencetracker.data.DataTransaction;
import com.example.expencetracker.entities.CustomViewModelList;
import com.example.expencetracker.entities.Transaction;
import com.example.expencetracker.interfaces.IAsync;
import com.example.expencetracker.ui.home.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorGetHomeData implements IAsync<Integer> {
    private final ExecutorService executor;
    private final Handler handler;
    private final View rootView;
    private final CustomViewModelList model;
    private List<Transaction> transactions;
    private final DataTransaction data;

    public ExecutorGetHomeData(@NonNull Context context, @NonNull View view, @NonNull CustomViewModelList model){
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
        this.rootView = view;
        this.model = model;
        this.data = new DataTransaction(context);
    }

    @Override
    public void runAsync() {
        executor.execute(
            ()->{
                int response = task();

                handler.post(()->
                   post(response)
                );
            }
        );
    }

    @Override
    public Integer task() {
        transactions  =
                data.readAllByMonthAndYear(
                        LocalDate.now().getMonthValue(), LocalDate.now().getYear()
                );
        return (transactions != null)? 1 : -1;
    }

    @Override
    public void post(Integer response) {
        final int OK = 1;
        if(response == OK)
            model.setData(transactions);
        else{
            model.setData(new ArrayList<>());
            Snackbar.make(rootView,"Error Data Base",Snackbar.LENGTH_SHORT).show();
        }

    }
}
