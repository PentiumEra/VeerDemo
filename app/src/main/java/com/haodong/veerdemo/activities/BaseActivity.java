package com.haodong.veerdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * @author linghailong
 * @date on 2018/11/6
 * @email 105354999@qq.com
 * @describe :
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected abstract int getContentLayoutId();
    protected void initWidget(){
        ButterKnife.bind(this);
    }
    protected void initData(){

    }
    protected void initWindows(){

    }

    /**
     * 初始化相关参数
     * @param bundle
     * @return 如果参数正确 返回true  错误返回false
     */
    protected boolean initArgs(Bundle bundle){
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化窗口
        initWindows();

        if (initArgs(getIntent().getExtras())){
            //得到界面id并设置到界面中
            int layoutId=getContentLayoutId();
            setContentView(layoutId);
            initWidget();
            initData();
        }else {
            finish();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面返回时，finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 垃圾回收
        System.gc();
        System.runFinalization();
    }
}
