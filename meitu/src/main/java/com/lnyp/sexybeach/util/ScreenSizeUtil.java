package com.lnyp.sexybeach.util;

import android.app.Activity;

import com.lnyp.sexybeach.MyApp;

/**
 * Created by 李宁 on 2016-08-21.
 */
public class ScreenSizeUtil {

    public static int getScreenWidth(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static int dp2px(float dp) {
        final float scale = MyApp.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
