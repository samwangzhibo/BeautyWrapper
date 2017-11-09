package com.lnyp.sexybeach.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.lnyp.sexybeach.entry.BeautyItemList;
import com.lnyp.sexybeach.fragment.BeautyListFragment;

import java.util.ArrayList;

public class ProjectPagerAdapter extends FragmentPagerAdapter {
    public final String DETALUT_ITEM_NAME = "默认";
    private SparseArray<Fragment> mFragments = new SparseArray<>();

    private ArrayList<BeautyItemList.BeautyItem.BeautyItemEntry> list = new ArrayList<>();

    public ProjectPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(ArrayList<BeautyItemList.BeautyItem.BeautyItemEntry> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments != null && mFragments.get(position) != null) {
            return mFragments.get(position);
        } else {
            Fragment fragment = new BeautyListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", list.get(position).getId());
            fragment.setArguments(bundle);
            mFragments.put(position, fragment);
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position < list.size() ? list.get(position).getName() : DETALUT_ITEM_NAME;
    }
}
