package com.gaojia.shop_demo.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class BaseFragment extends Fragment {
    private Activity activity;

    public Context getContext(){
        if(activity == null){
            return MyApplication.getInstance();
        }
        return activity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity = getActivity();
    }
}
