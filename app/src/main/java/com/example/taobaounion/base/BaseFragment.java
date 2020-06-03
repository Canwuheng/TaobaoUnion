package com.example.taobaounion.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taobaounion.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private View mEmptyView;
    private View mErroView;
    private View mLoadingView;
    private View mSuccessView;

    public enum State {NONE,ERROR,LOADING,EMPTY,SUCCESS}

    private State currentState = State.NONE;
    private Unbinder mBind;
    private FrameLayout mBaseContainer;

    @OnClick(R.id.network_error_tips)
    public void retry() {
        //点击重新加载
        onretryClick();
    }

    /**
     * 子类需要知道网络错误的点击覆盖
     */
    protected void onretryClick() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.base_fragment_layout, container, false);
        mBaseContainer = rootView.findViewById(R.id.base_container);
        loadStatesView(inflater, container);
        View succesView = loadSuccessView(inflater, container);
        mBind = ButterKnife.bind(this, rootView);

        initView(rootView);
        initpresenter();
        loadData();
        initListener();
        return rootView;    }


    /**
     * 设置事件
      */
    protected void initListener() {
    }

    /**
     * 加载各种状态的view
     * @param inflater
     * @param container
     */
    protected void loadStatesView(LayoutInflater inflater, ViewGroup container) {

        //成功的view
        mSuccessView = loadSuccessView(inflater, container);
        mBaseContainer.addView(mSuccessView);
        //loading的view
        mLoadingView = loadLoadingView(inflater,container);
        mBaseContainer.addView(mLoadingView);
        //erro的view
        mErroView = loadErrorView(inflater,container);

        mBaseContainer.addView(mErroView);
        //empty的view
        mEmptyView = loadEmptyView(inflater,container);
        mBaseContainer.addView(mEmptyView);
        setUpstate(State.NONE);

    }

    /**
     * 子类通过这个方法切换界面
     * @param state
     */
    public void setUpstate(State state) {
        this.currentState = state;
        mSuccessView.setVisibility(currentState == State.SUCCESS ? View.VISIBLE : View.GONE);
        mErroView.setVisibility(currentState == State.ERROR ? View.VISIBLE : View.GONE);
        mLoadingView.setVisibility(currentState == State.LOADING ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility(currentState == State.EMPTY ? View.VISIBLE : View.GONE);


    }

    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }

    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_error, container, false);
    }

    protected View loadLoadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }


    protected void initView(View rootView) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind!=null) {
            mBind.unbind();
        }
        release();
    }

    protected void release() {
        //释放资源

    };


    protected void initpresenter() {
    //创建presenter
    }

    protected void loadData() {
        //加载数据
    }

    protected View loadSuccessView(LayoutInflater inflater,ViewGroup container) {
        int resid = getRootViewResid();
        return inflater.inflate(resid, container, false);

    }

    protected abstract int getRootViewResid();
}
