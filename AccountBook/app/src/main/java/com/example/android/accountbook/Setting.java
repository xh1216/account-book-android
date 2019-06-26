package com.example.android.accountbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.accountbook.database.DatabaseHelper;
import com.example.android.accountbook.database.DbSchema;
import com.example.android.accountbook.database.DbSchema.SettingTable;
import com.example.android.accountbook.database.SettingCursorWrapper;

public class Setting {
    private static Setting sSetting;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Setting get(Context context) {
        if (sSetting == null) {
            sSetting = new Setting(context);
        }
        return sSetting;
    }

    private Setting(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DatabaseHelper(mContext).getWritableDatabase();
    }

    public void addDateFormat(String dateFormat) {
        ContentValues values = new ContentValues();
        values.put(SettingTable.Cols.DATE_FORMAT, dateFormat);
        mDatabase.insert(SettingTable.NAME, null, values);
    }

    public String getDateFormat() {
        Cursor c = mDatabase.query(
                SettingTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        SettingCursorWrapper cursor = new SettingCursorWrapper(c);
        try {
            if (cursor.getCount() == 0) {
                addDateFormat("dd/MM/yyyy");
                return "dd/MM/yyyy";
            }

            cursor.moveToFirst();
            return cursor.getDateFormat();

        } finally {
            cursor.close();
        }
    }

    public void setDateFormat(String dateFormat) {
        ContentValues values = new ContentValues();
        values.put(SettingTable.Cols.DATE_FORMAT, dateFormat);
        mDatabase.update(SettingTable.NAME, values, null, null);
    }
}
