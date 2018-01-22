package com.samnie.beautypic.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.samnie.beautypic.R;

import java.util.ArrayList;

import static com.samnie.beautypic.test.MyViewGroup.getMotionType;

public class Main2Activity extends Activity implements View.OnTouchListener, View.OnClickListener {
    private static final String TAG = "sam_Main2Activity";
    View myview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_container_main_fragment);
        initView();

//        setContentView(R.layout.activity_main2);
//        myview = findViewById(R.id.myview);
//        myview.setOnTouchListener(this); //decorview can dispatch touchevent
//        myview.setOnClickListener(this);
    }

    private void initView() {
        int NUM = 100;
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<NUM;i++){
            list.add("item" + i);
        }

        ListView listView = findViewById(R.id.pager);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e(TAG, "onTouch " + getMotionType(event));
        return false;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.e(TAG, "                                  ");
//        Log.e(TAG, "dispatchTouchEvent " + getMotionType(ev));
//        return super.dispatchTouchEvent(ev);
//
////        return false;
////        return true;
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        Log.e(TAG, "onTouchEvent " + getMotionType(ev));
//        return super.onTouchEvent(ev);
//    }
}
