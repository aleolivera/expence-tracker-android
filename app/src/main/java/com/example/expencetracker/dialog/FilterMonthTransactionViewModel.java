package com.example.expencetracker.dialog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expencetracker.entities.CustomViewModelList;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FilterMonthTransactionViewModel extends CustomViewModelList<ArrayList<Integer>> {
    private final MutableLiveData<ArrayList<Integer>> mData;
    public final int MONTH_INDEX = 0;
    public final int YEAR_INDEX = 1;

    public FilterMonthTransactionViewModel() {
        this.mData = new MutableLiveData<>();
    }

    @Override
    public LiveData<ArrayList<Integer>> getLiveData() {
        return mData;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public ArrayList<Integer> getData() {
        return mData.getValue();
    }

    @Override
    public void setData(ArrayList<Integer> data) {
        this.mData.setValue(data);
    }

    @Override
    public void updateElement(Object data) { }

    @Override
    public void deleteElement(Object data) { }

    @Override
    public void addElement(Object data) { }

    @Override
    public ArrayList<Integer> getReversedList() {
        return mData.getValue();
    }
}