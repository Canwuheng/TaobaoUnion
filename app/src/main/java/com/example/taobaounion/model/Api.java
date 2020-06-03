package com.example.taobaounion.model;

import com.example.taobaounion.model.domain.Categores;
import com.example.taobaounion.model.domain.HomePagerContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {
    //开头不要斜杠
    @GET("discovery/categories")
    Call<Categores> getCategores();

    @GET
    Call<HomePagerContent> getHomePagerContent(@Url String url );
}
