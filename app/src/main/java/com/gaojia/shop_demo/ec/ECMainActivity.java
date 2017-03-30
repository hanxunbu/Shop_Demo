package com.gaojia.shop_demo.ec;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.base.BaseActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ECMainActivity extends BaseActivity {
    // 发起聊天 username 输入框
    private EditText mChatIdEdit;
    // 发起聊天
    private Button mStartChatBtn;
    // 退出登录
    private Button mSignOutBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_ec);
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        mChatIdEdit = (EditText) findViewById(R.id.ec_edit_chat_id);

        mStartChatBtn = (Button) findViewById(R.id.ec_btn_start_chat);
        mStartChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChat();
            }
        });

        mSignOutBtn = (Button) findViewById(R.id.ec_btn_sign_out);
        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });
    }

    private void startChat(){
        Intent intent = new Intent(ECMainActivity.this,ECChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID,mChatIdEdit.getText().toString().trim());
        //单聊
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
        startActivity(intent);
    }
    private void signout(){
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(ECMainActivity.this,ECloginActivity.class));

            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(ECMainActivity.this, "退出失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}

