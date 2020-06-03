package com.example.taobaounion.presenter.impl;

import android.util.Log;

import com.example.taobaounion.model.Api;
import com.example.taobaounion.model.domain.Categores;
import com.example.taobaounion.presenter.IHomePresenter;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.view.IHomeCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class HomePresenterImpl implements IHomePresenter {
    private IHomeCallback mcallback;

    @Override
    public void getCategories() {
        if (mcallback != null) {
            mcallback.onLoading();
        }
        //加载数据
        Retrofit retrofit = RetrofitManager.getInstance().getmRetrofit();
        Api api = retrofit.create(Api.class);
        Call<Categores> task = api.getCategores();
        task.enqueue(new Callback<Categores>() {
            @Override
            public void onResponse(Call<Categores> call, Response<Categores> response) {
                //数据结果
                int code = response.code();

                if (code == HttpURLConnection.HTTP_OK) {
                    Categores categores = response.body();
                    //数据请求成功
                    if (mcallback != null) {
                        if (categores == null || categores.getData().size() == 0) {
                            mcallback.onEmpty();
                        } else {
                            if (mcallback != null) {
                                mcallback.onCategoriesLoaded(categores);
                                Log.d("HOME", "onResponse: "+categores.getData().toString());
                            }
                        }
                    }
                } else {
                    if (mcallback != null) {
                        mcallback.onNetWorkError();
                    }
                }

            }

            @Override
            public void onFailure(Call<Categores> call, Throwable t) {
                //加载失败的结果
                Log.d("HOME", "请求错误"+t.toString());
                if (mcallback != null) {

                    mcallback.onNetWorkError();
                }
            }
        });
    }

    @Override
    public void registerViewCallback(IHomeCallback callback) {
        this.mcallback = callback;
    }

    @Override
    public void unregisterViewCallback(IHomeCallback callback) {
        mcallback = null;
    }
}
