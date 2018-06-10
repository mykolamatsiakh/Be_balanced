package com.spe.bebalanced.bebalanced.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mykola Matsiakh on 21.04.18.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */
@Entity(tableName = "skills_table")
public class Skill {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "value")
    @TypeConverters(SkillTypeConventer.class)
    private List<Integer> mValue;

    @ColumnInfo(name = "date")
    @TypeConverters(SkillTypeConventer.class)
    private List<Integer> mDate;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Integer> getValue() {
        return mValue;
    }

    public void setValue(List<Integer> value) {
        mValue = value;
    }

    public List<Integer> getDate() {
        return mDate;
    }

    public void setDate(List<Integer> date) {
        mDate = date;
    }

    public Skill() {

    }

    @Ignore
    public Skill(String name) {
        mName = name;
        mValue = new ArrayList<>();
        mDate = new ArrayList<>();
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
