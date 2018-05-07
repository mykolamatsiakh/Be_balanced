package com.spe.bebalanced.bebalanced;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.google.common.primitives.Ints;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.spe.bebalanced.bebalanced.database.Skill;
import com.spe.bebalanced.bebalanced.database.SkillRoomDatabase;

import java.lang.reflect.Array;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.reactivex.Flowable;

/**
 * Created by Mykola Matsiakh on 08.03.18.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */

public class StatisticsActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener{

    protected BottomNavigationView navigationView;
    private XYPlot plot;
    SkillRoomDatabase mSkillRoomDatabase;
    Spinner mSpinner;

    List<Integer> skillValues = new ArrayList<>();
    List<String> skillDates = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        mSpinner = findViewById(R.id.spinner);

        mSkillRoomDatabase = Room.databaseBuilder(getApplicationContext(),
                SkillRoomDatabase.class, "skill_database").build();

        mSkillRoomDatabase.skillDao().findByName("Оточення").flatMap(item ->{
            skillValues.add(item.getValue());
            skillDates.add(item.getDate());
            Log.e("Skill value: ", skillValues.toString());
            return Flowable.fromArray();
        });

       // getSkillValue("Оточення");
        Log.e("DATE IS-->", skillDates.toString());
        plot = findViewById(R.id.plot);

        // create a couple arrays of y-values to plot:
        Log.d("VALUE --->", skillValues.toString());
        final Number[] domainLabels = {1, 2, 3, 6, 7, 8, 9, 10, 13, 14};
        Number[] series1Numbers = new Integer[]{1,34,2};
        Number[] series2Numbers = {5, 2, 10, 5, 20, 10, 40, 20, 80, 40};

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels);

        LineAndPointFormatter series2Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels_2);

        // add an "dash" effect to the series2 line:
        series2Format.getLinePaint().setPathEffect(new DashPathEffect(new float[] {

                // always use DP when specifying pixel sizes, to keep things consistent across devices:
                PixelUtils.dpToPix(20),
                PixelUtils.dpToPix(15)}, 0));

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        series2Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
        plot.addSeries(series2, series2Format);
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[i]);
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

//        GraphView graph = findViewById(R.id.graph);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(1, 5)
//        });
//        graph.addSeries(series);
//        Paint paint = new Paint();
//                paint.setStyle(Paint.Style.STROKE);
//                paint.setStrokeWidth(3);
//                paint.setColor(getResources().getColor(R.color.DarkGreen));
//                paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
//                series.setCustomPaint(paint);

//        getSkillValue("Стосунки");
//        getSkillDate("Стосунки");
//        Log.d("Array ->",getSkillValue("Стосунки").toString());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.skills_item) {
            startActivity(new Intent(this, MainActivity.class));
        } else{
            startActivity(new Intent(this, StatisticsActivity.class));
        }
        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }

    }

    int getContentViewId() {
        return R.layout.activity_main;
    }

    int getNavigationMenuItemId() {
        return R.id.statistics_item;
    }

    @SuppressLint("CheckResult")
    private List<Integer> getSkillValue(String name) {
        mSkillRoomDatabase.skillDao().findByName(name).flatMap(item ->{
           skillValues.add(item.getValue());
           return Flowable.fromArray();
        });
        return skillValues;
    }

    @SuppressLint("CheckResult")
    private List<String> getSkillDate(String name){
        mSkillRoomDatabase.skillDao().findByName(name).subscribe(item -> {
            // Now you can do with each item.
            Log.d("Item is", item.toString());
            skillDates.add(item.getDate());
        });
        return skillDates;
    }


    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
