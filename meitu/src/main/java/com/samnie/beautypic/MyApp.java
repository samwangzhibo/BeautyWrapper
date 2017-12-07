package com.samnie.beautypic;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.apkfuns.logutils.LogUtils;
import com.samnie.beautypic.util.PageIndexController;
import com.tencent.mm.sdk.openapi.IWXAPI;

public class MyApp extends Application {
    private static int versionCode;
    private static String versionName = "";
    private static boolean isReleased = true;

    // IWXAPI 是第三方app和微信通信的openapi接口
    public static IWXAPI api;
    static Application CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();

//        // 通过WXAPIFactory工厂，获取IWXAPI的实例
//        api = WXAPIFactory.createWXAPI(this, Const.APP_ID, true);
//        // 将该app注册到微信
//        api.registerApp(Const.APP_ID);

        LogUtils.configAllowLog = true;
        LogUtils.configTagPrefix = "beautywallpaper-";
        initAppInfo();
        PageIndexController.init();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        CONTEXT = this;
    }

    public static Context getApplication() {
        return CONTEXT;
    }

    /**
     * 初始化安装包相关信息，codeName,versionName,isReleased,cuid
     */
    private void initAppInfo() {
        try {
            PackageManager packageManager = CONTEXT.getPackageManager();
            //读取versionCode和versionName
            PackageInfo packageInfo = packageManager.getPackageInfo(CONTEXT.getPackageName(), PackageManager.GET_CONFIGURATIONS | PackageManager.GET_SIGNATURES);
            versionCode = packageInfo.versionCode;
            versionName = packageInfo.versionName;
            ApplicationInfo appinfo = packageManager.getApplicationInfo(CONTEXT.getPackageName(), 0);
            isReleased = (0 == (appinfo.flags &= ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (Throwable e) {
        }
    }

    public static int getVersionCode() {
        return versionCode;
    }

    public static String getVersionName() {
        return versionName;
    }

}
