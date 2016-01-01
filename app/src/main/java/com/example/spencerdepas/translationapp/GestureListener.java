package com.example.spencerdepas.translationapp;

import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by SpencerDepas on 12/16/15.
 */
public class GestureListener  extends GestureDetector.SimpleOnGestureListener {
    //class content


    public ButtonSelector delegate = null;
    private float flingMin = 100;
    private float velocityMin = 100;
    private GestureDetectorCompat gDetect;
    //user will move forward through messages on fling up or left
    boolean forward = false;
    //user will move backward through messages on fling down or right
    boolean backward = false;
    private String TAG = "MyGestureListener";


    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        //determine what happens on fling events
        Log.d(TAG, "forward=true : " + forward);
        Log.d(TAG, "backward=true : " + backward);

        //calculate the change in X position within the fling gesture
        float horizontalDiff = event2.getX() - event1.getX();
//calculate the change in Y position within the fling gesture
        float verticalDiff = event2.getY() - event1.getY();


        float absHDiff = Math.abs(horizontalDiff);
        float absVDiff = Math.abs(verticalDiff);
        float absVelocityX = Math.abs(velocityX);
        float absVelocityY = Math.abs(velocityY);

        Log.d(TAG, "event1 : " + event1);
        Log.d(TAG, "event2 : " + event2);

        Log.d(TAG, "velocityX : " + velocityX);
        Log.d(TAG, "velocityY : " + velocityY);

        if(absHDiff>absVDiff && absHDiff>flingMin && absVelocityX>velocityMin){
            //move forward or backward

            if(horizontalDiff>0) {
                backward=true;
            }
            else {
                forward=true;
            }
            Log.d(TAG, "forward=true : " + forward);
            Log.d(TAG, "backward=true : " + backward);

            //user is cycling forward through messages
            if(forward){

                delegate.gestureNextButton();

            } else if(backward){

                delegate.gesturePreviousButton();

            }

            backward=false;
            forward=false;

        }



        return true;
    }


}