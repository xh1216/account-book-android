package com.example.android.accountbook.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.android.accountbook.database.DbSchema.SettingTable;

public class SettingCursorWrapper extends CursorWrapper {
    public SettingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public String getDateFormat() {
        String dateFormat = getString(getColumnIndex(SettingTable.Cols.DATE_FORMAT));

        return dateFormat;
    }
}
