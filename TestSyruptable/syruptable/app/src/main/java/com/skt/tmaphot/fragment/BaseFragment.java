package com.skt.tmaphot.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.area.SelectionAreaActivity;
import com.skt.tmaphot.activity.main.coupon.more.CouponListActivity;
import com.skt.tmaphot.location.GPSData;

public class BaseFragment extends Fragment {

    protected TextView locationAddress;
    protected View rootView;

    @Override
    public void onStart() {
        super.onStart();
        if (rootView != null) {
            locationAddress = (TextView) rootView.findViewById(R.id.appbar_location_txt);
            locationAddress.setText(GPSData.LOCATION_ADDRESS);
            locationAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainApplication.ActivityStart(new Intent(getActivity(), SelectionAreaActivity.class), null);
                }
            });
        }
    }
}
