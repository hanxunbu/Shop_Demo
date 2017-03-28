package com.gaojia.shop_demo.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaojia.shop_demo.R;


/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class FindFragment extends Fragment {

    public static FindFragment newInstance(){
        return new FindFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,container,false);
        return view;
    }
}
