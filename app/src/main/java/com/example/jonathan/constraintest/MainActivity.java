package com.example.jonathan.constraintest;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;


public class MainActivity extends AppCompatActivity {

    private float y1,y2;
    static final int MIN_DISTANCE = 150;
    boolean phase = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConstraintSet constraintSet1 = new ConstraintSet();
        ConstraintSet constraintSet2 = new ConstraintSet();

        constraintSet1.clone(this, R.layout.activity_main);
        constraintSet2.clone(this, R.layout.activity_main_alt);
        ConstraintLayout mConstraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);


        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(800);


        findViewById(R.id.FAB).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    y1 = event.getY();
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    y2 = event.getY();
                    float deltaY = y2 - y1;
                    if (Math.abs(deltaY) > MIN_DISTANCE)
                    {
                        if(y1>y2){
                            TransitionManager.beginDelayedTransition(mConstraintLayout,transition);
                            constraintSet2.applyTo(mConstraintLayout); // set new constraints
                            phase = !phase;
                        }else{
                            TransitionManager.beginDelayedTransition(mConstraintLayout,transition);
                            constraintSet1.applyTo(mConstraintLayout); // set new constraints
                            phase = !phase;
                        }

                    }else{
                        TransitionManager.beginDelayedTransition(mConstraintLayout,transition);
                        if (phase = !phase) {
                            constraintSet1.applyTo(mConstraintLayout); // set new constraints
                        }  else {
                            constraintSet2.applyTo(mConstraintLayout); // set new constraints
                        }
                    }
                    return true;
                }
                return false;
            }
        });


    }


}
