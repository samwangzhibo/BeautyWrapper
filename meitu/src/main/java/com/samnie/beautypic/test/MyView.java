package com.samnie.beautypic.test;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static com.samnie.beautypic.test.MyViewGroup.getMotionType;

/**
 * Created by zybang on 2017/12/25.
 */

public class MyView extends View {
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("wzb", "MyView : onLayout " + left + " " + top + " " + right + " " +
                bottom + " " + changed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String motionType = getMotionType(ev);
        Log.e("wzb", "MyView : dispatchTouchEvent " + " , " + motionType);
        boolean result = super.dispatchTouchEvent(ev);
        return result;
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        String motionType = getMotionType(ev);
        Log.e("wzb", "MyView : onTouchEvent " + " , " + motionType);
//        boolean result = super.onTouchEvent(ev);
        return false;
//        return true;
//        return result;
    }
}
