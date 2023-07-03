package com.example.expencetracker.entities;

public class Category {
    private int id;
    private String name;
    private int idType;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Category(int id, String name, int idType) {
        this.id = id;
        this.name = name;
        this.idType = idType;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public int getIdType() {return idType;}

    @Override
    public String toString() {
        return name;
    }
}
