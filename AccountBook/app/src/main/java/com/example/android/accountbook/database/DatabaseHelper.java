package com.example.android.accountbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.accountbook.database.DbSchema.CategoryTable;
import com.example.android.accountbook.database.DbSchema.RecordTable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "accountBase.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + RecordTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                RecordTable.Cols.UUID + ", " +
                RecordTable.Cols.DATE + ", " +
                RecordTable.Cols.AMOUNT + ", " +
                RecordTable.Cols.CATEGORY_ID + ", " +
                RecordTable.Cols.MEMO + ", " +
                RecordTable.Cols.IS_INCOME + ", " +
                " FOREIGN KEY (" + RecordTable.Cols.CATEGORY_ID + ") REFERENCES " + CategoryTable.NAME + "(" + CategoryTable.Cols.UUID +
                ")) ");

        db.execSQL("create table " + CategoryTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CategoryTable.Cols.UUID + ", " +
                CategoryTable.Cols.CAT_NAME + ", " +
                CategoryTable.Cols.ICON +
                ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
