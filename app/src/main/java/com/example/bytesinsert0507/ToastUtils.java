package com.example.bytesinsert0507;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Android Studio.
 * User: zhenlong.luo
 * Date: 2021/5/24
 * description:
 */
public class ToastUtils {
    public static void ShowToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
