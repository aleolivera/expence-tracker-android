package com.example.expencetracker.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expencetracker.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class DataCategory {
    private final SqliteHelper helper;
    private final List<Category> categories;

    public DataCategory(Context context) {
        this.helper = new SqliteHelper(context);
        this.categories = new ArrayList<>();
    }

    public List<Category> readAll(){
        String query = "SELECT id, name, type_id FROM " + SqliteHelper.DB_TABLE_CATEGORIES_NAME;

        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                categories.add(cursorToCategories(cursor));
            }
            cursor.close();
            db.close();
            return categories;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Category> readAllByTypeOfTransaction(int idType){
        String query =
                "SELECT id, name, type_id" +
                " FROM " + SqliteHelper.DB_TABLE_CATEGORIES_NAME +
                " WHERE type_id = " + idType;

        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                categories.add(cursorToCategories(cursor));
            }
            cursor.close();
            db.close();
            return categories;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Category cursorToCategories(Cursor cursor) {
        return new Category(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2)
        );
    }

}
