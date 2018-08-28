package com.skt.tmaphot;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenLicense extends BaseActivity {
    @BindView(R.id.openlicense_recylerview)
    RecyclerView openlicenseRecylerview;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_license);
        ButterKnife.bind(this);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        linearLayoutManager = new LinearLayoutManager(this);
        openlicenseRecylerview.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        openlicenseRecylerview.setLayoutManager(linearLayoutManager);

        List<ItemLicense> items = new ArrayList<>();
        items.add(new ItemLicense("com.android.support:appcompat-v7:28.0.0-alpha3"));
        items.add(new ItemLicense("com.android.support:design:28.0.0-alpha3"));
        items.add(new ItemLicense("com.android.support:cardview-v7:28.0.0-alpha3"));
        items.add(new ItemLicense("com.android.support:recyclerview-v7:28.0.0-alpha3"));
        items.add(new ItemLicense("com.android.support.constraint:constraint-layout:1.0.2"));
        items.add(new ItemLicense("com.android.support:support-v4:28.0.0-alpha3"));
        items.add(new ItemLicense("com.google.android.gms:play-services-location:12.0.1"));
        items.add(new ItemLicense("com.jakewharton:butterknife:8.8.1\nhttps://github.com/JakeWharton/butterknife/blob/master/LICENSE.txt"));
        items.add(new ItemLicense("com.facebook.stetho:stetho:1.5.0\ncom.facebook.stetho:stetho-okhttp\nhttps://github.com/facebook/stetho/blob/master/LICENSE"));
        items.add(new ItemLicense("com.github.bumptech.glide:glide:4.7.1\nhttps://github.com/bumptech/glide/blob/master/LICENSE"));
        items.add(new ItemLicense("com.squareup.retrofit2:retrofit\ncom.squareup.retrofit2:converter-gson:2.2.0\ncom.squareup.retrofit2:adapter-rxjava2:2.2.0\nhttps://github.com/square/retrofit/blob/master/LICENSE.txt"));
        items.add(new ItemLicense("com.squareup.okhttp3:okhttp:3.0.1\ncom.squareup.okhttp3:okhttp-urlconnection:3.0.1\ncom.squareup.okhttp3:logging-interceptor:3.8.0\nhttps://github.com/square/okhttp/blob/master/LICENSE.txt"));
        items.add(new ItemLicense("io.reactivex.rxjava2:rxjava:2.2.0\nio.reactivex.rxjava2:rxandroid:2.0.2\nhttps://github.com/ReactiveX/RxJava"));
        items.add(new ItemLicense("com.jakewharton.rxbinding2:rxbinding:2.1.1\nhttps://github.com/JakeWharton/RxBinding/blob/master/LICENSE.txt"));
        items.add(new ItemLicense("com.github.chrisbanes:PhotoView:2.1.4\nhttps://github.com/chrisbanes/PhotoView/blob/master/LICENSE"));
        items.add(new ItemLicense("com.github.katoro:typekit:1.0.1\nhttps://github.com/katoro/typekit"));
        items.add(new ItemLicense("com.crashlytics.sdk.android:crashlytics:2.9.4@aar\nhttps://get.fabric.io/"));
        items.add(new ItemLicense("com.muddzdev:styleabletoast:2.1.2\nhttps://github.com/Muddz/StyleableToast/blob/master/LICENSE"));
        items.add(new ItemLicense("com.github.javiersantos:MaterialStyledDialogs:2.1\nhttps://github.com/javiersantos/MaterialStyledDialogs/blob/master/LICENSE"));

        recyclerViewAdapter = new RecyclerViewAdapter(items);
        openlicenseRecylerview.setAdapter(recyclerViewAdapter);
    }

    public class ItemLicense {
        String opensource;

        public ItemLicense(String number) {
            this.opensource = number;
        }

        public String getOpensource() {
            return opensource;
        }

        public void setOpensource(String opensource) {
            this.opensource = opensource;
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        private List<ItemLicense> person;

        public RecyclerViewAdapter(List<ItemLicense> person) {
            this.person = person;
        }

        @Override
        public int getItemCount() {
            return person.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mOpensource;

            public ViewHolder(View itemView) {
                super(itemView);
                mOpensource = (TextView) itemView.findViewById(R.id.license_source);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_license_recycler_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            ItemLicense data = person.get(position);

            holder.mOpensource.setText((position + 1) + ". " + data.getOpensource());
        }
    }
}
