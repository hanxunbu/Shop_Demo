package com.gaojia.shop_demo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public abstract class NewBaseAdapter extends BaseAdapter{
    public List data = new ArrayList<>();
    public Context context;
    public LayoutInflater inflater;

    public NewBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
