package com.example.android.accountbook.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.android.accountbook.Record;
import com.example.android.accountbook.database.DbSchema.RecordTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

public class RecordCursorWrapper extends CursorWrapper {

    public RecordCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Record getRecord() {
        String uuidString = getString(getColumnIndex(RecordTable.Cols.UUID));
        String date = getString(getColumnIndex(RecordTable.Cols.DATE));
        int amount = getInt(getColumnIndex(RecordTable.Cols.AMOUNT));
        String memo = getString(getColumnIndex(RecordTable.Cols.MEMO));
        int isIncome = getInt(getColumnIndex(RecordTable.Cols.IS_INCOME));

        Record record = new Record(UUID.fromString(uuidString));
        try {
            record.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        record.setAmount(amount);
        record.setMemo(memo);
        record.setIncome(isIncome != 0);

        return record;
    }
}
