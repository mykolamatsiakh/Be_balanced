package com.spe.bebalanced.bebalanced.database;

import android.arch.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Mykola Matsiakh on 24.04.18.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */
public interface iSkillDataResource {
    Flowable<List<Skill>> getAll();

    Flowable<Skill> findByName(String name);

    Flowable<List<Skill>> getDate();

    void insertAll(List<Skill> skill);

    void insert(Skill skill);

    void update(Skill skill);

    void delete(Skill skill);

}
