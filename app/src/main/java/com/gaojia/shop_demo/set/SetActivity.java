package com.gaojia.shop_demo.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.base.BaseActivity;
import com.gaojia.shop_demo.ec.ECloginActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/8.
 */

public class SetActivity extends BaseActivity{

    @BindView(R.id.exit)
    Button exit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.exit)
    public void onClick(){
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("main", "下线成功了");
                startActivity(new Intent(getApplicationContext(), ECloginActivity.class));
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.e("main", "下线失败了！" + s);
//                        Toast.makeText(MainActivity.this, "下线失败,多点几次吧( " + s + " )", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });//下线
    }
}
