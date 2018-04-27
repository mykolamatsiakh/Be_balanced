package com.spe.bebalanced.bebalanced.database;

import io.reactivex.Flowable;

import java.util.List;

/**
 * Created by Mykola Matsiakh on 24.04.18.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */
public class SkillDataResource implements iSkillDataResource{

    private SkillDao mSkillDao;
    public static SkillDataResource mInstance;

    public SkillDataResource(SkillDao skillDao) {
        this.mSkillDao = skillDao;
    }


    public static SkillDataResource getInstance(SkillDao skillDao){
        if(mInstance ==null){
            mInstance = new SkillDataResource(skillDao);
        }
        return mInstance;
    }

    @Override
    public Flowable<List<Skill>> getAll() {
        return mSkillDao.getAll();
    }

    @Override
    public Flowable<Skill> findByName(String name) {
        return mSkillDao.findByName(name);
    }

    @Override
    public void insertAll(List<Skill> skill) {
        mSkillDao.insertAll(skill);
    }

    @Override
    public void insert(Skill skill) {
        mSkillDao.insert(skill);
    }

    @Override
    public void update(Skill skill) {
        mSkillDao.update(skill);
    }

    @Override
    public void delete(Skill skill) {
        mSkillDao.delete(skill);
    }
}
