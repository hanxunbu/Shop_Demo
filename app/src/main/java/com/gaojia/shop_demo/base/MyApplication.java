package com.gaojia.shop_demo.base;

import android.app.Application;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class MyApplication extends Application{
    private static MyApplication mInstance;
    public static MyApplication getInstance(){
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}
