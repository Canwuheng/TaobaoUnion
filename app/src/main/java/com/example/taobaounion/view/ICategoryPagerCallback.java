package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaesCallback;
import com.example.taobaounion.model.domain.Categores;
import com.example.taobaounion.model.domain.HomePagerContent;

import java.util.List;

public interface ICategoryPagerCallback extends IBaesCallback {
    void onContentLoad(List<HomePagerContent.DataBean> contents,int categoryId);


    int getCategoryId();

    /**
     * 加载更多时网络出错
     */
    void onLoaderMoreError();

    /**
     * 加载更多时数据为空

     */
    void onLoaderMoreEmpty();

    /**
     * 加载到更多内容
     * @param contents
     */
    void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents);

    /**
     * 轮播图的加载
     * @param contents
     */
    void onLooprListLoaded(List<HomePagerContent.DataBean> contents);
}
