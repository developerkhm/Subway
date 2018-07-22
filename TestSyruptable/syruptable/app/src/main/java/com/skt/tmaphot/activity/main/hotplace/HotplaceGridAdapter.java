package com.skt.tmaphot.activity.main.hotplace;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;

import java.util.ArrayList;
import java.util.List;

public class HotplaceGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<HotplaceGridViewItem> hotplaceGridViewItemsList;
    private LayoutInflater layoutInflater;

    public HotplaceGridAdapter(Context context, List<HotplaceGridViewItem> hotplaceGridViewItemsList) {
        mContext = context;
        this.hotplaceGridViewItemsList = hotplaceGridViewItemsList;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }

    public int getCount() {
        return hotplaceGridViewItemsList.size();
    }

    public Object getItem(int position) {
        return hotplaceGridViewItemsList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_main_grid_hotplace_item, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.main_grid_hotplace_img_item);
        TextView TitletextView = (TextView) convertView.findViewById(R.id.main_grid_hotplace_txt_title);
        TextView MenutextView = (TextView) convertView.findViewById(R.id.main_grid_hotplace_txt_menu);
        TextView DistancetextView = (TextView) convertView.findViewById(R.id.main_grid_hotplace_txt_distance);
        TextView SaletextView = (TextView) convertView.findViewById(R.id.main_grid_hotplace_txt_sale);
        TextView ReviewtextView = (TextView) convertView.findViewById(R.id.main_grid_hotplace_txt_review);


        GradientDrawable drawable=
                (GradientDrawable) mContext.getDrawable(R.drawable.round_main_grid_hotplace_item);
        imageView.setBackground(drawable);
        imageView.setClipToOutline(true);

        MainApplication.loadUrlImage(mContext,hotplaceGridViewItemsList.get(position).getUrl(), imageView);


        TitletextView.setText(hotplaceGridViewItemsList.get(position).getTitle());
        MenutextView.setText(hotplaceGridViewItemsList.get(position).getMenu());
        DistancetextView.setText(hotplaceGridViewItemsList.get(position).getDistance());
        SaletextView.setText(hotplaceGridViewItemsList.get(position).getSale());
        ReviewtextView.setText(hotplaceGridViewItemsList.get(position).getReview());

        return convertView;
    }
}
