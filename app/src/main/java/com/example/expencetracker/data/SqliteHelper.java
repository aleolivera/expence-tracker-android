package com.example.expencetracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "expences_tracker_db";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE_TRANSACTIONS_NAME = "transactions";
    public static final String DB_TABLE_TYPES_NAME = "types";
    public static final String DB_TABLE_CATEGORIES_NAME = "categories";

    public SqliteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableTypes =
                "CREATE TABLE "+DB_TABLE_TYPES_NAME+" ("+
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                        "name TEXT NOT NULL UNIQUE" +
                        ");";
        String createTableCategories =
                "CREATE TABLE "+DB_TABLE_CATEGORIES_NAME+" ("+
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                        "type_id INTEGER NOT NULL," +
                        "name TEXT NOT NULL UNIQUE," +
                        "FOREIGN KEY (type_id) "+
                        "REFERENCES "+DB_TABLE_TYPES_NAME+" (id)" +
                        ");";
        String createTableTransactions =
                "CREATE TABLE "+DB_TABLE_TRANSACTIONS_NAME+" ("+
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                        "description TEXT NOT NULL," +
                        "category_id INTEGER NOT NULL," +
                        "type_id INTEGER NOT NULL," +
                        "price REAL NOT NULL," +
                        "date TEXT NOT NULL," +
                        "state INTEGER NOT NULL," +

                        "FOREIGN KEY (category_id) " +
                        "REFERENCES "+DB_TABLE_CATEGORIES_NAME+" (id)," +
                        "FOREIGN KEY (type_id) "+
                        "REFERENCES "+DB_TABLE_TYPES_NAME+" (id)" +
                        ");";

        db.execSQL(createTableCategories);
        db.execSQL(createTableTypes);
        db.execSQL(createTableTransactions);

        insertCategories(db);
        insertTypes(db);
        //insertTransactions(db);
    }

    private void insertTypes(SQLiteDatabase db) {
        db.execSQL("INSERT INTO TYPES (name) VALUES ('expence')");
        db.execSQL("INSERT INTO TYPES (name) VALUES ('income')");
    }

    private void insertCategories(SQLiteDatabase db) {
        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('supplies',1)");
        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('services',1)");
        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('fun',1)");
        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('clothes',1)");
        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('gift',1)");
        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('health',1)");
        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('education',1)");
        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('other expence',1)");

        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('salary',2)");
        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('paid job',2)");
        db.execSQL("INSERT INTO CATEGORIES (name,type_id) VALUES ('other income',2)");
    }

    private void insertTransactions(SQLiteDatabase db) {
        String q1 = "INSERT INTO transactions (description,category_id,type_id,price,date,state) " +
                "VALUES ('fernet',3,1,1800,'2023-02-20',1)";

        String q2 = "INSERT INTO transactions (description,category_id,type_id,price,date,state) " +
                "VALUES ('coca',3,1,600,'2023-02-20',1)";

        String q3 = "INSERT INTO transactions (description,category_id,type_id,price,date,state) " +
                "VALUES ('sprite',3,1,2000,'2023-03-20',1)";

        String q4 = "INSERT INTO transactions (description,category_id,type_id,price,date,state) " +
                "VALUES ('gancia',3,1,1600,'2023-03-20',1)";

        String q5 = "INSERT INTO transactions (description,category_id,type_id,price,date,state) " +
                "VALUES ('sueldo mayo',9,2,80000,'2023-05-08',1)";

        String q6 = "INSERT INTO transactions (description,category_id,type_id,price,date,state) " +
                "VALUES ('vagabond manga',3,1,2000,'2023-05-08',1)";

        String q7 = "INSERT INTO transactions (description,category_id,type_id,price,date,state) " +
                "VALUES ('fideos',1,1,300,'2023-05-08',1)" ;

        db.execSQL(q1);
        db.execSQL(q2);
        db.execSQL(q3);
        db.execSQL(q4);
        db.execSQL(q5);
        db.execSQL(q6);
        db.execSQL(q7);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
