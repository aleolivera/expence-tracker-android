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
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUpdateTransaction implements IAsync<Integer> {

    private final ExecutorService executor;
    private final Handler handler;
    private final DataTransaction data;
    private final Transaction transaction;
    private final View rootView;
    private final CustomViewModelList model;

    public ExecutorUpdateTransaction(
            @NonNull Context context,
            @NonNull View view,
            @NonNull Transaction transaction,
            @NonNull CustomViewModelList model
    ){
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
        this.data = new DataTransaction(context);
        this.rootView = view;
        this.transaction = transaction;
        this.model = model;
    }

    @Override
    public void runAsync() {
        executor.execute(() -> {
            int rows = task();

            handler.post(()-> post(rows));
        });
    }

    @Override
    public Integer task() {
        return data.update(transaction);
    }

    @Override
    public void post(Integer rows) {
        final String MESSAGE;

        if(rows > 0){
            model.updateElement(transaction);
            MESSAGE = "Done!";
        }
        else
            MESSAGE = "Data base error";

        Snackbar.make(rootView,MESSAGE,Snackbar.LENGTH_SHORT).show();

    }
}
