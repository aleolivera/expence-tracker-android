package com.example.expencetracker.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expencetracker.entities.Type;

import java.util.ArrayList;
import java.util.List;

public class DataType {
    private final SqliteHelper helper;
    private final List<Type> types;

    public DataType(Context context) {
        this.helper = new SqliteHelper(context);
        this.types = new ArrayList<>();
    }

    public List<Type> readAll(){
        String query = "SELECT * FROM types";

        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                types.add(cursorToType(cursor));
            }
            cursor.close();
            db.close();
            return types;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Type cursorToType(Cursor cursor) {
        return new Type(
                cursor.getInt(0),
                cursor.getString(1)
        );
    }
}