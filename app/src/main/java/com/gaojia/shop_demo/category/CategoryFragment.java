package com.gaojia.shop_demo.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gaojia.shop_demo.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class CategoryFragment extends Fragment {


    public static CategoryFragment newInstance(){
        return new CategoryFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
