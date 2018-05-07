package com.spe.bebalanced.bebalanced.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Mykola Matsiakh on 21.04.18.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */
@Entity(tableName = "skills_table")
public class Skill {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "value")
    private int mValue;

    @ColumnInfo(name = "date")
    private String mDate;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    @NonNull
    public int getId() {
        return mId;
    }

    public void setId(@NonNull int id) {
        mId = id;
    }

    public Skill() {

    }

    @Ignore
    public Skill(String name, int value, String date) {
        mName = name;
        mValue = value;
        mDate = date;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "mName='" + mName + '\'' +
                ", mValue=" + mValue +
                ", mDate='" + mDate + '\'' +
                '}';
    }
}
