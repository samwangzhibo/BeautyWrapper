package com.samnie.beautypic.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.lnyp.flexibledivider.GridSpacingItemDecoration;
import com.lnyp.recyclerview.EndlessRecyclerOnScrollListener;
import com.lnyp.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.lnyp.recyclerview.NullItemAnimator;
import com.lnyp.recyclerview.RecyclerViewLoadingFooter;
import com.lnyp.recyclerview.RecyclerViewStateUtils;
import com.samnie.beautypic.R;
import com.samnie.beautypic.activity.BeautyDetailActivity;
import com.samnie.beautypic.activity.MainActivity;
import com.samnie.beautypic.adapter.BeautyListAdapter;
import com.samnie.beautypic.entry.BeautyList;
import com.samnie.beautypic.entry.BeautySimple;
import com.samnie.beautypic.net.Net;
import com.samnie.beautypic.util.PageIndexController;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 美女列表
 */
public class BeautyListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 20;

    @BindView(R.id.rotateLoading)
    public RotateLoading rotateLoading;

    @BindView(R.id.listViewBeauties)
    public RecyclerView  listViewBeauties;

    @BindView(R.id.refreshLayout)
    public SwipeRefreshLayout refreshLayout;

    private HeaderAndFooterRecyclerViewAdapter mAdapter;

    private List<BeautySimple> mDatas;

    private int page = 1;

    private int initPage = 1;

    private int id;

    private boolean hasMore = false;

    private boolean isRefresh = false;

    private boolean isFirstLoad = false;

    protected MainActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            id = getArguments().getInt("id");
        initPage = PageIndexController.getPageIndex(id); //如果没有开启，默认每次都为1
        page = initPage;
        Log.e("wzb", " id =  " + id + " , page = " + page);
    }

    protected void initView() {
        ButterKnife.bind(this, mRootView);

        refreshLayout.setOnRefreshListener(this);
        listViewBeauties.setHasFixedSize(true); // 设置固定大小
        mDatas = new ArrayList<>();
        listViewBeauties.setItemAnimator(new NullItemAnimator());
        BeautyListAdapter beautyListAdapter = new BeautyListAdapter(this, mDatas, onItemClick);
        mAdapter = new HeaderAndFooterRecyclerViewAdapter(beautyListAdapter);
        listViewBeauties.setAdapter(mAdapter);
//        ((SimpleItemAnimator)listViewBeauties.getItemAnimator()).setSupportsChangeAnimations(false);
      /*  GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup(listViewBeauties.getAdapter(), gridLayoutManager.getSpanCount()));*/
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        listViewBeauties.setLayoutManager(staggeredGridLayoutManager);

        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration.Builder(getActivity(), staggeredGridLayoutManager.getSpanCount())
                .setH_spacing(1)
                .setV_spacing(1)
                .setDividerColor(Color.parseColor("#FFFFFF"))
                .build();

        listViewBeauties.addItemDecoration(itemDecoration);

        listViewBeauties.addOnScrollListener(mOnScrollListener);
    }

    @Override
    int getMainLayout() {
        return R.layout.fragment_beauty_list;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mAdapter != null && mAdapter.getInnerAdapter().getItemCount() == 0){
            Log.e("wzb", toString() + " isVisibleToUser : " + isVisibleToUser + " mAdapter : " + mAdapter);
            startLoadData();
            isFirstLoad = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad){
            startLoadData();
            isFirstLoad = true;
        }
    }

    private void startLoadData() {
        rotateLoading.start();
        onRefresh();
    }


    /**
     * 获取列表
     */
    private void getBeauties() {
        if (isRefresh){
            page = initPage;
        }else {
            page++;
        }
        Log.e("wzb", "start load data : " + " page= " + page);
        Net.getData(mHandler, "http://route.showapi.com/852-2", BeautyList.class, new Net.INetCallBack<BeautyList>() {
            @Override
            public void onResponse(BeautyList beautyList) {
                ArrayList<BeautyList.PageBean.Content> contentlist = beautyList.getPagebean().contentlist;
                if (contentlist != null) {
                    List<BeautySimple> tngous = new ArrayList<>();
                    for (BeautyList.PageBean.Content content : contentlist){
                        BeautySimple beautySimple = new BeautySimple();
                        beautySimple.setPic(content.list.get(0));
                        beautySimple.setTitle(content.title);
                        tngous.add(beautySimple);
                    }
                    if (tngous != null) {
                        if (isRefresh) {
                            mDatas.clear();

                            isRefresh = false;
                        }else {
                            if (tngous.size() > 0)
                                PageIndexController.updatePageIndex(id, page); //有返回并且在下拉时
                        }

                        mDatas.addAll(tngous);

                        if (tngous.size() > 0) {
                            hasMore = true;
                        } else {
                            hasMore = false;
                        }
                    }
                }
                    doSuccess();
            }

            @Override
            public void onError() {
                doFaiture();
            }
        }, "type", id+"", "page", page+"");
    }

    Handler mHandler = new Handler(Looper.getMainLooper());
    private void doSuccess() {
        updateData();
        RecyclerViewStateUtils.setFooterViewState(listViewBeauties, RecyclerViewLoadingFooter.State.Normal);
        rotateLoading.stop();
        refreshLayout.setRefreshing(false);
    }

    private void doFaiture() {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), listViewBeauties, REQUEST_COUNT, RecyclerViewLoadingFooter.State.NetWorkError, mFooterClick);
        rotateLoading.stop();
        refreshLayout.setRefreshing(false);
    }

    private void updateData() {
        if (mAdapter != null) {
//            mAdapter.notifyDataSetChanged();
            mAdapter.notifyDataChanged();
        }
    }


    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            RecyclerViewLoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(listViewBeauties);

            if (state == RecyclerViewLoadingFooter.State.Loading) {
                return;
            }

            if (hasMore) {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), listViewBeauties, REQUEST_COUNT, RecyclerViewLoadingFooter.State.Loading, null);
                getBeauties();
            } else {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), listViewBeauties, REQUEST_COUNT, RecyclerViewLoadingFooter.State.TheEnd, null);
            }
        }
    };

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), listViewBeauties, REQUEST_COUNT, RecyclerViewLoadingFooter.State.Loading, null);
            getBeauties();
        }
    };

    private View.OnClickListener onItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                int pos = (int) v.getTag();
                BeautySimple beautySimple = mDatas.get(pos);
                if (beautySimple != null) {
                    startActivity(BeautyDetailActivity.createIntent(getActivity(), beautySimple));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mRootView) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
//        page = initPage;
        getBeauties();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
