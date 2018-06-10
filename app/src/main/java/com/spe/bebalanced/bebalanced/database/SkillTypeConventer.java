package com.spe.bebalanced.bebalanced.database;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Victor Artemyev on 6/10/18.
 * Copyright (c) 2018, LadbrokesCoral. All rights reserved.
 */
public class SkillTypeConventer {

    @TypeConverter
    public static List<Integer> toList(String data) {
        List<Integer> result = new ArrayList<>();
        if (data == null || data.isEmpty()) {
            return result;
        }

        for (String value : Arrays.asList(data.split(","))) {
            result.add(Integer.valueOf(value));
        }

        return result;
    }

    @TypeConverter
    public static String toString(List<Integer> data) {
        return TextUtils.join(",", data);
    }
}
