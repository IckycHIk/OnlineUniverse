package com.devforyou.onlineunivers.entity;

import android.media.Image;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.w3c.dom.Text;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class QuickMultipleEntity implements MultiItemEntity {

    public static final int TEXT = 1;
    public static final int IMG = 2;
    public static final int VIDEO = 4;
    public static final int IMG_TEXT = 3;
    public static final int TEXT_SPAN_SIZE = 3;
    public static final int IMG_SPAN_SIZE = 1;
    public static final int IMG_TEXT_SPAN_SIZE = 4;
    public static final int IMG_TEXT_SPAN_SIZE_MIN = 2;
    private int itemType;
    private int spanSize;
    private String img;
    private String video_url;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public QuickMultipleEntity(int itemType, int spanSize, String content) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.content = content;
    }

    public QuickMultipleEntity(int itemType, String img,String video_url) {
        this.itemType = itemType;
        this.img =  img;
        this.video_url =video_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public QuickMultipleEntity(int itemType, String img) {
        this.itemType = itemType;
        this.img =  img;
    }



    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }


    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
