package com.lnyp.sexybeach.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.lnyp.sexybeach.R;
import com.lnyp.sexybeach.adapter.ProjectPagerAdapter;
import com.lnyp.sexybeach.entry.BeautyItemList;
import com.lnyp.sexybeach.net.Net;
import com.victor.loading.rotate.RotateLoading;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    public static String ITEM_URL = "https://route.showapi.com/852-1?showapi_appid=48695&showapi_sign=2b0649a68d364e8b87e0309b10095237";
    public static String ITEM_NAME = "美女图片";

    @Bind(R.id.tabPageProjects)
    public TabPageIndicator tabPageProjects;
    @Bind(R.id.viewPagerProjects)
    public ViewPager viewPagerProjects;
    @Bind(R.id.rotateLoading)
    public RotateLoading rotateLoading;

    ProjectPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mAdapter = new ProjectPagerAdapter(getSupportFragmentManager());
        viewPagerProjects.setOffscreenPageLimit(1);
        viewPagerProjects.setAdapter(mAdapter);
        tabPageProjects.setViewPager(viewPagerProjects);
        getItem();
    }

    private void getItem() {
        rotateLoading.start();
        Net.post(mHandler, ITEM_URL, BeautyItemList.class, new Net.INetCallBack<BeautyItemList>() {
            @Override
            public void onResponse(BeautyItemList beautyItemList) {
                for (BeautyItemList.BeautyItem beautyItem : beautyItemList.getList()){
                    if (ITEM_NAME.equals(beautyItem.getName())){
                        rotateLoading.stop();
                        mAdapter.setData(beautyItem.getList());
                        tabPageProjects.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }
}
