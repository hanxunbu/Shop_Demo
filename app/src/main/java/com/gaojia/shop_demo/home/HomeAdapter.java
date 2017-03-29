package com.gaojia.shop_demo.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.base.NewBaseAdapter;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public class HomeAdapter extends NewBaseAdapter{

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.adapter_homefragment_item,null);
            holder = new HomeHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.adapter_homefragment_item_iv);
            holder.name = (TextView) convertView.findViewById(R.id.adapter_homefragment_item_tv);
            holder.detail = (TextView) convertView.findViewById(R.id.adapter_homefragment_item_name_tv);
            holder.price = (TextView) convertView.findViewById(R.id.adapter_homefragment_item_date_tv);
            convertView.setTag(holder);

        }else {
            holder = (HomeHolder) convertView.getTag();
        }
        return convertView;
    }
    class HomeHolder{
        ImageView image;
        TextView name;
        TextView detail;
        TextView price;
    }
}
