package com.samnie.beautypic.test;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static com.samnie.beautypic.test.MyViewGroup.getMotionType;

public class MyView extends View {
    private static final String TAG = "sam_MyView";

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure " + widthMeasureSpec + " " + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e(TAG, "onLayout " + left + " " + top + " " + right + " " + bottom + " " + changed);
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String motionType = getMotionType(ev);
        Log.e(TAG, "dispatchTouchEvent " + " , " + motionType );
        boolean result = super.dispatchTouchEvent(ev);
        return false;
        //        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        String motionType = getMotionType(ev);
        Log.e(TAG, "onTouchEvent " + " , " + motionType );
//        boolean result = super.onTouchEvent(ev);
//        return false;
                return false;
    }
}
