package com.example.expencetracker.interfaces;
import androidx.lifecycle.LiveData;

public interface IVModelList <T>{
    public LiveData<T> getLiveData();
    public int size();
    public T getData();
    public void setData(T data);
    public void updateElement(Object data);
    public void deleteElement(Object data);
    public void addElement(Object data);
    public T getReversedList();
}
