package com.haodong.veerdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haodong.veerdemo.R;
import com.haodong.veerdemo.recycler.IndexAdapter;
import com.haodong.veerdemo.recycler.IndexDataConverter;
import com.haodong.veerdemo.recycler.ItemEntity;
import com.haodong.veerdemo.recycler.PagingBean;
import com.haodong.veerdemo.utils.CallBackUtil;
import com.haodong.veerdemo.utils.OkhttpUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;

import okhttp3.Call;


public class MainActivity extends BaseActivity {
    private static final String TAG = "MainAvtivity";
    final String LOCATION_URL = "http://mingke.veervr.tv:1920/test";
    private IndexDataConverter converter;
    private PagingBean bean;
    private ArrayList<ItemEntity> datas = new ArrayList<>();
    
    private int currentPage = 0;
    private int mPageIndex = 0;
    //当前已经显示了几条数据
    private int mCurrentCount = 0;
    //总数据条数
    private int mTotal = 0;
    private final int PAGE_SIZE = 6;

    RefreshLayout mRefreshLayout = null;
    RecyclerView mRecyclerView = null;
    IndexAdapter mAdapter = null;
    Intent intent = null;
    Bundle bundle = null;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    protected void initWidget() {
        intent = new Intent(MainActivity.this, PageActivity.class);
        bundle = new Bundle();
        converter = new IndexDataConverter();
        bean = new PagingBean();
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置 Header 为 贝塞尔雷达 样式
        mRefreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                firstPage();
                refreshLayout.finishRefresh();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mAdapter.addData(getPageData(mCurrentCount));
                currentPage++;
                mCurrentCount = mCurrentCount + 6;
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    protected void initData() {
        firstPage();
    }

    private void firstPage() {
        OkhttpUtil.okHttpGet(LOCATION_URL, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                final JSONArray dataArray = JSON.parseObject(response).getJSONArray("data");
                mTotal = dataArray.size();
                datas = converter.setJsonData(response).convert();
                currentPage = 1;
                mCurrentCount = 0;
                mAdapter = new IndexAdapter(R.layout.item_index, getPageData(mCurrentCount));
                mRecyclerView.setAdapter(mAdapter);
                currentPage++;
                mCurrentCount = mCurrentCount + 6;
                bean.addIndex();
                mAdapter.setOnItemClickListener(new IndexAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Log.e(TAG, position + "");
                        bundle.putString("thumb", datas.get(position).getThumb());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 将数据进行分页
     */
    private ArrayList<ItemEntity> getPageData(int currentCount) {
        ArrayList<ItemEntity> pageData = new ArrayList<>();
        pageData.clear();
        if (currentCount == 0) {
            for (int i = 0; i < 6; i++) {
                pageData.add(datas.get(i));
            }
            return pageData;
        }
        final int id = currentCount - 1;
        if (id < mTotal) {
            for (int i = id; i < id + PAGE_SIZE; i++) {
                pageData.add(datas.get(i));
            }
        }
        return pageData;
    }

}
