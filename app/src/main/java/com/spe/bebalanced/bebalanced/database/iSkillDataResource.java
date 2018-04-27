package com.spe.bebalanced.bebalanced.database;

import io.reactivex.Flowable;

import java.util.List;

/**
 * Created by Mykola Matsiakh on 24.04.18.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */
public interface iSkillDataResource {
    Flowable<List<Skill>> getAll();

    Flowable<Skill> findByName(String name);

    void insertAll(List<Skill> skill);

    void insert(Skill skill);

    void update(Skill skill);

    void delete(Skill skill);

}
