package com.lnyp.sexybeach.activity;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.lnyp.sexybeach.util.GrantUtil;
import com.lnyp.sexybeach.util.ImgUtils;
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

/*    @Bind(R.id.scrollContent)
    public ScrollView scrollContent;*/

    @Bind(R.id.fy_top)
    public FrameLayout topFy;

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

    @Bind(R.id.ll_menu)
    public LinearLayout menuLl;

    BeautySimple beautySimple;
    GrantUtil grantUtil = new GrantUtil();

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
        topFy.setVisibility(View.INVISIBLE);

        beautySimple = (BeautySimple) getIntent().getSerializableExtra("beautySimple");

        rotateLoading.start();
        updateData();
//        getBeautyDetail(beautySimple.getId());
    }

    private void initListenr() {
        imgCover.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

            @Override
            public void onViewTap(View view, float x, float y) {
                toggleVisibleAllView();
            }
        });
    }

    private void toggleVisibleAllView() {
        toggleVisible(titleBar);
        toggleVisible(menuLl);
    }

    /**
     * 更新页面UI
     */
    private void updateData() {
        textTitle.setText(beautySimple.getTitle());

        String imgUrl = Const.BASE_IMG_URL2 + beautySimple.getPic().big;
        Log.e("wzb", "big img load : " + imgUrl);

        Glide.with(this)
                .load(imgUrl)
//                .override(720, 1280)
                .override(1080, 1920)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(new GlideDrawableImageViewTarget(imgCover) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        //在这里添加一些图片加载完成的操作
                        Log.e("wzb", "big img load success");
//                        textCount.setText("共有" + beautyDetail.getSize() + "张");

                        topFy.setVisibility(View.VISIBLE);

                        rotateLoading.stop();
                    }
                });

    }

    private void toggleVisible(View view){
        view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == grantUtil.REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                savePic();
            } else {
                Toast.makeText(this, "只有允许了我才能写入图片操作哦~~", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    private void savePic() {
        try {
            Bitmap bm2 =((GlideBitmapDrawable) (imgCover).getDrawable()).getBitmap();
            ImgUtils.saveImageToGallery(this, bm2, beautySimple.getTitle());
        }catch (Exception e){
            Toast.makeText(this, "下载失败，请重试哦，亲", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.layoutImgs, R.id.imgBack, R.id.imgShare, R.id.tv_set_wallpaper, R.id.tv_download, R.id.fy_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fy_top:
                toggleVisibleAllView();
                break;
            case R.id.tv_download:
                if (grantUtil.isStoragePermissionGranted(this)) {
                    savePic();
                } else {
                    grantUtil.requestStoragePermissions(this);
                }
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
