package com.example.open.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.open.R;

public class Fragment5 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             //@Nullable标注给定的参数或者返回值可以为null
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate
                (R.layout.fragment5,container,false);
        return v;
    }
}
