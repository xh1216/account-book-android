package com.example.android.accountbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.accountbook.database.DatabaseHelper;
import com.example.android.accountbook.database.DbSchema;
import com.example.android.accountbook.database.RecordCursorWrapper;
import com.example.android.accountbook.database.DbSchema.RecordTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordList {
    private static RecordList sRecordList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static RecordList get(Context context) {
        if (sRecordList == null) {
            sRecordList = new RecordList(context);
        }

        return sRecordList;
    }

    private RecordList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DatabaseHelper(mContext).getWritableDatabase();
    }

    public void addRecord(Record r) {
        ContentValues values = getContentValues(r);

        mDatabase.insert(RecordTable.NAME, null, values);
    }

    public void removeRecord(Record r) {
        mDatabase.delete(RecordTable.NAME,
                RecordTable.Cols.UUID + " = ?",
                new String[] { r.getId().toString() });
    }

    public List<Record> getRecords() {
        List<Record> records = new ArrayList<>();

        RecordCursorWrapper cursor = queryRecords(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                records.add(cursor.getRecord());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return records;
    }

    public Record getRecord(UUID id) {
        RecordCursorWrapper cursor = queryRecords(
                RecordTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            Record record =  cursor.getRecord();

            String categoryId = cursor.getString(4);
            Category category = CategoryList.get(mContext).getCategory(UUID.fromString(categoryId));
            if (category != null) {
                record.setCategory(category);
            }
            System.out.println(record.getCategory().getName());

            return record;
        } finally {
            cursor.close();
        }
    }

    public void updateRecord(Record record) {
        String uuidString = record.getId().toString();
        ContentValues values = getContentValues(record);

        mDatabase.update(RecordTable.NAME, values,
                RecordTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private RecordCursorWrapper queryRecords(String whereClause, String[] whereArgs) {
//        String[] columns = {"records.uuid", "date", "amount", "category_id", "memo", "categories.cat_name", "categories.icon"};
        Cursor cursor = mDatabase.query(
                RecordTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
//        String query = "SELECT * FROM records INNER JOIN categories ON records.category_id = categories.uuid";
//        if (whereClause != null) {
//            query = query + " WHERE records." + whereClause;
//        }
//
//        Cursor cursor = mDatabase.rawQuery(query, whereArgs);

        return new RecordCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Record record) {
        ContentValues values = new ContentValues();
        values.put(RecordTable.Cols.UUID, record.getId().toString());
        values.put(RecordTable.Cols.DATE, record.getDate().getTime());
        values.put(RecordTable.Cols.AMOUNT, record.getAmount());
        values.put(RecordTable.Cols.CATEGORY_ID, record.getCategory().getId().toString());
        values.put(RecordTable.Cols.MEMO, record.getMemo());
        values.put(RecordTable.Cols.IS_INCOME, record.isIncome() ? 1 : 0);
        return values;
    }
}
