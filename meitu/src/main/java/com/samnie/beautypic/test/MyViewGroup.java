package com.samnie.beautypic.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zybang on 2017/12/25.
 */

public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("wzb", "MyViewGroup : onLayout " + l + " " + t + " " + r + " " +
                b + " " + changed);
        for (int i = 0; i < getChildCount(); i++ ){
            View child = getChildAt(i);
            child.layout(l, t, l + child.getMeasuredWidth(), t + child.getMeasuredHeight());
            Log.e("wzb", "MyViewGroup  measureChild: " + child.getMeasuredWidth() +
                    "," + child.getMeasuredHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        Log.e("MyViewGroup", "MyViewGroup : onTouchEvent " + result);
        return result;
    }
}
