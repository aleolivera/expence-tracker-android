package com.example.expencetracker.ui.summary;

import androidx.lifecycle.LiveData;

import com.example.expencetracker.entities.CustomViewModelList;
import com.example.expencetracker.entities.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MonthSummaryViewModel extends CustomViewModelList<List<Transaction>> {

    public MonthSummaryViewModel(){ super(); }

    @Override
    public LiveData<List<Transaction>> getLiveData() {
        return mData;
    }

    @Override
    public int size() {
        return (mData.getValue() != null) ? mData.getValue().size() : 0;
    }

    @Override
    public List<Transaction> getData() {
        return mData.getValue();
    }

    @Override
    public void setData(List<Transaction> data) {
        mData.setValue(data);
    }

    @Override
    public void updateElement(Object data) {
        List<Transaction> list = mData.getValue();
        Transaction transaction = (Transaction) data;

        if(transaction != null && list != null) {

            for(int i = 0 ; i < list.size() ; i++){

                if(list.get(i).getId() == transaction.getId()){
                    if(transaction.isState())
                        list.set(i,transaction);
                    else
                        list.remove(i);
                    break;
                }

            }
            mData.setValue(list);
        }
    }

    @Override
    public void deleteElement(Object data) {
        List<Transaction> list = mData.getValue();
        Transaction transaction = (Transaction) data;

        if(transaction != null && list != null) {
            int i;

            for(i = 0 ; i < list.size() ; i++){
                if(list.get(i).getId() == transaction.getId())
                    break;
            }

            list.remove(i);
            mData.setValue(list);
        }
    }

    @Override
    public void addElement(Object data) {
        List<Transaction> list = mData.getValue();
        Transaction transaction = (Transaction) data;

        if(transaction != null && list != null){
            list.add(transaction);
            mData.setValue(list);
        }
    }

    @Override
    public List<Transaction> getReversedList() {
        List<Transaction> data = (mData.getValue() != null) ? mData.getValue() : new ArrayList<>();
        List<Transaction> reversedList = new ArrayList<>();

        int dataLastIndex = mData.getValue().size() - 1;

        for(int i = dataLastIndex; i >= 0 ; i--){
            reversedList.add(data.get(i));
        }

        return reversedList;
    }
}
