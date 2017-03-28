package com.gaojia.shop_demo.home;

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

public class HomeFragment extends Fragment {
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
