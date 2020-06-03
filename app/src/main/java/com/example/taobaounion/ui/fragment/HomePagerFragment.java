package com.example.taobaounion.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.domain.Categores;
import com.example.taobaounion.model.domain.HomePagerContent;
import com.example.taobaounion.presenter.ICategoryPagerPresenter;
import com.example.taobaounion.presenter.impl.CategoryPagerPresenterImpl;
import com.example.taobaounion.ui.adpter.HomePagerContentAdapter;
import com.example.taobaounion.ui.adpter.looperViewPagerAdapter;
import com.example.taobaounion.utils.Constans;
import com.example.taobaounion.utils.SizeUtils;
import com.example.taobaounion.view.ICategoryPagerCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    @BindView(R.id.vp_looper)
    ViewPager vpLooper;
    @BindView(R.id.looper_point_contatner)
    LinearLayout looperPointContatner;
    @BindView(R.id.TwinkLing)
    TwinklingRefreshLayout mtwinkLing;
    private ICategoryPagerPresenter mCategoryPagerPresenter;
    private int mMaterialId;

    @BindView(R.id.home_pager_content_lists)
    public RecyclerView mcontentlist;
    @BindView(R.id.tv_categorty)
    public TextView mtvcategorty;

    private HomePagerContentAdapter mHomePagerContentAdapter;
    private String mTitle;
    private looperViewPagerAdapter mLooperViewPagerAdapter;
    private String TAG= "homepager";


    public static HomePagerFragment newInstance(Categores.DataBean itemcategory) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.KEY_HOME_PAGER_TITLE, itemcategory.getTitle());
        bundle.putInt(Constans.KEY_HOME_PAGER_MATERIAL_ID, itemcategory.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected int getRootViewResid() {
        return R.layout.fragment_pager_home;
    }

    @Override
    protected void initView(View rootView) {

        // 设置布局管理器
        mcontentlist.setLayoutManager(new LinearLayoutManager(getContext()));
        //创建适配器
        mHomePagerContentAdapter = new HomePagerContentAdapter(getContext());
        //设置适配器
        mcontentlist.setAdapter(mHomePagerContentAdapter);

        //创建ViewPager适配器
        mLooperViewPagerAdapter = new looperViewPagerAdapter();
        //设置适配器
        vpLooper.setAdapter(mLooperViewPagerAdapter);

        //设置Refresh的相关属性
        mtwinkLing.setEnableRefresh(false);
        mtwinkLing.setEnableLoadmore(true);



    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        mTitle = arguments.getString(Constans.KEY_HOME_PAGER_TITLE);
        mMaterialId = arguments.getInt(Constans.KEY_HOME_PAGER_MATERIAL_ID);

        Log.d(TAG, "loadData: " + "title--->" + mTitle);
        Log.d("homepager", "loadData: " + "id---->" + mMaterialId);
//        TODO:加载数据

        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.getContentByCategoryId(mMaterialId);
        }

    }

    @Override
    protected void initpresenter() {
        mCategoryPagerPresenter = CategoryPagerPresenterImpl.getIntance();
        mCategoryPagerPresenter.registerViewCallback(this);

    }

    @Override
    public void onContentLoad(List<HomePagerContent.DataBean> contents, int categoryId) {

        //数据列表加载到了
        mHomePagerContentAdapter.setData(contents);

        setUpstate(State.SUCCESS);

        //TODO:更新UI
        //设置标题
        mtvcategorty.setText(mTitle);
    }

    @Override
    public int getCategoryId() {
        return mMaterialId;
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
    public void onLoaderMoreError() {

    }

    @Override
    public void onLoaderMoreEmpty() {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents) {
        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.unregisterViewCallback(this);
        }
    }

    @Override
    public void onLooprListLoaded(List<HomePagerContent.DataBean> contents) {
        mLooperViewPagerAdapter.setData(contents);
        int dx = (Integer.MAX_VALUE / 2) % contents.size();
        int tagecenterPosition = (Integer.MAX_VALUE / 2) - dx;
        vpLooper.setCurrentItem(tagecenterPosition);

        looperPointContatner.removeAllViews();
        //添加滑动点
        for (int i = 0; i < contents.size(); i++) {

            View view = new View(getContext());

            int px = SizeUtils.dip2px(getContext(), 8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(px, px);
            layoutParams.setMargins(13, 0, 13, 0);
            view.setLayoutParams(layoutParams);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.shape_indicator_select);
            } else {
                view.setBackgroundResource(R.drawable.shape_indicator_normal);
            }

            looperPointContatner.addView(view);
        }
    }

    @Override
    protected void initListener() {
        vpLooper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realeposion = position % mLooperViewPagerAdapter.getDataSize();

                updataLooperIndicator(realeposion);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mtwinkLing.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                Log.d(TAG, "onLoadMore: "+"触发了londmore");
                //TODO:去加载更多内容
//                mtwinkLing.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //TODO:模拟加载数据
//                        //结束刷新
//                        mtwinkLing.finishLoadmore();
//                        Toast.makeText(getContext(), "更新100条数据", Toast.LENGTH_SHORT).show();
//                    }
//                }, 3000);

                if (mCategoryPagerPresenter != null) {
                    mCategoryPagerPresenter.loaderMore(mMaterialId);
                }
            }
        });
    }

    private void updataLooperIndicator(int realeposion) {
        for (int i = 0; i < looperPointContatner.getChildCount(); i++) {
            View v = looperPointContatner.getChildAt(i);
            if (i == realeposion) {
                v.setBackgroundResource(R.drawable.shape_indicator_select);
            } else {
                v.setBackgroundResource(R.drawable.shape_indicator_normal);

            }
        }
    }
}
