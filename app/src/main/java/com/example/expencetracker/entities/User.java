package com.example.expencetracker.entities;

import java.util.List;

public class User {
    private List<Transaction> transactions;

    public User(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {return transactions;}
}
