package com.gaojia.shop_demo.goodsload;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.base.BaseActivity;
import com.gaojia.shop_demo.base.PhotoPopwindow;

import java.io.File;
import java.sql.SQLSyntaxErrorException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.ProgressCallback;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static android.os.Environment.DIRECTORY_DCIM;

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
    @BindView(R.id.add_image1)
    ImageView addImage1;
    private PhotoPopwindow mPhotoPopwindow;
    private String path;//指定文件路径
    private static String url="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsload);
        ButterKnife.bind(this);
        mPhotoPopwindow = new PhotoPopwindow(this,mHandler);

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

    @OnClick({R.id.btn_goods_load,R.id.layout_goodsload_type_bt,R.id.add_image1})
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
                break;
            case R.id.add_image1:
                if(mPhotoPopwindow != null){
                    if(!mPhotoPopwindow.isShowing()){
                        mPhotoPopwindow.showAtLocation(findViewById(R.id.add_image1),
                                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                    }else {
                        mPhotoPopwindow.dismiss();
                    }
                }
                break;
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
        final GoodsLoad goodsLoad = new GoodsLoad();
        goodsLoad.setGoodsID(goodsname.getText().toString());
        goodsLoad.setGoodsprice(mInt);
        goodsLoad.setGoodsdescribe(goodsdescribe.getText().toString());
        goodsLoad.setGoodstype(goodstype.getText().toString());
        File file = new File(path);
        final BmobFile bmobFile = new BmobFile(file);
        goodsLoad.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e==null){
                    Toast.makeText(GoodsLoadActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    System.out.println(e);
                    Toast.makeText(GoodsLoadActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            startPhotoZoom(data.getData());
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            startPhotoZoom(Uri.fromFile(new File(path)));
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            getImageToView(data);
        }
    }

    //固定设置一个指定的文件用来保存图片
    public Uri getPath() {
        path = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM) + "/" + System.currentTimeMillis() + ".jpg";//创建路径
        Uri uri = Uri.fromFile(new File(path));//通过给定的path得到一个uri地址
        return uri;
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 360);
        intent.putExtra("outputY", 360);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(this.getResources(), photo);
            addImage1.setImageDrawable(drawable);

        }
    }
    /** 处理照片popwindow点击回调 **/
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 110:
                    Uri path = getPath();
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);//设置action为拍照
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, path);
                    startActivityForResult(intent, 2);
                    break;
                case 111:
                    Uri path1 = getPath();
                    Intent intent1 = new Intent();
                    intent1.setAction(Intent.ACTION_PICK);
                    intent1.putExtra(MediaStore.EXTRA_OUTPUT ,path1);
                    intent1.setType("image/*");//从所有图片中进行选择
                    startActivityForResult(intent1, 1);
                    break;
                default:
                    break;
            }
        }
    };
    
}
