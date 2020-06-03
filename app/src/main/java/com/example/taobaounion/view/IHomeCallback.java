package com.example.taobaounion.view;

import com.example.taobaounion.model.domain.Categores;

public interface IHomeCallback {
    /**
     *
     * @param categoryes
     */
    void onCategoriesLoaded(Categores categoryes);

    void onNetWorkError();
    void onLoading();
    void onEmpty();


}
