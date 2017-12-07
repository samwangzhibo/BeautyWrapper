package com.samnie.beautypic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基类
 *
 * @author lining
 */
public abstract class BaseFragment extends Fragment {
    Handler mHandler;
    public View mRootView;
    private boolean mInited = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mRootView != null && !isDetached() && mInited) {
            if (mRootView.getParent() != null) {
                ViewGroup parent = (ViewGroup) mRootView.getParent();
                parent.removeView(mRootView);
            }
            return mRootView;
        }
        mRootView = inflater.inflate(getMainLayout(), container, false);
        initView();
        mInited = true;
        return mRootView;
    }

    protected abstract void initView();

    abstract int getMainLayout();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
