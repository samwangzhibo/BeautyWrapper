package com.lnyp.sexybeach.activity;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.lnyp.sexybeach.R;
import com.lnyp.sexybeach.common.Const;
import com.lnyp.sexybeach.entry.BeautySimple;
import com.lnyp.sexybeach.widget.ShowMaxImageView;
import com.victor.loading.rotate.RotateLoading;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 美女图片详情
 */
public class BeautyDetailActivity extends BaseActivity {

    private static final String TAG = "wzb";
    @Bind(R.id.rotateLoading)
    public RotateLoading rotateLoading;

    @Bind(R.id.scrollContent)
    public ScrollView scrollContent;

    @Bind(R.id.imgCover)
    public ShowMaxImageView imgCover;

    @Bind(R.id.layoutTitleBar)
    public RelativeLayout titleBar;

    @Bind(R.id.textCount)
    public TextView textCount;

    @Bind(R.id.textTitle)
    public TextView textTitle;

    @Bind(R.id.tv_set_wallpaper)
    public TextView setWallpaperTv;

    @Bind(R.id.tv_download)
    public TextView downloadTv;

    BeautySimple beautySimple;

    public static Intent createIntent(Context context, BeautySimple beautySimple){
        Intent intent = new Intent(context, BeautyDetailActivity.class);
        intent.putExtra("beautySimple", beautySimple);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_detail);

        ButterKnife.bind(this);
        initListenr();
        scrollContent.setVisibility(View.INVISIBLE);

        beautySimple = (BeautySimple) getIntent().getSerializableExtra("beautySimple");

        rotateLoading.start();
        updateData();
//        getBeautyDetail(beautySimple.getId());
    }

    private void initListenr() {
        imgCover.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                toggleVisible(titleBar);
            }
        });
    }

   /* private void getBeautyDetail(int id) {


        OkHttpClient client = new OkHttpClient();

        String url = "http://www.tngou.net/tnfs/api/show?" + "id=" + id;
        LogUtils.e(url);
        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                BeautyDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BeautyDetailActivity.this, "网络错误", Toast.LENGTH_SHORT).show();

                        rotateLoading.stop();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();

                beautyDetail = FastJsonUtil.json2T(result, BeautyDetail.class);
                if (beautyDetail != null) {
                    BeautyDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateData();
                        }
                    });
                }
            }
        });

    }*/

    /**
     * 更新页面UI
     */
    private void updateData() {

        String imgUrl = Const.BASE_IMG_URL2 + beautySimple.getPic().big;

        Glide.with(this)
                .load(imgUrl)
//                .override(720, 1280)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(false)
                .into(new GlideDrawableImageViewTarget(imgCover) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        //在这里添加一些图片加载完成的操作

//                        textCount.setText("共有" + beautyDetail.getSize() + "张");

                        textTitle.setText(beautySimple.getTitle());

                        scrollContent.setVisibility(View.VISIBLE);

                        rotateLoading.stop();
                    }
                });

    }

    private void toggleVisible(View view){
        view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick({R.id.layoutImgs, R.id.imgBack, R.id.imgShare, R.id.tv_set_wallpaper, R.id.tv_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_download:
                break;
            case R.id.tv_set_wallpaper:
                //设置壁纸
                try {
                    WallpaperManager wpm = (WallpaperManager) getSystemService(
                            Context.WALLPAPER_SERVICE);
                    if (wpm != null) {
                        Bitmap bm =((GlideBitmapDrawable) (imgCover).getDrawable()).getBitmap();
                        wpm.setBitmap(bm);
                        Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
                        Log.i("wzb", "wallpaper not null");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed to set wallpaper: " + e);
                    Toast.makeText(this, "设置失败，请重试哦，亲", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layoutImgs:
              /*  if (beautyDetail != null) {
                    Intent intent = new Intent(this, ImageBrowseActivity.class);
                    intent.putParcelableArrayListExtra("imgs", beautyDetail.getList());
                    startActivity(intent);
                }*/
                break;
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgShare:

               /* WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = beautyDetail.getImg();
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = beautyDetail.getTitle();
                msg.description = beautyDetail.getTitle();
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                msg.thumbData = Util.bmpToByteArray(thumb, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                MyApp.api.sendReq(req);*/
                break;
        }
    }
}
