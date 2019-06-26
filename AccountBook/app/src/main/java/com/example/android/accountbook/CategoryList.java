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

    public void removeRecord(Category c) {
        mDatabase.delete(CategoryTable.NAME,
                CategoryTable.Cols.UUID + " = ?",
                new String[] { c.getId().toString() });
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        CategoryCursorWrapper cursor = queryCategories(null, null);

        try {
            if (cursor.getCount() == 0) {

                String[] expCatName = {"Food", "Transport", "Entertainment"};
                int[] expIcon = {R.drawable.exp_cat0, R.drawable.exp_cat1, R.drawable.exp_cat2};
                String[] incomeCatName = {"Salary", "Pocket money"};
                int[] incomeIcon = {R.drawable.income_cat0, R.drawable.income_cat1};
                for (int i = 0; i < expCatName.length; i++) {
                    Category cat = new Category();
                    cat.setName(expCatName[i]);
                    cat.setIcon(expIcon[i]);
                    cat.setIncome(false);
                    addCategory(cat);
                    categories.add(cat);
                }
                for (int i = 0; i < incomeCatName.length; i++) {
                    Category cat = new Category();
                    cat.setName(incomeCatName[i]);
                    cat.setIcon(incomeIcon[i]);
                    cat.setIncome(true);
                    addCategory(cat);
                    categories.add(cat);
                }
                return categories;
            }
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

    public List<Category> getIncomeCategories() {
        List<Category> categories = new ArrayList<>();

        CategoryCursorWrapper cursor = queryCategories(
                CategoryTable.Cols.IS_INCOME + " = ?",
                new String[] { String.valueOf(1) }
        );

        try {
            if (cursor.getCount() == 0) {
                String[] incomeCatName = {"Salary", "Pocket money"};
                int[] incomeIcon = {R.drawable.income_cat0, R.drawable.income_cat1};
                for (int i = 0; i < incomeCatName.length; i++) {
                    Category cat = new Category();
                    cat.setName(incomeCatName[i]);
                    cat.setIcon(incomeIcon[i]);
                    cat.setIncome(true);
                    addCategory(cat);
                    categories.add(cat);
                }
                return categories;
            }
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

    public List<Category> getExpenseCategories() {
        List<Category> categories = new ArrayList<>();

        CategoryCursorWrapper cursor = queryCategories(
                CategoryTable.Cols.IS_INCOME + " = ?",
                new String[] { String.valueOf(0) }
        );

        try {
            if (cursor.getCount() == 0) {
                String[] expCatName = {"Food", "Transport", "Entertainment"};
                int[] expIcon = {R.drawable.exp_cat0, R.drawable.exp_cat1, R.drawable.exp_cat2};
                for (int i = 0; i < expCatName.length; i++) {
                    Category cat = new Category();
                    cat.setName(expCatName[i]);
                    cat.setIcon(expIcon[i]);
                    cat.setIncome(false);
                    addCategory(cat);
                    categories.add(cat);
                }
                return categories;
            }
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
        values.put(CategoryTable.Cols.IS_INCOME, category.isIncome() ? 1 : 0);

        return values;
    }
}
