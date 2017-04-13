package com.gaojia.shop_demo.ec;

import android.app.Application;

import com.gaojia.shop_demo.base.GlobalField;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ECApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EaseUI.getInstance().init(this,options);
        EMClient.getInstance().setDebugMode(true);
        EMClient.getInstance().updateCurrentUserNick(getSharedPreferences(GlobalField.USERINFO_FILENAME, MODE_PRIVATE).getString("username", "hdl"));//设置推送的昵称



    }
}
