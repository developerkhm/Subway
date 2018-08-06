package com.skt.tmaphot.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.BaseApplication;

public class BaseFragment extends Fragment {

    protected View rootView;

    @Override
    public void onStart() {
        super.onStart();
//        if (rootView != null) {
//            locationAddress = (TextView) rootView.findViewById(R.id.appbar_location_txt);
//            locationAddress.setText(GPSData.LOCATION_ADDRESS);
//            locationAddress.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    MainApplication.ActivityStart(new Intent(getActivity(), SelectionAreaActivity.class), null);
//                }
//            });
//        }
    }

    public void ActivityStart(Intent intent, Bundle bundle) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.getInstance().ActivityStart(intent, bundle);
    }

    public void loadImage(Context context, Object res, ImageView view, boolean isRound) {
        BaseApplication.getInstance().loadImage(context, res, view, isRound);
    }
}
