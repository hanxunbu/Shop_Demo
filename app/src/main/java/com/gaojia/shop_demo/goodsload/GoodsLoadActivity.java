package com.gaojia.shop_demo.goodsload;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class GoodsLoadActivity extends BaseActivity {
    @BindView(R.id.layout_goodsload_name_et)
    EditText goodsname;
    @BindView(R.id.layout_goodsload_pay_et)
    EditText goodsprice;
    @BindView(R.id.layout_goodsload_type_bt)
    Button setgoodstype;
    @BindView(R.id.layout_goodsload_describe_et)
    EditText goodsdescribe;
    @BindView(R.id.layout_goodsload_type_tv)
    TextView goodstype;
    @BindView(R.id.btn_goods_load)
    Button load;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsload);
        ButterKnife.bind(this);

        //        第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("64ff02a0268ea3a576ec974dbebb3226")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }

    @OnClick({R.id.btn_goods_load,R.id.layout_goodsload_type_bt})
    public void OnClick(View view){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ToGoodsLoad();
            }
        });
        switch (view.getId()){
            case R.id.btn_goods_load:
                thread.start();
            break;
            case R.id.layout_goodsload_type_bt:
                ShowChoise();
        }
    }

    private void ShowChoise() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(GoodsLoadActivity.this);
        builder.setTitle("选择一个类别");
        final String[] type = {"家用", "电子", "服饰", "玩具", "图书", "礼品", "其他"};
        builder.setItems(type, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goodstype.setText(type[which]);
            }
        });
        builder.show();
    }
    /**往数据库中添加商品*/
    private void ToGoodsLoad() {
        String str = goodsprice.getText().toString();
        Integer mInt = Integer.parseInt(str);
        GoodsLoad goodsLoad = new GoodsLoad();
        goodsLoad.setGoodsID(goodsname.getText().toString());
        goodsLoad.setGoodsprice(mInt);
        goodsLoad.setGoodsdescribe(goodsdescribe.getText().toString());
        goodsLoad.setGoodstype(goodstype.getText().toString());
        goodsLoad.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e==null){
                    Toast.makeText(GoodsLoadActivity.this,"创建数据成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
