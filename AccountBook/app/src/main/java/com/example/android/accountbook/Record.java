package com.example.android.accountbook;

import java.util.Date;
import java.util.UUID;

public class Record {

    private UUID mId;
    private Date mDate;
    private int mAmount;
    private Category mCategory;
    private String mMemo;
    private Boolean isIncome;

    public Record() {
        this(UUID.randomUUID());
    }

    public Record(UUID id) {
        mId = id;
        mDate = new Date();
        mAmount = 0;
        mCategory = new Category();
        mMemo = "abc";
        isIncome = false;
    }

    public UUID getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    public String getMemo() {
        return mMemo;
    }

    public void setMemo(String memo) {
        mMemo = memo;
    }

    public Boolean isIncome() {
        return isIncome;
    }

    public void setIncome(Boolean income) {
        isIncome = income;
    }
}
