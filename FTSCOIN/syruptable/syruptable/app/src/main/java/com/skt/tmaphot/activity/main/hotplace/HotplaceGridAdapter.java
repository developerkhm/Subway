package com.skt.tmaphot.activity.main.hotplace;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;

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


    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.main_hotplace_grid_item, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.hotplace_grid_item_image);
        TextView TitletextView = (TextView) convertView.findViewById(R.id.hotplace_grid_txt_title);
        TextView MenutextView = (TextView) convertView.findViewById(R.id.hotplace_review);
        TextView DistancetextView = (TextView) convertView.findViewById(R.id.hotplace_grid_txt_distance);


        Drawable drawable= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = (Drawable)mContext.getDrawable(R.drawable.round_main_item);
            imageView.setBackground(drawable);
            imageView.setClipToOutline(true);
        }

        MainApplication.loadUrlImage(mContext,hotplaceGridViewItemsList.get(position).getUrl(), imageView);


        TitletextView.setText(hotplaceGridViewItemsList.get(position).getTitle());
        MenutextView.setText(hotplaceGridViewItemsList.get(position).getMenu());
        DistancetextView.setText(hotplaceGridViewItemsList.get(position).getDistance());



        return convertView;
    }
}
