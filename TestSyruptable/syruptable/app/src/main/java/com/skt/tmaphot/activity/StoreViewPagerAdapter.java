package com.skt.tmaphot.activity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.skt.tmaphot.R;

public class StoreViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
//    private Integer [] images = {R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};

    private String[] linkURL = new String[]{
            "https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg",
            "https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg",
            "https://picksell.co.kr/images/product/124369/4d69f008-386e-479f-aa09-a7447301383e.jpg"
    };

    public StoreViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return linkURL.length;
    }


    //페이지 뷰가 생성된 페이지의 object key와 같은지 확인합니다. object key는 instantiateItem 메소드에서 리턴 시킨 object 입니다.
    //즉, 페이지의 뷰가 생성된 뷰인지 아닌지를 확인합니다.

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_store_viewpager, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);


        Glide.with(context).load(linkURL[position]).into(imageView);

//        imageView.setImageResource();

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
