package com.samnie.beautypic.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.samnie.beautypic.R;
import com.samnie.beautypic.adapter.ProjectPagerAdapter;
import com.samnie.beautypic.entry.BeautyItemList;
import com.samnie.beautypic.net.Net;
import com.samnie.beautypic.util.PageIndexController;
import com.victor.loading.rotate.RotateLoading;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    public static String ITEM_URL = "https://route.showapi.com/852-1?showapi_appid=48695&showapi_sign=2b0649a68d364e8b87e0309b10095237";
    public static String ITEM_NAME = "美女图片";

    @BindView(R.id.tabPageProjects)
    public TabPageIndicator tabPageProjects;
    @BindView(R.id.viewPagerProjects)
    public ViewPager viewPagerProjects;
    @BindView(R.id.rotateLoading)
    public RotateLoading rotateLoading;

    long lastClickTime = 0L;
    int CLICK_DURATION = 1500;

    ProjectPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initView();
        mAdapter = new ProjectPagerAdapter(getSupportFragmentManager());
        viewPagerProjects.setOffscreenPageLimit(1);
        viewPagerProjects.setAdapter(mAdapter);
        tabPageProjects.setViewPager(viewPagerProjects);
        getItem();
    }

    private void initView() {
        findViewById(R.id.btn_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AboutActivity.createIntent(MainActivity.this));
            }
        });
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

    @Override
    public void finish() {
        super.finish();
        PageIndexController.savePageIndex2Path();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastClickTime > CLICK_DURATION){
            Toast.makeText(this, "再点击一下就退出哦，亲", Toast.LENGTH_SHORT).show();
        }else {
            super.onBackPressed();
        }
        lastClickTime = System.currentTimeMillis();
    }
}
