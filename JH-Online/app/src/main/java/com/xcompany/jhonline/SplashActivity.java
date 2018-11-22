package com.xcompany.jhonline;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;


/**
 *  启动页
 */
public class SplashActivity extends AppCompatActivity {
    //implements  ActivityCompat.OnRequestPermissionsResultCallback {

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    public final long MIN_DELAYED = 3000;
    private Handler handler = null;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.handler = new Handler();
        startTime = System.currentTimeMillis();
        testPermissions();
    }

    public void testPermissions() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUEST_CODE_ASK_PERMISSIONS);
//            return;
//        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
//                    REQUEST_CODE_ASK_PERMISSIONS);
//            return;
//        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
//                    REQUEST_CODE_ASK_PERMISSIONS);
//            return;
//        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_CODE_ASK_PERMISSIONS);
//            return;
//        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
//                    REQUEST_CODE_ASK_PERMISSIONS);
//            return;
//        }
        toNextActivity();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .create()
                .show();
    }

    private void toNextActivity() {

        long timeElapse = System.currentTimeMillis() - startTime;
        long delayed = MIN_DELAYED - timeElapse;
        if (delayed < 0) {
            delayed = 1;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, delayed);
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_ASK_PERMISSIONS: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
//                        String message = "";
//                        if (permissions[0].equalsIgnoreCase(Manifest.permission.CALL_PHONE)) {
//                            message = "我们需要获取拨号权限，让您可以使用公司的商务电话功能；否者，您将无法正常启动应用";
//                        } else if (permissions[0].equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                            message = "我们需要获取存储空间，为您存储个人信息；否者，您将无法正常使用应用";
//                        } else if (permissions[0].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)) {
//                            message = "我们需要获取定位权限，让您可以使用地图定位功能；否者，您将无法正常启动应用";
//                        } else if (permissions[0].equalsIgnoreCase(Manifest.permission.CAMERA)) {
//                            message = "我们需要获取拍照权限，让您可以使用拍照功能；否者，您将无法正常启动应用";
//                        } else if (permissions[0].equalsIgnoreCase(Manifest.permission.RECORD_AUDIO)) {
//                            message = "我们需要获取录像权限，让您可以使用录像功能；否者，您将无法正常启动应用";
//                        }
//                        showMessageOKCancel(message, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                testPermissions();
//                            }
//                        });
//                        return;
//                    }
//                }
//                testPermissions();
//                return;
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

}
