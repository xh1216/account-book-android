package com.example.android.accountbook;

import java.util.UUID;

public class Category {
    private UUID mId;
    private String mName;
    private String mIcon;
    private Boolean isIncome;

    public Category() {
        this(UUID.randomUUID());
    }

    public Category(UUID id) {
        mId = id;
        mName = "";
        mIcon = "";
        isIncome = false;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public Boolean isIncome() {
        return isIncome;
    }

    public void setIncome(Boolean income) {
        isIncome = income;
    }
}
