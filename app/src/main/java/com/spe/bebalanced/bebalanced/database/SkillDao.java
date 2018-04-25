package com.spe.bebalanced.bebalanced.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.spe.bebalanced.bebalanced.database.Skill;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Mykola Matsiakh on 21.04.18.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */

@Dao
public interface SkillDao {
    @Query("SELECT * FROM skills_table")
    Flowable<List<Skill>> getAll();

    @Query("SELECT * FROM skills_table WHERE name = :name")
    Flowable<Skill> findByName(String name);

    @Query("SELECT date FROM skills_table")
    Flowable<List<Skill>> getDate();

    @Insert
    void insert(Skill... skill);


    @Insert
    void insertAll(List<Skill> skill);


    @Update
    void update(Skill... skill);

    @Delete
    void delete(Skill... skill);
}
