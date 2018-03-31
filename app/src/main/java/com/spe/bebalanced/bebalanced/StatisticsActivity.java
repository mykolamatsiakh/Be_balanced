package com.spe.bebalanced.bebalanced;

import android.content.Intent;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Mykola Matsiakh on 08.03.18.
 * Copyright (c) 2017, Reynolds. All rights reserved.
 */

public class StatisticsActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener{

    protected BottomNavigationView navigationView;
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        mSpinner = findViewById(R.id.spinner);
        GraphView graph = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 1),
                new DataPoint(2, 2),
                new DataPoint(3, 3),
                new DataPoint(4, 4),
                new DataPoint(5, 5),
                new DataPoint(1, 6),
                new DataPoint(2, 7),
                new DataPoint(5, 8),
                new DataPoint(7, 9),
                new DataPoint(5, 10),
        });
        graph.addSeries(series);
        Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(3);
                paint.setColor(getResources().getColor(R.color.DarkGreen));
                paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
                series.setCustomPaint(paint);

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
