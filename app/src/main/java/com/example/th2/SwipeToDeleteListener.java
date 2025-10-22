package com.example.th2;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public abstract class SwipeToDeleteListener implements View.OnTouchListener {
    private ListView listView;
    private float startX;
    private float startY;
    private boolean isSwiping = false;
    private static final int SWIPE_THRESHOLD = 200; // Khoảng cách tối thiểu để xác định là swipe
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    public SwipeToDeleteListener(ListView listView) {
        this.listView = listView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                isSwiping = false;
                return false;

            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getX() - startX;
                float deltaY = event.getY() - startY;
                
                // Kiểm tra nếu là swipe ngang (không phải scroll dọc)
                if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > 20) {
                    isSwiping = true;
                    return true; // Chặn scroll
                }
                return false;

            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float endY = event.getY();
                float totalDeltaX = endX - startX;
                float totalDeltaY = endY - startY;

                // Kiểm tra swipe sang trái
                if (isSwiping && totalDeltaX < -SWIPE_THRESHOLD && Math.abs(totalDeltaY) < 100) {
                    int position = listView.pointToPosition((int) startX, (int) startY);
                    if (position != ListView.INVALID_POSITION) {
                        onSwipeLeft(position);
                        return true;
                    }
                }
                
                isSwiping = false;
                return false;

            default:
                return false;
        }
    }

    public abstract void onSwipeLeft(int position);
}

