package com.gaojia.shop_demo.mine;

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
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.base.PhotoPopwindow;
import com.gaojia.shop_demo.ec.ECloginActivity;
import com.gaojia.shop_demo.goodsload.GoodsLoadActivity;
import com.gaojia.shop_demo.user.UserActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_DCIM;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class MineFragment extends Fragment {
    @BindView(R.id.text_service)
    TextView service_tv;
    @BindView(R.id.fragment_mine_goodsload)
    RelativeLayout goodsload;
    @BindView(R.id.text_username)
    TextView lar;
    @BindView(R.id.image_avatar)
    ImageView mImg;

    private PhotoPopwindow mPhotoPopwindow;

    private String path;//指定文件路径
    public static MineFragment newInstance(){
        return new MineFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_mine,container,false);
        ButterKnife.bind(this,view);
        mPhotoPopwindow = new PhotoPopwindow(getContext(),mHandler);
        return view;

    }
    @OnClick({R.id.text_service,R.id.fragment_mine_goodsload,R.id.text_username,R.id.image_avatar})
    public void OnClick(View view ){
        switch (view.getId()){
            case R.id.text_service:
                startActivity(new Intent(getContext(), ECloginActivity.class));
                break;
            case R.id.fragment_mine_goodsload:
                Intent goodsload = new Intent(getContext(),GoodsLoadActivity.class);
                startActivity(goodsload);
                break;
            case R.id.text_username:
                Intent LoginAndRegister = new Intent(getContext(),UserActivity.class);
                startActivity(LoginAndRegister);
                break;
            case R.id.image_avatar:
                if(mPhotoPopwindow != null){
                    if(!mPhotoPopwindow.isShowing()){
                        mPhotoPopwindow.showAtLocation(view.findViewById(R.id.image_avatar),
                                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    }else {
                        mPhotoPopwindow.dismiss();
                    }
                }
                break;

        }
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
            mImg.setImageDrawable(drawable);
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
