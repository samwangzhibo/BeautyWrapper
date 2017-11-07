package com.lnyp.sexybeach.net;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lnyp.sexybeach.Constant;
import com.lnyp.sexybeach.util.FastJsonUtil;
import com.show.api.ShowApiRequest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by samwangzhibo on 2017/10/26.
 */

public class Net {

    public static <T> void post(final Handler mHandler, final String url, final Class<T> tClass, final INetCallBack<T> callBack){
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if (callBack != null) callBack.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e("wzb", url +  result);
                doCallBack(mHandler, result, tClass, callBack);
            }
        });
    }
    public static <T> void doCallBack(final Handler mHandler, String result, final Class<T> tClass, final INetCallBack<T> callBack){
        if (!TextUtils.isEmpty(result)){
            JSONObject jsonObject = JSON.parseObject(result);
            int resCode = jsonObject.getIntValue("showapi_res_code");
            JSONObject resultContent = jsonObject.getJSONObject("showapi_res_body");
            if (resCode == 0) {
                T rspBeautySimple = null;
                try {
                    rspBeautySimple = FastJsonUtil.json2T(resultContent.toString(), tClass);
                    if (callBack != null) {
                        if (mHandler != null) {
                            final T finalRspBeautySimple = rspBeautySimple;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onResponse(finalRspBeautySimple);
                                }
                            });
                        }
                        else
                            callBack.onResponse(rspBeautySimple);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            if (callBack != null) {
                callBack.onError();
            }else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError();
                    }
                });
            }
        }
    }
    public static <T> void getData(final Handler mHandler, final String url, final Class<T> tClass, final INetCallBack<T> callBack, final String... params){
        new Thread(){
            //在新线程中发送网络请求
            public void run() {
                ShowApiRequest showApiRequest = new ShowApiRequest(url, Constant.APP_ID, Constant.SIGN_KEY);
                for (int i = 0; i < params.length/2; i++){
                    showApiRequest
                            .addTextPara(params[2 * i], params[2 * i + 1]);
                }
                final String res= showApiRequest.post();
                Log.e("wzb", url + res);
                //把返回内容通过handler对象更新到界面
                doCallBack(mHandler, res, tClass, callBack);
            }
        }.start();
    }

    public interface INetCallBack<T>{
        void onResponse(T t);
        void onError();
    }
}
