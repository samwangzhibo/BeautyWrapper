package com.samnie.beautypic.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by samwangzhibo on 2017/12/6.
 */

public class ImgUtils {

    public static final String APP_PATH = "meinvtupian";
    public static final String IMG_PATH = "img";
    public static final String SCREENSHOTS_DIR_NAME =
//            "Meinv"; //会放在相册中的其他目录
            "Screenshots"; //会放入相册的截屏目录中

    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp, String descrip) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), SCREENSHOTS_DIR_NAME);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = descrip + ".jpg";
        File file = new File(appDir, fileName);
        if (file.exists()){
            Toast.makeText(context, "该图片已保存", Toast.LENGTH_SHORT).show();
            return true;
        }
        boolean isSuccess = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, descrip);

            //保存图片后发送广播通知更新数据库
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
                MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.e( "onScanCompleted: ", path);
                        Log.e( "onScanCompleted: ", uri.toString());
                    }
                });
            }else {
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
            return isSuccess;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Toast.makeText(context, isSuccess ? "已保存相册" : "下载失败，请重试哦，亲", Toast.LENGTH_SHORT).show();
            if (isSuccess){
                updatePicture2Album(context, file.getAbsolutePath(), descrip, bmp.getWidth(), bmp.getHeight());
            }
        }
        return false;
    }

    public static void updatePicture2Album(Context context, String imagePath, String imageName, int width, int height) {
        long mImageTime = System.currentTimeMillis();

        try {
            long dateSeconds = mImageTime / 1000;
            ContentValues values = new ContentValues();
            ContentResolver resolver = context.getContentResolver();
            values.put(MediaStore.Images.ImageColumns.DATA, imagePath);
            values.put(MediaStore.Images.ImageColumns.TITLE, imageName);
            values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, imageName);
            values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, mImageTime);
            values.put(MediaStore.Images.ImageColumns.DATE_ADDED, dateSeconds);
            values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED, dateSeconds);
            values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.ImageColumns.WIDTH, width);
            values.put(MediaStore.Images.ImageColumns.HEIGHT, height);
            values.put(MediaStore.Images.ImageColumns.SIZE, new File(imagePath).length());
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } catch (Exception ex) {
            Log.d("LiveHelper", ex.toString());
        }
    }
}
