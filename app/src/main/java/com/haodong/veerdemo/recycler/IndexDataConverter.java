package com.haodong.veerdemo.recycler;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.util.ArrayList;

/**
 * @author linghailong
 * @date on 2018/11/6
 * @email 105354999@qq.com
 * @describe :
 */
public class IndexDataConverter {
    private static final String TAG="IndexDataConverter";
    protected final ArrayList<ItemEntity> ENTITIES=new ArrayList<>();
    private String mJsonData=null;

    //总数据条数
    private int mTotal = 0;
    //一页显示几条数据
    private final int PAGE_SIZE = 6;

    public IndexDataConverter setJsonData(String json){
        this.mJsonData=json;
        return this;
    }
    protected String getJsonData(){
        if(mJsonData==null||mJsonData.isEmpty()){
            throw new NullPointerException("DATA IS NULL OR DATA IS EMPTY");
        }
        return mJsonData;
    }

    /**
     *获取所有数据
     * @return
     */
    public ArrayList<ItemEntity>convert(){
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        mTotal=dataArray.size();
        for (int i=0;i<mTotal;i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final Integer id=data.getInteger("id");
            final String title=data.getString("title");
            final String category=data.getString("category");
            final String thumb_url=data.getString("thumb_url");
            final String page_url=data.getString("page_url");
            final ItemEntity entity=new ItemEntity();
            entity.setId(id);
            entity.setTitle(title);
            entity.setCategory(category);
            entity.setImgUrl(thumb_url);
            entity.setThumb(page_url);
            Log.e(TAG,entity.toString());
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }




}
