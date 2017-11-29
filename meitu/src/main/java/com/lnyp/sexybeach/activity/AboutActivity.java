package com.lnyp.sexybeach.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lnyp.sexybeach.MyApp;
import com.lnyp.sexybeach.R;

public class AboutActivity extends TitleActivity {

    public static Intent createIntent(Context context){
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ((TextView)findViewById(R.id.tv_vc_code)).setText("版本号："+ MyApp.getVersionName());
        setTitleLineVisible(false);
    }
}
