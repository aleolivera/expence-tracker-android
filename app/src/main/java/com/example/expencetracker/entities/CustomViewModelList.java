package com.example.expencetracker.entities;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expencetracker.interfaces.IVModelList;

public abstract class CustomViewModelList<T> extends ViewModel implements IVModelList<T> {
    protected final MutableLiveData<T> mData;

    protected CustomViewModelList() {
        this.mData = new MutableLiveData<>();
    }
}
