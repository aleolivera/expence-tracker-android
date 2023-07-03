package com.example.expencetracker.entities;

public class Type {
    private int id;
    private String name;

    public Type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}
