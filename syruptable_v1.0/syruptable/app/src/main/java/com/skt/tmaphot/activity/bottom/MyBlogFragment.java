package com.skt.tmaphot.activity.bottom;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.skt.tmaphot.MainSplashActivity;
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


        new MaterialStyledDialog.Builder(getContext())
                .setTitle(R.string.app_name)
                .setDescription("로그인이 필요합니다.")
//                .setStyle(Style.HEADER_WITH_TITLE)
//                .setHeaderColor(R.color.colorBlack)
                .setHeaderColor(R.color.text_gray_d4)
                .setHeaderDrawable(R.drawable.ic_sms_failed)
                .setPositiveText("네")
                .withDialogAnimation(true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .setNegativeText("아니요")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        
                    }
                })
                .show();


//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setMessage(
//                "로그인 하시겠습니까?")
//                .setPositiveButton("예",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//
//                            }
//                        })
//                .setNegativeButton("아니요",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                            }
//                        });
//        AlertDialog alert = builder.create();
//        alert.show();

        return view;
    }

}
