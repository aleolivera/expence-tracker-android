package com.example.expencetracker.ui.summary;

import androidx.lifecycle.LiveData;

import com.example.expencetracker.entities.CustomViewModelList;
import com.example.expencetracker.entities.Transaction;

import java.util.ArrayList;
import java.util.List;

public class YearSummaryViewModel extends CustomViewModelList<List< List<Transaction> >> {

    public YearSummaryViewModel(){ super(); }

    @Override
    public LiveData<List<List<Transaction>>> getLiveData() {
        return mData;
    }

    @Override
    public int size() {
        return (mData.getValue() != null) ? mData.getValue().size() : 0;
    }

    @Override
    public List<List<Transaction>> getData() {
        return mData.getValue();
    }

    @Override
    public void setData(List<List<Transaction>> data) {
        this.mData.setValue(data);
    }

    @Override
    public void updateElement(Object data) { }

    @Override
    public void deleteElement(Object data) { }

    @Override
    public void addElement(Object data) { }

    @Override
    public List<List<Transaction>> getReversedList() {
        return mData.getValue();
    }

    public List<List<Transaction>> onlyMonthWithTransactions(){
        List<List<Transaction>> lists = (mData.getValue() != null)? mData.getValue() : new ArrayList<>();
        ArrayList<List<Transaction>> filteredLists = new ArrayList<>();

        for (List<Transaction> item : lists) {
            if (item.size() > 0) {
                filteredLists.add(item);
            }
        }

        return filteredLists;
    }

}
