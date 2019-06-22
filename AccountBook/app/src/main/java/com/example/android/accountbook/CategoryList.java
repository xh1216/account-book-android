package com.example.android.accountbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.accountbook.database.CategoryCursorWrapper;
import com.example.android.accountbook.database.DatabaseHelper;
import com.example.android.accountbook.database.DbSchema;
import com.example.android.accountbook.database.DbSchema.CategoryTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryList {

    private static CategoryList sCategoryList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CategoryList get(Context context) {
        if (sCategoryList == null) {
            sCategoryList = new CategoryList(context);
        }

        return sCategoryList;
    }

    private CategoryList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DatabaseHelper(mContext).getWritableDatabase();

    }

    public void addCategory(Category c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CategoryTable.NAME, null, values);
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        CategoryCursorWrapper cursor = queryCategories(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                categories.add(cursor.getCategory());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return categories;
    }

    public Category getCategory(UUID id) {
        CategoryCursorWrapper cursor = queryCategories(
                CategoryTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCategory();
        } finally {
            cursor.close();
        }
    }

    public void updateCategory(Category category) {
        String uuidString = category.getId().toString();
        ContentValues values = getContentValues(category);

        mDatabase.update(CategoryTable.NAME, values,
                CategoryTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private CategoryCursorWrapper queryCategories(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CategoryTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new CategoryCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Category category) {
        ContentValues values = new ContentValues();
        values.put(CategoryTable.Cols.UUID, category.getId().toString());
        values.put(CategoryTable.Cols.CAT_NAME, category.getName());
        values.put(CategoryTable.Cols.ICON, category.getIcon());

        return values;
    }
}
