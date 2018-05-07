package com.spe.bebalanced.bebalanced;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.spe.bebalanced.bebalanced.database.Skill;
import com.spe.bebalanced.bebalanced.database.SkillDataResource;
import com.spe.bebalanced.bebalanced.database.SkillRepository;
import com.spe.bebalanced.bebalanced.database.SkillRoomDatabase;
import com.ultramegasoft.radarchart.RadarEditWidget;
import com.ultramegasoft.radarchart.RadarHolder;
import com.ultramegasoft.radarchart.RadarView;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    RadarEditWidget mEditWidget;
    RadarView mRadarView;
    Animation mEditInAnimation;
    Button mButton;
    TextView mTextView;
    protected BottomNavigationView navigationView;

    SkillRoomDatabase mSkillRoomDatabase;
    List<Skill> mSkillList;

    /**
     * The animation to use when hiding the RadarEditWidget
     */
     Animation mEditOutAnimation;


    @NonNull
    private ArrayList<RadarHolder> mData = new ArrayList<RadarHolder>() {
        {
            add(new RadarHolder("Тіло", 5));
            add(new RadarHolder("Оточення", 5));
            add(new RadarHolder("Стосунки", 5));
            add(new RadarHolder("Кар'єра", 5));
            add(new RadarHolder("Гроші", 5));
            add(new RadarHolder("Саморозвиток", 5));
            add(new RadarHolder("Сенс", 5));
            add(new RadarHolder("Відпочинок", 5));

        }
    };



    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSkillRoomDatabase = Room.databaseBuilder(getApplicationContext(),
                SkillRoomDatabase.class, "skill_database").build();
        Flowable<List<Skill>> skill =  mSkillRoomDatabase.skillDao().getAll();
        Log.d("BEM", skill.toString());
        String[] array = getResources().getStringArray(R.array.advices);
        final List<String> mainParams = Arrays.asList(
            "Тіло",
            "Оточення",
            "Стосунки", "Кар'єра", "Гроші", "Саморозвиток", "Сенс", "Відпочинок");

        mSkillRoomDatabase.skillDao().getAll().flatMap(list -> {
            Skill[] skills = new Skill[list.size()];
            list.toArray(skills);
            return Flowable.fromArray(skills);
        }).subscribe(item -> {
            // Now you can do with each item.
            Log.d("Item is", item.toString());

        });

        final List<String> resultOptions = Arrays.asList(array);
        mRadarView = findViewById(R.id.radar);
        mEditWidget = findViewById(R.id.edit_widget);
        mRadarView.setData(mData);
        mRadarView.setOnClickListener(this);
        mRadarView.setMaxValue(9);
        mRadarView.setBottom(0);
        navigationView = findViewById(R.id.navigation);
        mTextView=findViewById(R.id.advice_text_view);
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
                //Log.e(mRadarView.getSelectedName(), String.valueOf(mData.get(mRadarView.getSelectedIndex()).value));
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
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                final int scale = 100 / mRadarView.getMaxValue();

                if(fromUser) {
                    final int value = Math.round(i / scale);
                    mRadarView.setSelectedValue(value);
                    int mainPointValue = mainParams.indexOf(mRadarView.getSelectedName());
                    int subpointValue = mRadarView.getSelectedValue();

//                    Log.d("message", String.valueOf(resultOptions.get(mainPointValue * 10 + subpointValue)));
                    mTextView.setText(String.valueOf(i/10) + " - " +
                            String.valueOf(resultOptions.get(mainPointValue * 10 + subpointValue)));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            }

        );
        mEditWidget.setOnButtonClickListener(new RadarEditWidget.OnButtonClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSave() {
                mData = mRadarView.getData();
                setEditMode(false);
                mButton.setVisibility(View.VISIBLE);
                new DatabaseAsync().execute();
//                LocalDate today = LocalDate.now();
//                String month = String.valueOf(today.getMonthValue());
//                String day = String.valueOf(today.getDayOfMonth());


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

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            java.util.Date currentTime = java.util.Calendar.getInstance().getTime();
            String date = currentTime.toString().substring(8,10)+"/"+currentTime.getMonth();
            for (int i=0; i< mData.size(); i++) {
                Skill skill = new Skill(mData.get(i).name, mData.get(i).value, date);
                mSkillRoomDatabase.skillDao().insert(skill);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //To after addition operation here.
        }
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

            Log.e("action", mRadarView.getSelectedName());
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


