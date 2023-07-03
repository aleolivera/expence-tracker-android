package com.example.expencetracker.async;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.expencetracker.entities.CustomViewModelList;
import com.example.expencetracker.interfaces.IAsync;
import com.example.expencetracker.data.DataTransaction;
import com.example.expencetracker.entities.Transaction;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorCreateTransaction implements IAsync<Long> {
    private final ExecutorService executor;
    private final Handler handler;

    private final CustomViewModelList model;
    private final Transaction transaction;
    private final DataTransaction data;
    @SuppressLint("StaticFieldLeak")
    private final View rootView;

    public ExecutorCreateTransaction(@NonNull Context context, @NonNull View view , CustomViewModelList model, Transaction transaction){
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
        this.model = model;
        this.transaction = transaction;
        this.data = new DataTransaction(context);
        this.rootView = view;
    }

    @Override
    public void runAsync() {
        executor.execute(() -> {
            long id = task();

            handler.post(() ->
                post(id)
            );
        });
    }

    @Override
    public Long task() {
        return data.create(transaction);
    }

    @Override
    public void post(Long id) {
        String message;

        if(id > 0){
            transaction.setId(id);
            model.addElement(transaction);
            message = "Saved";
        }
        else
            message = "Data base error";

        Snackbar.make(rootView,message,Snackbar.LENGTH_SHORT).show();
    }
}
