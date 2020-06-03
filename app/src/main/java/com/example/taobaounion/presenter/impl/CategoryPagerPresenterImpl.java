package com.example.taobaounion.presenter.impl;

import android.util.Log;

import com.example.taobaounion.model.Api;
import com.example.taobaounion.model.domain.HomePagerContent;
import com.example.taobaounion.presenter.ICategoryPagerPresenter;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.UrlUtils;
import com.example.taobaounion.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryPagerPresenterImpl implements ICategoryPagerPresenter {
    private static Integer DEFAULT_PAGE = 1;
    private ICategoryPagerCallback mcallback;
    private Map<Integer, Integer> pagerInfo = new HashMap<>();
    private String TAG= "home_pager_presenter";

    private CategoryPagerPresenterImpl() {

    }

    private static ICategoryPagerPresenter sIntance = null;

    public static ICategoryPagerPresenter getIntance() {
        if (sIntance == null) {
            sIntance = new CategoryPagerPresenterImpl();
        }
        return sIntance;
    }


    @Override
    public void getContentByCategoryId(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {

                callback.onLoading();
            }
        }

        //根据分类载入数据
        Integer targetPage = pagerInfo.get(categoryId);
        if (targetPage == null) {
            targetPage = DEFAULT_PAGE;
        }
        Call<HomePagerContent> task = createTask(categoryId, targetPage);


        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    HomePagerContent homePagerContent = response.body();
                    Log.d("home_pager_oresenter", "homepagercontent--->" + homePagerContent.toString());
                    //把数据给到UI

                    handleHomePagerContentResult(homePagerContent,categoryId);
                } else {
                    handleNetError(categoryId);
                }

            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {

                Log.d(TAG, "onFailure: "+t.toString());
            handleNetError(categoryId);
            }
        });
    }

    private Call<HomePagerContent> createTask(int categoryId, Integer targetPage) {
        Retrofit retrofit = RetrofitManager.getInstance().getmRetrofit();
        Api api = retrofit.create(Api.class);
        pagerInfo.put(categoryId, targetPage);
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPage);
        return api.getHomePagerContent(homePagerUrl);
    }

    private void handleNetError(int catgoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == catgoryId) {
                callback.onNetWorkError();
            }
        }
    }

    private void handleHomePagerContentResult(HomePagerContent homePagerContent,int categoryId) {
        //通知UI层更新数据

        List<HomePagerContent.DataBean> data = homePagerContent.getData();
        for (ICategoryPagerCallback callback : callbacks) {
                if (callback.getCategoryId() == categoryId) {

                    if (homePagerContent == null || homePagerContent.getData().size() == 0) {
                        callback.onEmpty();
                    } else {
                        List<HomePagerContent.DataBean> looperData = data.subList(data.size() - 5, data.size());
                        callback.onLooprListLoaded(looperData);
                        callback.onContentLoad(homePagerContent.getData(),categoryId);
                    }
                }
            }
    }

    @Override
    public void loaderMore(int categoryId) {
        //加载更多数据
        //1.拿到当前页面
        Integer currentpage = pagerInfo.get(categoryId);
        if (currentpage != null) {
            currentpage = 1;
        }
        //2.页码++
        currentpage++;

        //3.加载数据
        Call<HomePagerContent> task = createTask(categoryId, currentpage);

        //4处理数据
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    HomePagerContent body = response.body();
                    Log.d(TAG, "onResponse: "+body.toString());
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {

            }
        });
    }

    @Override
    public void reload(int catgoryId) {

    }

    private List<ICategoryPagerCallback> callbacks = new ArrayList<>();


    @Override
    public void registerViewCallback(ICategoryPagerCallback callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);

        }
    }

    @Override
    public void unregisterViewCallback(ICategoryPagerCallback callback) {
        callback = null;
    }

}
