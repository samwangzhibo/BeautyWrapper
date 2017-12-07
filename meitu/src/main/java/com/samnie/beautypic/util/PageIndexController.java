package com.samnie.beautypic.util;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import static com.samnie.beautypic.Constant.isOpenPageIndexRecord;

/**
 * Created by samwangzhibo on 2017/12/6.
 */

public class PageIndexController {
    /**
     *   4001,1 4002,2
     */
    public static HashMap<Integer, Integer> pageIndexs;

    public static String SPACE = " ";
    public static String SPLIT = ",";

    public static void init(){
        if (!isOpenPageIndexRecord) return;
        String sourse = PreferenceUtils.getString(PageIndexPreference.KEY_PAGEINDEX);
        pageIndexs = parseSourseString(sourse);
        Log.e("wzb", "PageIndexController.init" + sourse);
    }

    private static HashMap<Integer, Integer> parseSourseString(String sourse) {
        HashMap<Integer, Integer> pageIndexs = new HashMap<>();
        if (!TextUtils.isEmpty(sourse)){
            for (String indexEntry : sourse.split(SPACE)){
               String[] kv = indexEntry.split(SPLIT);
               if (kv != null && kv.length == 2){
                   try {
                       pageIndexs.put(Integer.parseInt(kv[0]), Integer.parseInt(kv[1]));
                   }catch (Exception e){
                   }
               }
            }
        }
        return pageIndexs;
    }

    private static String parseSourseString(HashMap<Integer, Integer> pageIndexs) {
        StringBuilder sourseString = new StringBuilder();
        for (Map.Entry entry : pageIndexs.entrySet()){
            sourseString.append(entry.getKey());
            sourseString.append(SPLIT);
            sourseString.append(entry.getValue());
            sourseString.append(SPACE);
        }
        if (sourseString.indexOf(SPACE) != -1)
            sourseString.deleteCharAt(sourseString.lastIndexOf(SPACE));
        return sourseString.toString();
    }

    public static void updatePageIndex(int id, int pageIndex){
        if (!isOpenPageIndexRecord) return;
        pageIndexs.put(id, pageIndex);
        Log.e("wzb", "PageIndexController.updatePageIndex " + " id= " + id + " pageIndex= " + pageIndex);
    }

    public static int getPageIndex(int id){
        if (!isOpenPageIndexRecord) return 1;
        int result = 1;
        try {
            result = pageIndexs.get(id) == null ? 1 : pageIndexs.get(id);
        }catch (Exception e){

        }
        return result;
    }

    public static void savePageIndex2Path(){
        if (!isOpenPageIndexRecord) return;
        String result = parseSourseString(pageIndexs);
        if (!TextUtils.isEmpty(result))
            PreferenceUtils.setString(PageIndexPreference.KEY_PAGEINDEX, result);
        Log.e("wzb", "PageIndexController.savePageIndex2Path " + result);
    }
}
