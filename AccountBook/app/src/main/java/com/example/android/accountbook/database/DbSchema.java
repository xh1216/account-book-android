package com.example.android.accountbook.database;

public class DbSchema {
    public static final class RecordTable {
        public static final String NAME = "records";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String AMOUNT = "amount";
            public static final String CATEGORY_ID = "category_id";
            public static final String MEMO = "memo";
            public static final String IS_INCOME = "is_income";
        }
    }
    public static final class CategoryTable {
        public static final String NAME = "categories";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String CAT_NAME = "cat_name";
            public static final String ICON = "icon";
            public static final String IS_INCOME = "is_income";
        }
    }

    public static final class SettingTable {
        public static final String NAME = "setting";

        public static final class Cols {
            public static final String DATE_FORMAT = "date_format";
        }
    }
}
