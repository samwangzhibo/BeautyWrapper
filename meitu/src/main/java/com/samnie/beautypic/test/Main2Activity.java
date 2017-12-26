package com.samnie.beautypic.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.samnie.beautypic.R;

import static com.samnie.beautypic.test.MyViewGroup.getMotionType;

public class Main2Activity extends Activity implements View.OnTouchListener, View.OnClickListener
{
    View myview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myview = findViewById(R.id.myview);
        myview.setOnTouchListener(this);
//        myview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e("wzb", "MyView : onClick");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("wzb", "MyView : onTouch " + getMotionType(event));
        return false;
    }
}
