package com.example.velmurugan.retrofitandroidexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    Context context;
    List<GateList> gateList;

    public RecyclerAdapter(Context context, List<GateList> gateList) {
        this.context = context;
        this.gateList = gateList;
    }

    public void setGateList(List<GateList> gateList) {
        this.gateList = gateList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_adapter,parent,false);
            return new ItemViewHolder(view);

        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_header, parent, false);
            return new HeaderViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof HeaderViewHolder) {

            //set the Value from List to corresponding UI component as shown below.

            Glide.with(context).load(R.drawable.hood_main_2_list_title).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(((HeaderViewHolder) holder).headerImageView);

            //similarly bind other UI components or perform operations

        }else if (holder instanceof ItemViewHolder) {

            Glide.with(context).load(R.drawable.list_0122).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(((ItemViewHolder) holder).image);
            ((ItemViewHolder) holder).tvMovieName.setText(splitStringLineUp(gateList.get(position-1).getCfrBuild()));   //-1 중요
            float text_height = ((ItemViewHolder) holder).tvMovieName.getHeight() / 2;
            ((ItemViewHolder) holder).tvMovieName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) text_height);
        }
    }

    @Override
    public int getItemCount() {
        if(gateList != null){
            return gateList.size() + 1; // Add two more counts to accomodate header and footer
        }
        return 0;
    }

    //뷰타입 정하기
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }


    // The ViewHolders for Header, Item and Footer
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public View View;
        private final ImageView headerImageView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            View = itemView;

            // add your ui components here like this below
            headerImageView = (ImageView) View.findViewById(R.id.header);
            headerImageView.getLayoutParams().width = utilGetViewWidthPx(2160);
            headerImageView.getLayoutParams().height = utilGetViewWidthPx(161);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View View;
        ImageView image;
        TextView tvMovieName;
        public ItemViewHolder(View itemView) {
            super(itemView);
            View = itemView;
            // Add your UI Components here
            image = (ImageView)View.findViewById(R.id.image);
            image.getLayoutParams().width = utilGetViewWidthPx(639);
            image.getLayoutParams().height = utilGetViewWidthPx(228);
            tvMovieName = (TextView)itemView.findViewById(R.id.title);
            tvMovieName.getLayoutParams().width = utilGetViewWidthPx(2160-639);
            tvMovieName.getLayoutParams().height = utilGetViewWidthPx(228);


        }

    }

//    public class MyviewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
//        TextView tvMovieName;
////        ImageView image;
//
//        public MyviewHolder(View itemView) {
//            super(itemView);
//            image = (ImageView)itemView.findViewById(R.id.image);
//            image.getLayoutParams().width = utilGetViewWidthPx(639);
//            image.getLayoutParams().height = utilGetViewWidthPx(228);
//            tvMovieName = (TextView)itemView.findViewById(R.id.title);
//            tvMovieName.getLayoutParams().width = utilGetViewWidthPx(2160-639);
//            tvMovieName.getLayoutParams().height = utilGetViewWidthPx(228);
//        }
//    }

    public int utilGetViewWidthPx(int originWidth){
        // 1. 시안 총 폭을 구하고   =   PDF_WIDTH_PX
        // 2. 현 기기 가로 px 구하고
        int screenWidth = getPhoneWidthPx();
        // 3. 대상 이미지의 폭 인자로 받고
        // 4. 이 기기에서의 x 값을 구함. (2160:1080 = 2080:x)
        int x = screenWidth * originWidth / 2160;
        return x;
    }

    public int getPhoneWidthPx(){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        Log.i("hoyangi","screen width:"+screenWidth);
        return screenWidth;
    }


    public StringBuilder splitStringLineUp(String oriString){

        String tempString[] = oriString.split(",");
        StringBuilder resultString = new StringBuilder();


        for (int i = 0; i < tempString.length; i++) {

            if(i == tempString.length-1){
                resultString.append(tempString[i]);
                break;
            }

            if((i+1)%3 == 0 ){
                resultString.append(tempString[i]).append("\n");
                continue;
            }
            resultString.append(tempString[i]).append(",");
        }

        return resultString;
    }
}

