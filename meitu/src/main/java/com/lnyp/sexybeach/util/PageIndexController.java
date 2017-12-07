package com.lnyp.sexybeach.util;

import android.util.Log;
import android.util.SparseArray;

import java.util.HashMap;

/**
 * Created by samwangzhibo on 2017/12/6.
 */

public class PageIndexController {
    public static HashMap<Integer, Integer> pageIndexs;

    public static void init(){
        pageIndexs = PreferenceUtils.getObject(PageIndexPreference.KEY_PAGEINDEX, HashMap.class);
        if (pageIndexs == null){
            pageIndexs = new HashMap<>();
        }
        Log.e("wzb", "PageIndexController.init" + pageIndexs);
    }

    public static void updatePageIndex(int id, int pageIndex){
        pageIndexs.put(id, pageIndex);
        Log.e("wzb", "PageIndexController.updatePageIndex " + " id= " + id + " pageIndex= " + pageIndex);
    }

    public static int getPageIndex(int id){
        int result = 1;
        try {
            result = pageIndexs.get(id) == null ? 1 : pageIndexs.get(id);
        }catch (Exception e){

        }
        return result;
    }

    public static void savePageIndex2Path(){
        PreferenceUtils.setObject(PageIndexPreference.KEY_PAGEINDEX, pageIndexs);
        Log.e("wzb", "PageIndexController.savePageIndex2Path " + pageIndexs);
    }
}
