package com.lnyp.sexybeach.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
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

    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp, String descrip) {
        // 首先保存图片
        String storePath = context.getExternalFilesDir(IMG_PATH).getAbsolutePath();
        File appDir = new File(storePath);
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
            isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
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
            Toast.makeText(context, isSuccess ? "下载成功" : "下载失败，请重试哦，亲", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
