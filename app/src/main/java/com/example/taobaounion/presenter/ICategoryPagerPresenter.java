package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBaesPresenter;
import com.example.taobaounion.model.domain.HomePagerContent;
import com.example.taobaounion.view.ICategoryPagerCallback;

import java.util.List;

public interface ICategoryPagerPresenter extends IBaesPresenter<ICategoryPagerCallback> {
    /**
     * 根据分类id获取内容
     * @param catgoryId
     */
    void getContentByCategoryId(int catgoryId);

    void loaderMore(int categoryId);

    void reload(int catgoryId);
}
