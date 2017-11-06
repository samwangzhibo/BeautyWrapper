package com.lnyp.sexybeach.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.lnyp.sexybeach.R;
import com.lnyp.sexybeach.adapter.ProjectPagerAdapter;
import com.lnyp.sexybeach.entry.BeautyItemList;
import com.lnyp.sexybeach.net.Net;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends BaseActivity {
    public static String ITEM_URL = "https://route.showapi.com/852-1?showapi_appid=48695&showapi_sign=2b0649a68d364e8b87e0309b10095237";

    @Bind(R.id.tabPageProjects)
    public TabPageIndicator tabPageProjects;

    @Bind(R.id.viewPagerProjects)
    public ViewPager viewPagerProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getItem();
        ProjectPagerAdapter mAdapter = new ProjectPagerAdapter(getSupportFragmentManager());

        viewPagerProjects.setOffscreenPageLimit(1);

        viewPagerProjects.setAdapter(mAdapter);

        tabPageProjects.setViewPager(viewPagerProjects);
    }

    private void getItem() {
        Net.post(ITEM_URL, BeautyItemList.class, new Net.INetCallBack<BeautyItemList>() {
            @Override
            public void onResponse(BeautyItemList beautyItemList) {
                Log.e("wzb", beautyItemList.toString());
            }

            @Override
            public void onError() {

            }
        });
    }
}
