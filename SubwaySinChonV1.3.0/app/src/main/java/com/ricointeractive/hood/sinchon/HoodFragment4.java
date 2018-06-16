package com.ricointeractive.hood.sinchon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ricointeractive.common.CommonUtil;
import com.ricointeractive.subwaydemosinchonstationmap.R;

public class HoodFragment4 extends Fragment {
    private ImageView mImg_Main;
    private int mImg_res;
    private GridView gridview;
    private int[] mThumbIds;
    private ImageView mImg_Search;
    private Button btn[];
    private Button mBtn_Search, mBtn_Exit;
    private EditText et;
    private LinearLayout mKeyboard;
    private HoodKeyboard chunjiin;

    private AdapterView.OnItemClickListener mOnItemClickListener;

    public HoodFragment4() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hood_fragment4, container, false);


        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (HoodActivity.mModeNo == 4) {
                    float text_height = et.getHeight() / 2;
                    et.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) text_height);

                    for (int i = 0; i < btn.length; i++) {
                        btn[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, btn[0].getHeight()/2);
                    }

                    //spacebar
                    Drawable drawable = getActivity().getResources().getDrawable(R.drawable.hood_main_4_keyboard_spacebar);
                    drawable.setBounds(0, 0, btn[0].getHeight() / 2, btn[0].getHeight() / 2);
                    btn[10].setCompoundDrawables(drawable, null, null, null);
                    btn[10].setPadding(btn[0].getHeight()/2,0,0,0);

                    //backspace
                    Drawable drawable2 = getActivity().getResources().getDrawable(R.drawable.hood_main_4_keyboard_backspace);
                    drawable2.setBounds(0, 0, btn[0].getHeight() / 2 -2, btn[0].getHeight() / 2 -2);
                    btn[11].setCompoundDrawables(drawable2, null, null, null);
                    btn[11].setPadding(btn[0].getHeight()/2,0,0,0);

                    //exit
                    Drawable drawable3 = getActivity().getResources().getDrawable(R.drawable.hood_main_4_keyboard_exit);
                    drawable3.setBounds(0, 0, btn[0].getHeight() / 2, btn[0].getHeight() / 2);
                    mBtn_Exit.setCompoundDrawables(drawable3, null, null, null);
                    mBtn_Exit.setPadding(btn[0].getHeight()/2,0,0,0);

                    //search
                    Drawable drawable4 = getActivity().getResources().getDrawable(R.drawable.hood_main_4_keyboard_search);
                    drawable4.setBounds(0, 0, btn[0].getHeight() / 2, btn[0].getHeight() / 2);
                    mBtn_Search.setCompoundDrawables(drawable4, null, null, null);
                    mBtn_Search.setPadding(btn[0].getHeight()/2,0,0,0);
                }
            }
        });


        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridview.getVisibility() == View.VISIBLE) {
                    gridview.setVisibility(View.GONE);
                } else {
                    gridview.setVisibility(View.VISIBLE);
                }

                if (mKeyboard != null)
                    mKeyboard.setVisibility(View.GONE);
            }
        });
        mImg_res = R.drawable.hood_main_1;
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);

        mThumbIds = new int[]{
                R.drawable.hood_main_4_grid_1,
                R.drawable.hood_main_4_grid_2,
                R.drawable.hood_main_4_grid_3,
                R.drawable.hood_main_4_grid_4,
                R.drawable.hood_main_4_grid_5,
                R.drawable.hood_main_4_grid_6,
                R.drawable.hood_main_4_grid_7,
                R.drawable.hood_main_4_grid_8,
                R.drawable.hood_main_4_grid_9,
                R.drawable.hood_main_4_grid_10,
                R.drawable.hood_main_4_grid_11,
                R.drawable.hood_main_4_grid_12,
                R.drawable.hood_main_4_grid_13
        };

        gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(442 + 442 + 442 + 442 + 60);
        gridview.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(474 + 474 + 474 + 474 + 60);

        gridview.setAdapter(new ImageAdapter(getActivity()));
        mOnItemClickListener = new MenuOnClickListener();

        gridview.setOnItemClickListener(mOnItemClickListener);


        //******************** keyboard **********************

        mKeyboard = (LinearLayout) view.findViewById(R.id.keyboard);
        mKeyboard.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(1500);
        mKeyboard.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(1200);

        et = (EditText) view.findViewById(R.id.chunjiin_text);
        btn = new Button[13];
        btn[0] = (Button) view.findViewById(R.id.chunjiin_button0);
        btn[1] = (Button) view.findViewById(R.id.chunjiin_button1);
        btn[2] = (Button) view.findViewById(R.id.chunjiin_button2);
        btn[3] = (Button) view.findViewById(R.id.chunjiin_button3);
        btn[4] = (Button) view.findViewById(R.id.chunjiin_button4);
        btn[5] = (Button) view.findViewById(R.id.chunjiin_button5);
        btn[6] = (Button) view.findViewById(R.id.chunjiin_button6);
        btn[7] = (Button) view.findViewById(R.id.chunjiin_button7);
        btn[8] = (Button) view.findViewById(R.id.chunjiin_button8);
        btn[9] = (Button) view.findViewById(R.id.chunjiin_button9);
        btn[10] = (Button) view.findViewById(R.id.chunjiin_buttonex1); //spacebar
        btn[11] = (Button) view.findViewById(R.id.chunjiin_buttonex2); //backspace
        btn[12] = (Button) view.findViewById(R.id.chunjiin_buttonex3);


        mBtn_Search = (Button) view.findViewById(R.id.chunjiin_search); //search
        mBtn_Exit = (Button) view.findViewById(R.id.chunjiin_exit); //exit
        mBtn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.getInstance().callToast(getActivity());
                mImg_Search.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mKeyboard.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });

        mBtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeyboard.setVisibility(View.GONE);
                gridview.setVisibility(View.VISIBLE);
            }
        });


        mImg_Search = (ImageView) view.findViewById(R.id.image_search);
        mImg_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.getInstance().callToast(getActivity());
                mImg_Search.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mKeyboard.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });
        //******************** keyboard **********************

        return view;
    }

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context c) {

            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return mThumbIds[position];
        }

        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = null;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(
                        CommonUtil.getInstance().utilGetViewWidthPx(442), CommonUtil.getInstance().utilGetViewWidthPx(474)));
                imageView.setScaleType(ImageView.ScaleType.FIT_START);
//                imageView.setPadding(1, 1, 1, 1);
            } else {

                imageView = (ImageView) convertView;
            }
            CommonUtil.getInstance().loadImage(getActivity(), mThumbIds[position], imageView);
            return imageView;
        }
    }

    private class MenuOnClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:    //search
                    gridview.setVisibility(View.GONE);
                    et.setText("");
                    getKeybord();
                    return;
            }
            CommonUtil.getInstance().callToast(getActivity());
        }
    }

    private void getKeybord() {
        chunjiin = new HoodKeyboard(et, btn);
        mKeyboard.setVisibility(View.VISIBLE);
        et.requestFocus();
        et.setFocusable(true);
    }

    public void testGridViewOnoff() {
        if (mKeyboard != null)
            mKeyboard.setVisibility(View.GONE);

        if (gridview != null) {
            if (gridview.getVisibility() == View.GONE) {
                gridview.setVisibility(View.VISIBLE);
            }
        }
    }

//    ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener

}
