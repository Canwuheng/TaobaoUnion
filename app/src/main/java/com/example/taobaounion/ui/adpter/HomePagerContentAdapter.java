package com.example.taobaounion.ui.adpter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.model.domain.HomePagerContent;
import com.example.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

public class HomePagerContentAdapter extends RecyclerView.Adapter<HomePagerContentAdapter.InnerHolder> {


    private List<HomePagerContent.DataBean> datas = new ArrayList<>();

    public HomePagerContentAdapter(Context context) {
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        HomePagerContent.DataBean dataBean = datas.get(position);
        holder.setData(dataBean);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<HomePagerContent.DataBean> contents) {
        datas.clear();
        this.datas.addAll(contents);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item)
        ImageView ivItem;
        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_pricenow)
        TextView tvItemPricenow;
        @BindView(R.id.tv_item_priceold)
        TextView tvItemPrice;
        @BindView(R.id.tv_item_volume)
        TextView tvItemvolume;
        @BindView(R.id.tv_item_preferential)
        TextView tvItemPreferential;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        public void setData(HomePagerContent.DataBean dataBean) {
            Context context = itemView.getContext();
            String final_price = dataBean.getZk_final_price();
            long coupon_amount = dataBean.getCoupon_amount();
            float l = Float.parseFloat(final_price) - Float.valueOf(coupon_amount);
            long volume = dataBean.getVolume();

            //TODO:不清楚那个是折扣价和原价
            tvItemTitle.setText(dataBean.getTitle());
            tvItemPreferential.setText(String.format(context.getString(R.string.text_good_off_price),coupon_amount));
            tvItemPricenow.setText(String.format(context.getString(R.string.text_good_final_price),l));
            tvItemPrice.setText("￥"+final_price+"元");
            tvItemPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvItemvolume.setText(String.format(context.getString(R.string.text_good_volume),volume));

            Glide.with(context).load(UrlUtils.getCoverPath(dataBean.getPict_url())).into(ivItem);


        }
    }
}
