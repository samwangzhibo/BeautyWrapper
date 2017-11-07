package com.lnyp.sexybeach.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lnyp.sexybeach.entry.BeautyItemList;
import com.lnyp.sexybeach.fragment.FragmentBeautyList;

import java.util.ArrayList;

public class ProjectPagerAdapter extends FragmentPagerAdapter {
    public final String DETALUT_ITEM_NAME = "默认";
    private ArrayList<BeautyItemList.BeautyItem.BeautyItemEntry> list = new ArrayList<>();
    public ProjectPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setData(ArrayList<BeautyItemList.BeautyItem.BeautyItemEntry> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle bundle;
        fragment = new FragmentBeautyList();
        bundle = new Bundle();
        bundle.putInt("id", list.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
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
