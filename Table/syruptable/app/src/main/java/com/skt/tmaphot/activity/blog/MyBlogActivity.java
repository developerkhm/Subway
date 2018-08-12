package com.skt.tmaphot.activity.blog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.skt.tmaphot.R;
import com.skt.tmaphot.BaseActivity;

public class MyBlogActivity extends BaseActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        LinearLayout linearLayout = (LinearLayout) inflater.inflate( R.layout.myblog_layout, null );
        setContentView( linearLayout );

        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        MyBlog blog = new MyBlog(baceContext, handler, linearLayout);


    } // onCreate End
} // class End
