package com.spe.bebalanced.bebalanced;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.ultramegasoft.radarchart.RadarEditWidget;
import com.ultramegasoft.radarchart.RadarHolder;
import com.ultramegasoft.radarchart.RadarView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RadarEditWidget mEditWidget;
    RadarView mRadarView;
    private Animation mEditInAnimation;
    Button mButton;

    /**
     * The animation to use when hiding the RadarEditWidget
     */
    private Animation mEditOutAnimation;

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
        mRadarView.setMaxValue(10);
        mRadarView.setTop(10);

        // Set the data for the RadarView to display.

        // Set the target RadarView for the RadarEditWidget to control.
        mEditWidget.setTarget(mRadarView);
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
                mRadarView.setData(mData);
                setEditMode(false);
                mButton.setVisibility(View.VISIBLE);
            }
        });

        mEditInAnimation = AnimationUtils.loadAnimation(this, R.anim.flavor_edit_in);
        mEditOutAnimation = AnimationUtils.loadAnimation(this, R.anim.flavor_edit_out);
        mButton = findViewById(R.id.button_edit);

        findViewById(R.id.button_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditMode(true);
                mButton.setVisibility(View.GONE);
            }
        });

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



    }


