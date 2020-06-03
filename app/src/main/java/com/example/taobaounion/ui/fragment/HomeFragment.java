package com.example.taobaounion.ui.fragment;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.domain.Categores;
import com.example.taobaounion.presenter.IHomePresenter;
import com.example.taobaounion.presenter.impl.HomePresenterImpl;
import com.example.taobaounion.ui.adpter.HomePagerAdpter;
import com.example.taobaounion.view.IHomeCallback;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;

/**
 * 首页界面
 */
public class HomeFragment extends BaseFragment implements IHomeCallback {

    @BindView(R.id.tab_mune)
    public TabLayout tab_mune;
    private IHomePresenter mhomePresenter;
    @BindView(R.id.mViewpager)
    public ViewPager homepager;
    private HomePagerAdpter homePagerAdpter;
    private List<Categores.DataBean> datas;

    @Override
    protected int getRootViewResid() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        tab_mune.setupWithViewPager(homepager);
        homePagerAdpter = new HomePagerAdpter(getChildFragmentManager());
        homepager.setAdapter(homePagerAdpter);

    }


    @Override
    protected void initpresenter() {
        //创建Presenter
        mhomePresenter = new HomePresenterImpl();
        mhomePresenter.registerViewCallback(this);
    }

    @Override
    protected void loadData() {
        setUpstate(State.LOADING);
        //加载数据
        mhomePresenter.getCategories();
    }

    @Override
    protected void release() {
        //取消回调注册
        if (mhomePresenter != null) {
            mhomePresenter.unregisterViewCallback(this);

        }
    }

    @Override
    public void onCategoriesLoaded(Categores categoryes) {

        //加载的数据就会从这里回来
        setUpstate(State.SUCCESS);
        if (homePagerAdpter != null) {
            //预加载界面
//            homepager.setOffscreenPageLimit(1);
            homePagerAdpter.setcategoryes(categoryes);
        }

    }

    @Override
    public void onNetWorkError() {
        setUpstate(State.ERROR);
    }

    @Override
    public void onLoading() {
        setUpstate(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setUpstate(State.EMPTY);
    }

    @Override
    protected void onretryClick() {
        //网络手动重新加载
        if (mhomePresenter != null) {
            mhomePresenter.getCategories();
        }
    }
}
