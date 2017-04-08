package com.gaojia.shop_demo.vip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaojia.shop_demo.R;

/**
 * Created by Administrator on 2017/3/31.
 */

public class VIPFragment extends Fragment {

    public static VIPFragment newInstance(){
        return new VIPFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_vip,container,false);
        return view;

    }
}
