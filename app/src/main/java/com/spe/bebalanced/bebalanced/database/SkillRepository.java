package com.spe.bebalanced.bebalanced.database;

import io.reactivex.Flowable;

import java.util.List;

/**
 * Created by Mykola Matsiakh on 21.04.18.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */
public class SkillRepository implements iSkillDataResource{
    private iSkillDataResource mLocalDatasource;
    private static SkillRepository mInstance;

    public SkillRepository(iSkillDataResource mLocalDataSource) {
        this.mLocalDatasource = mLocalDataSource;
    }

    public static SkillRepository getInstance(iSkillDataResource skillDataResource) {
        if (mInstance == null){
            mInstance = new SkillRepository(skillDataResource);
        }
        return mInstance;
    }

    @Override
    public Flowable<List<Skill>> getAll() {
        return mLocalDatasource.getAll();
    }

    @Override
    public Flowable<Skill> findByName(String name) {
        return mLocalDatasource.findByName(name);
    }

    @Override
    public void insertAll(List<Skill> skill) {
            mLocalDatasource.insertAll(skill);
    }

    public void insert (Skill mSkill) {
        mLocalDatasource.insert(mSkill);
    }

    @Override
    public void update(Skill skill) {
        mLocalDatasource.insert(skill);
    }

    @Override
    public void delete(Skill skill) {
        mLocalDatasource.delete(skill);
    }

}
