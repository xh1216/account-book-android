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
        String icon = getString(getColumnIndex(CategoryTable.Cols.ICON));

        Category category = new Category(UUID.fromString(uuidString));
        category.setName(catName);
        category.setIcon(icon);
        return category;
    }
}
