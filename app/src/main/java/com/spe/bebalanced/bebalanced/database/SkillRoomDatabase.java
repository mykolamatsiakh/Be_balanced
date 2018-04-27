package com.spe.bebalanced.bebalanced.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Mykola Matsiakh on 21.04.18.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */
@Database(entities = {Skill.class}, version = 1)
public abstract class SkillRoomDatabase extends RoomDatabase {

    public abstract SkillDao skillDao();

    private static SkillRoomDatabase INSTANCE;

    public static SkillRoomDatabase getInstance( Context context) {
        if (INSTANCE == null) {
            synchronized (SkillRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SkillRoomDatabase.class, "skill_database")
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
