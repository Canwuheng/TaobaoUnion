package com.example.taobaounion.base;

public interface IBaesCallback {
    /**
     * 加载出错
     */
    void onNetWorkError();

    /**
     * 加载中
     */
    void onLoading();

    /**
     * 数据为空
     */
    void onEmpty();
}
