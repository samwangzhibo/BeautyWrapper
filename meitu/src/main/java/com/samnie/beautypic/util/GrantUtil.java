package com.samnie.beautypic.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by samwangzhibo on 2017/12/6.
 */

public class GrantUtil {
    public static final int REQUEST_EXTERNAL_STORAGE = 2;

    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void requestStoragePermissions(Activity activity) {
        ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
        );
    }

    public boolean isStoragePermissionGranted(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }



}
