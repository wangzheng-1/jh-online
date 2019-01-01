package com.xcompany.jhonline.module.system.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.UnicodeSet;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.squareup.haha.perflib.Main;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.JhApplication;
import com.xcompany.jhonline.model.base.CityService;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.module.login.LoginActivity;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.CityUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *  启动页
 */
public class SplashActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

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
        initPermission();
    }

    String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE};
    List<String> mPermissionList = new ArrayList<>();

    public void  testPermissions() {

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
                if(UserService.getInstance().isLogin()){
                    intent.setClass(SplashActivity.this, MainActivity.class);
                }
                else {
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                SplashActivity.this.finish();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CityUtil.getCityList(1, "0",null);
                    }
                }).start();
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
//
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissions[0])) {
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

    //权限判断和申请
    private void initPermission() {
        mPermissionList.clear();//清空没有通过的权限

        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }
        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        }
        else {
            toNextActivity();
        }
    }


    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                    break;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                initPermission();//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
            }else{
                //全部权限通过，可以进行下一步操作。。。
                toNextActivity();
            }
        }

    }


}
