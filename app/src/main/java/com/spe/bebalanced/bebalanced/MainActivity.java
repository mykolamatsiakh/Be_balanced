package com.spe.bebalanced.bebalanced;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ultramegasoft.radarchart.RadarEditWidget;
import com.ultramegasoft.radarchart.RadarHolder;
import com.ultramegasoft.radarchart.RadarView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    RadarEditWidget mEditWidget;
    RadarView mRadarView;
    Animation mEditInAnimation;
    Button mButton;
    TextView mTextView;
    SeekBar mSeekbar;
    protected BottomNavigationView navigationView;

    /**
     * The animation to use when hiding the RadarEditWidget
     */
     Animation mEditOutAnimation;


    @NonNull
    private ArrayList<RadarHolder> mData = new ArrayList<RadarHolder>() {
        {
            add(new RadarHolder("Тіло", 3));
            add(new RadarHolder("Оточення", 4));
            add(new RadarHolder("Стосунки", 4));
            add(new RadarHolder("Кар'єра", 4));
            add(new RadarHolder("Гроші", 2));
            add(new RadarHolder("Саморозвиток", 2));
            add(new RadarHolder("Сенс", 2));
            add(new RadarHolder("Відпочинок", 2));

        }
    };



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRadarView = findViewById(R.id.radar);
        mEditWidget = findViewById(R.id.edit_widget);
        mRadarView.setData(mData);
        mRadarView.setOnClickListener(this);
        mRadarView.setMaxValue(10);
        mRadarView.setTop(10);
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        findViewById(R.id.rew_button_back);
        mButton = findViewById(R.id.button_edit);
        // Set the data for the RadarView to display.
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
            }
        });

        // Set the target RadarView for the RadarEditWidget to control.
        mEditWidget.setTarget(mRadarView);
        mRadarView.setInteractive(true);
        mRadarView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this){
            public void onSwipeTop(){
            }
            public void onSwipeRight(){
                mRadarView.turnCCW();
            }
            public void onSwipeLeft() {
                mRadarView.turnCW();
            }
            public void onSwipeBottom() {
            }
        });
        Button mButtonSave = findViewById(R.id.rew_button_save);
        Button mButtonCancel = findViewById(R.id.rew_button_cancel);
        SeekBar mSeekBar = findViewById(R.id.rew_slider);
        mButtonCancel.setVisibility(View.INVISIBLE);
        mButtonSave.setText("Зберегти");
        mEditWidget.setVisibility(View.VISIBLE);
        mEditWidget.setShowButtonBar(true);
        mEditWidget.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        // Set the callbacks for the RadarEditWidget buttons.
        mEditWidget.setOnButtonClickListener(new RadarEditWidget.OnButtonClickListener() {
            @Override
            public void onSave() {
                mData = mRadarView.getData();
                Log.d("VALUE IS: ", String.valueOf(mData.get(1).value));
                setEditMode(false);
                mButton.setVisibility(View.VISIBLE);

            }


            @Override
            public void onCancel() {
                setVisible(false);
                mRadarView.setData(mData);
                setEditMode(false);
                mButton.setVisibility(View.VISIBLE);
            }
        });

        mEditInAnimation = AnimationUtils.loadAnimation(this, R.anim.flavor_edit_in);
        mEditOutAnimation = AnimationUtils.loadAnimation(this, R.anim.flavor_edit_out);


        findViewById(R.id.button_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);

            }
        });


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

    private void setEditMode(boolean editMode) {
        if(editMode == mRadarView.isInteractive()) {
            return;
        }

        if(editMode) {
            // Enable interactive mode.
            mRadarView.setInteractive(true);


            // Show the RadarEditWidget.
            mEditWidget.startAnimation(mEditInAnimation);
            mEditWidget.setVisibility(View.VISIBLE);
        } else {
            // Disable interactive mode.
            mRadarView.setInteractive(false);

            // Hide the RadarEditWidget.
            mEditWidget.startAnimation(mEditOutAnimation);
            mEditWidget.setVisibility(View.GONE);
        }
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
        return R.layout.activity_statistics;
    }

    int getNavigationMenuItemId() {
        return R.id.statistics_item;
    }

    @Override
    public void onClick(View view) {
        mEditWidget.setShowButtonBar(true);
    }
}


