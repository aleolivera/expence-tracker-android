package com.example.expencetracker.interfaces;

public interface IAsync <T> {
    public void runAsync();
    public T task();
    public void post(T response);
}
