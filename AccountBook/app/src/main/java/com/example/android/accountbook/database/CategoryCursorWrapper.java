package com.example.android.accountbook.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.android.accountbook.Category;
import com.example.android.accountbook.database.DbSchema.CategoryTable;
import java.util.UUID;

public class CategoryCursorWrapper extends CursorWrapper {
    public CategoryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Category getCategory() {
        String uuidString = getString(getColumnIndex(CategoryTable.Cols.UUID));
        String catName = getString(getColumnIndex(CategoryTable.Cols.CAT_NAME));
        int icon = getInt(getColumnIndex(CategoryTable.Cols.ICON));
        int isIncome = getInt(getColumnIndex(CategoryTable.Cols.IS_INCOME));

        Category category = new Category(UUID.fromString(uuidString));
        category.setName(catName);
        category.setIcon(icon);
        category.setIncome(isIncome != 0);
        return category;
    }
}
