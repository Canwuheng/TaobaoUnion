package com.example.taobaounion.ui.adpter;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.taobaounion.model.domain.Categores;
import com.example.taobaounion.ui.fragment.HomeFragment;
import com.example.taobaounion.ui.fragment.HomePagerFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdpter extends FragmentPagerAdapter {


    private List<Categores.DataBean> categoreList=new ArrayList<>();

    public HomePagerAdpter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public int getCount() {
        Log.d("ViewPager", "setcategoryes: "+categoreList.size());
        return categoreList.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Categores.DataBean dataBean = categoreList.get(position);
        HomePagerFragment homePagerFragment = HomePagerFragment.newInstance(dataBean);
        return homePagerFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoreList.get(position).getTitle();
    }

    public void setcategoryes(Categores categoryes) {
        categoreList.clear();
        List<Categores.DataBean> data = categoryes.getData();
        categoreList.addAll(data);
        Log.d("ViewPager", "size: "+categoreList.size());
        notifyDataSetChanged();
    }
}
