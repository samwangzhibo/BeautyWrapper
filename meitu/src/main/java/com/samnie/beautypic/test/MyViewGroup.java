package com.samnie.beautypic.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by samwangzhibo on 2017/12/25.
 */

public class MyViewGroup extends ViewGroup {
    public static final String TAG = "sam_MyViewGroup";
    float firstY;

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure " + widthMeasureSpec + " , " + heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG, "onLayout " + l + " " + t + " " + r + " " + b + " " + changed);
        if (changed)
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.layout(l, t, l + child.getMeasuredWidth(), t + child.getMeasuredHeight());
                Log.e(TAG, "layoutChild: " + child.getMeasuredWidth() + "," + child.getMeasuredHeight());
            }
    }

    public static String getMotionType(MotionEvent ev) {
        String motionType = "";
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                motionType = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                motionType = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                motionType = "ACTION_UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                motionType = "ACTION_CANCEL";
                break;
        }
        return motionType;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String motionType = getMotionType(ev);
        Log.e(TAG, "dispatchTouchEvent " + " , " + motionType);
        boolean result = super.dispatchTouchEvent(ev);
        return result;

//        return true;
//        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        String motionType = getMotionType(ev);
        Log.e(TAG, "onInterceptTouchEvent " + " , " + motionType );

        float nowY;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                nowY = ev.getRawY();
                if (-(nowY - firstY) > 100){  //往上
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

//        if(ev.getAction()==MotionEvent.ACTION_MOVE)
//            return true;
//        boolean result = super.onInterceptTouchEvent(ev);
//        return result;

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String motionType = getMotionType(event);
        Log.e(TAG, "onTouchEvent " + " , " + motionType);
//        boolean result = super.onTouchEvent(event);
//        return result;

//        return event.getAction() == MotionEvent.ACTION_MOVE;
        return false;
//        return true;
    }
}
