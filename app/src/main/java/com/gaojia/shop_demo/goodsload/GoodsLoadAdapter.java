package com.gaojia.shop_demo.goodsload;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gaojia.shop_demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class GoodsLoadAdapter extends RecyclerView.Adapter<GoodsLoadAdapter.ImageHolder> {

    private List list = new ArrayList<>();
    private Context context;

    public GoodsLoadAdapter(Context context) {
        this.context = context;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class ImageHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public ImageHolder(View itemView) {
            super(itemView);
            //找到管理的控件
            iv = (ImageView) itemView.findViewById(R.id.adapter_image_item_iv);
        }
    }
}
