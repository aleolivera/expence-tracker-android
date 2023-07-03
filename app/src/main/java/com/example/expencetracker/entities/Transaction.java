package com.example.expencetracker.entities;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class Transaction implements Parcelable {
    private long id;
    private final String description;
    private Category category;
    private LocalDate date;
    private Type type;
    private final double price;
    private boolean state;

    public Transaction(long id, String description, Category category, LocalDate date, Type type, double price, boolean state) {
        this.id = id;
        this.description = description;
        this.category = (category != null)? category : new Category(0,"",0);
        this.date = date;
        this.type = (type != null)? type : new Type(0,"");
        this.price = price;
        this.state = state;
    }

    protected Transaction(Parcel in) {
        id = in.readLong();
        description = in.readString();
        price = in.readDouble();
        state = in.readByte() != 0;
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeByte((byte) (state ? 1 : 0));
    }

    public static Transaction newInstance(){
        return new Transaction(
                0,"",new Category(0,""),
                LocalDate.now(),new Type(0,""),0,false
        );
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDescription() { return description; }

    public Category getCategory() { return category; }

    public LocalDate getDate() { return date; }

    public Type getType() { return type; }

    public double getPrice() { return price; }

    public boolean isState() { return state; }
    public void setState(boolean state) { this.state = state; }

    public ContentValues toContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", this.id);
        contentValues.put("description", this.description);
        contentValues.put("category_id", this.category.getId());
        contentValues.put("type_id", this.type.getId());
        contentValues.put("price", this.price);
        contentValues.put("date", this.date.toString());
        contentValues.put("state", this.state);

        return contentValues;
    }
}
