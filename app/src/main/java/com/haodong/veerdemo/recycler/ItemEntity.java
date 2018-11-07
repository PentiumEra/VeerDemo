package com.haodong.veerdemo.recycler;

/**
 * @author linghailong
 * @date on 2018/11/6
 * @email 105354999@qq.com
 * @describe :
 */
public class ItemEntity  {
    private String imgUrl;
    private String title;
    private String category;
    private String thumb;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", thumb='" + thumb + '\'' +
                ", id=" + id +
                '}';
    }
}
