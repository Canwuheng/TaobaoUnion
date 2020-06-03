package com.example.taobaounion.ui.adpter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.taobaounion.model.domain.HomePagerContent;
import com.example.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

public class looperViewPagerAdapter extends PagerAdapter {

    private List<HomePagerContent.DataBean> mDatas = new ArrayList<>();

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        //处理越界问题
        int realposition = position % mDatas.size();
        HomePagerContent.DataBean dataBean = mDatas.get(realposition);
        String pict_url = UrlUtils.getCoverPath(dataBean.getPict_url());

        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        Glide.with(container.getContext()).load(pict_url).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (mDatas!=null) {

            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


    public void setData(List<HomePagerContent.DataBean> contents) {
        this.mDatas.clear();
        mDatas.addAll(contents);
        notifyDataSetChanged();
    }

    public int getDataSize() {
        return mDatas.size();
    }
}
