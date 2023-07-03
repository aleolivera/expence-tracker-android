package com.example.expencetracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expencetracker.entities.Category;
import com.example.expencetracker.entities.Transaction;
import com.example.expencetracker.entities.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataTransaction {
    private final SqliteHelper helper;

    public DataTransaction(Context context) {
        this.helper = new SqliteHelper(context);
    }

    public ArrayList<Transaction> readAllBetweenDates(LocalDate first, LocalDate last){
        try {
            ArrayList<Transaction> data = new ArrayList<>();
            String query =
                    "SELECT t.id, t.description, t.category_id, c.name,t.type_id, ty.name,t.price,t.date " +
                    "FROM transactions t " +
                    "INNER JOIN types ty on ty.id = t.type_id " +
                    "INNER JOIN categories c on c.id = t.category_id " +
                    "WHERE t.date BETWEEN '" + first.toString() +"' AND '" + last.toString() +"' "+
                    "AND t.state = 1";

            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                data.add(cursorToTransaction(cursor));
            }

            cursor.close();
            db.close();
            return data;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> readDistinctYears(){
        try {
            ArrayList<Integer> data = new ArrayList<>();
            String query =
                    "SELECT distinct SUBSTR(date, 1, 4) " +
                    "FROM transactions";

            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                data.add(cursor.getInt(0));
            }

            cursor.close();
            db.close();
            return data;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Transaction> readAllByMonthAndYear(int month, int year){
        try {
            ArrayList<Transaction> data = new ArrayList<>();
            String regex1 = (year + "%");
            String regex2 = (month < 10) ? ("%-0" + month+"-%") : ("%-" + month+"-%") ;
            String query =
                    "SELECT t.id, t.description, t.category_id, c.name,t.type_id, ty.name,t.price,t.date " +
                    "FROM transactions t " +
                    "INNER JOIN types ty on ty.id = t.type_id " +
                    "INNER JOIN categories c on c.id = t.category_id " +
                    "WHERE t.date LIKE '" + regex1 + "' " +
                    "AND t.date LIKE '"+ regex2 + "' " +
                    "AND t.state = 1";

            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                data.add(cursorToTransaction(cursor));
            }

            cursor.close();
            db.close();
            return data;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long create(Transaction transaction){
        long response;

        try {
            ContentValues value = new ContentValues();
            value.put("description",transaction.getDescription());
            value.put("date",transaction.getDate().toString());
            value.put("category_id",transaction.getCategory().getId());
            value.put("type_id",transaction.getType().getId());
            value.put("price",transaction.getPrice());
            value.put("state",true);

            SQLiteDatabase db = helper.getWritableDatabase();
            response = db.insert(SqliteHelper.DB_TABLE_TRANSACTIONS_NAME,null,value);

            db.close();
            return response;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmpty(){
        try{
            boolean empty;
            String query = "SELECT id FROM transactions WHERE id=1";
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);

            empty = !cursor.moveToNext();

            cursor.close();
            db.close();
            return empty;

        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public int update(Transaction transaction){
        int response;

        try {
            String whereClause = "id = ?";
            String[] whereArgs = new String[]{String.valueOf(transaction.getId())};

            SQLiteDatabase db = helper.getWritableDatabase();
            response =
                    db.update(
                            SqliteHelper.DB_TABLE_TRANSACTIONS_NAME,
                            transaction.toContentValues(),
                            whereClause,
                            whereArgs
                    );

            db.close();
            return response;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction cursorToTransaction(Cursor cursor){
        return new Transaction(
                cursor.getInt(0),
                cursor.getString(1),
                new Category(
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                ),
                LocalDate.parse(cursor.getString(7)),
                new Type(cursor.getInt(4),
                        cursor.getString(5)),
                cursor.getDouble(6),
                true
        );
    }
}
