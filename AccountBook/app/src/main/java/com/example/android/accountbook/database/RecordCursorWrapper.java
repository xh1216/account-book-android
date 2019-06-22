package com.example.android.accountbook.database;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.android.accountbook.CategoryList;
import com.example.android.accountbook.MainActivity;
import com.example.android.accountbook.Record;
import com.example.android.accountbook.database.DbSchema.RecordTable;
import java.util.Date;
import java.util.UUID;

public class RecordCursorWrapper extends CursorWrapper {

    public RecordCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Record getRecord() {
        String uuidString = getString(getColumnIndex(RecordTable.Cols.UUID));
        long date = getLong(getColumnIndex(RecordTable.Cols.DATE));
        int amount = getInt(getColumnIndex(RecordTable.Cols.AMOUNT));
        String memo = getString(getColumnIndex(RecordTable.Cols.MEMO));
        int isIncome = getInt(getColumnIndex(RecordTable.Cols.IS_INCOME));

        Record record = new Record(UUID.fromString(uuidString));
        record.setDate(new Date(date));
        record.setAmount(amount);
        record.setMemo(memo);
        record.setIncome(isIncome != 0);

        return record;
    }
}
