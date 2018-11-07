package com.haodong.veerdemo.recycler;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haodong.veerdemo.R;

import java.util.List;

/**
 * @author linghailong
 * @date on 2018/11/6
 * @email 105354999@qq.com
 * @describe :
 */
public class IndexAdapter extends BaseQuickAdapter<ItemEntity,BaseViewHolder> {
    // 加载图片策略
    // 请求的选项
    private static final RequestOptions REQUEST_OPTIONS=
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();
    public IndexAdapter(int layoutResId, @Nullable List<ItemEntity> data) {
        super(layoutResId, data);
        init();
    }

    public IndexAdapter(@Nullable List<ItemEntity> data) {
        super(data);
        init();
    }


    public IndexAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemEntity item) {
        final String imgUrl=item.getImgUrl();
        final String title=item.getTitle();
        final String category=item.getCategory();
        helper.setText(R.id.tv_item_title,title)
                .setText(R.id.tv_item_category,category);
        Glide.with(mContext)
                .load(imgUrl)
                .apply(REQUEST_OPTIONS)
                .into((ImageView) helper.getView(R.id.iv_item));

    }

    private void init() {
        // 动画加载效果
        openLoadAnimation();
        // 多次执行动画
        isFirstOnly(false);
    }

}
