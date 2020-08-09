package com.rahimian.app;

import android.view.GestureDetector;
import android.view.MotionEvent;
public class SwipeGestureDetector implements GestureDetector.OnGestureListener {


    private final int MIN_X_SWIPE_DISTANCE = 180;
    private final int MIN_Y_SWIPE_DISTANCE = 180;

    private SwipeActions swipeActions;


    public SwipeGestureDetector(SwipeActions actions) {
        swipeActions = actions;
    }

    @Override
    public boolean onDown(MotionEvent e) { return false; }
    @Override
    public void onShowPress(MotionEvent e) { }
    @Override
    public boolean onSingleTapUp(MotionEvent e) { return false; }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { return false; }
    @Override
    public void onLongPress(MotionEvent e) { }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


        //TypeCast the difference of co-ordinates to int and store in another variable

        int distanceSwipedInX = (int) (e1.getX() - e2.getX());
        int distanceSwipedInY = (int) (e1.getY() - e2.getY());

        // Make Check For Horizontal Swipe

        if (Math.abs(distanceSwipedInX) > MIN_X_SWIPE_DISTANCE) {

            // Now Check Which Side Swipe Happened

            if (distanceSwipedInX > 0) {
                swipeActions.onSwipeLeft();
            } else {
                swipeActions.onSwipeRight();
            }
            return true;
        }

        // Make Check For Horizontal Swipe

        if (Math.abs(distanceSwipedInY) > MIN_Y_SWIPE_DISTANCE) {

            // Now Check Which Side Swipe Happened

            if (distanceSwipedInY > 0) {
                swipeActions.onSwipeUp();
            } else {
                swipeActions.onSwipeDown();
            }
        }
        return false;
    }




}
