package com.skt.tmaphot.activity.bottom;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.blog.MyBlog;
import com.skt.tmaphot.fragment.BaseFragment;

public class MyBlogFragment extends BaseFragment {

    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.myblog_layout, container, false);
        view.findViewById(R.id.toolbar).setVisibility(View.GONE);

        MyBlog blog = new MyBlog(getActivity(), handler, view);

        return view;
    }

}
