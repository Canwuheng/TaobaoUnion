package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBaesPresenter;
import com.example.taobaounion.view.IHomeCallback;

public interface IHomePresenter extends IBaesPresenter<IHomeCallback> {
    /**
     * 获取商品分类
     */
    void getCategories();



}
