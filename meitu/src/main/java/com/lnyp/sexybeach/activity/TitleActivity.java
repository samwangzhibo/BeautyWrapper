package com.lnyp.sexybeach.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lnyp.sexybeach.R;
import com.lnyp.sexybeach.util.ScreenSizeUtil;

/**
 * 基类
 *
 * @author lining
 */
public class TitleActivity extends AppCompatActivity {
    protected FrameLayout rootView;
    private View mContentView;
    private View titleView;

    private boolean hideTitle = false;

    @Override
    public void setContentView(int layoutResID) {
        FrameLayout frameLayout = new FrameLayout(this);
        rootView = frameLayout;
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        super.setContentView(frameLayout);
        if (!hideTitle) {
            int resId = R.layout.common_title_bar_old;
            LayoutInflater.from(this).inflate(resId, rootView);
        }
        mContentView = LayoutInflater.from(this).inflate(layoutResID, null);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.BOTTOM);
        int marginTop = hideTitle ? 0 : ScreenSizeUtil.dp2px(48);
        lp.setMargins(0, marginTop, 0, 0);
        rootView.addView(mContentView, lp);
        titleView = findViewById(R.id.title_bar);
    }

    public void setTitleLineVisible(boolean visible){
        findViewById(R.id.title_line).setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setTitleText(String title) {
        TextView textView = ((TextView) findViewById(R.id.title_name));
        textView.setText(title);
        textView.setVisibility(View.VISIBLE);
    }

    protected void setTitleVisible(boolean b) {
        RelativeLayout titleBar = (RelativeLayout) findViewById(R.id.title_bar);

        if (null != titleBar) {
            int marginTop = b ? ScreenSizeUtil.dp2px(48) : 0;
            titleBar.setVisibility(b ? View.VISIBLE : View.GONE);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.BOTTOM);
            lp.setMargins(0, marginTop, 0, 0);
            mContentView.setLayoutParams(lp);
        }

        if (null != titleBar) titleBar.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    /**
     * override to handle left button clicked event
     * default action is finish
     *
     * @param v
     */
    public void onLeftButtonClicked(View v) {
        finish();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
