package com.xcompany.jhonline.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by xieliang on 2018/11/21 11:47
 */
public class T {
    public static Toast mToast;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void showToast(final Context mContent, final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(mContent, "", Toast.LENGTH_LONG);
                }
                mToast.setText(msg);
                mToast.show();
            }
        });

    }


}
