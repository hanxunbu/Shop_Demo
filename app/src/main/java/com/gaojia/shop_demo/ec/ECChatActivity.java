package com.gaojia.shop_demo.ec;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.base.BaseActivity;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

/**
 * Created by Administrator on 2017/3/30.
 */
public class ECChatActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_ec);

        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();

        //将参数传递给聊天界面
        chatFragment.setArguments(getIntent().getExtras());

        //传入参数
        Bundle args = new Bundle();

        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID));
        chatFragment.setArguments(args);

        //加载EASEUI封装的聊天界面Fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ec_layout_container,chatFragment).commit();
    }

}
