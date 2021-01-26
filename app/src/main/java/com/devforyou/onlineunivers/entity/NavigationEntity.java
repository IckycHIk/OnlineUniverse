package com.devforyou.onlineunivers.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

public class NavigationEntity  extends BaseExpandNode {


    private List<BaseNode> childNode;
    private String title;
    private boolean add_element_button;
    public int ACTIVITY;

    public NavigationEntity (List<BaseNode> childNode, String title, boolean add_element_button,int activity) {
        this.childNode = childNode;
        this.title = title;
        this.add_element_button = add_element_button;
        ACTIVITY = activity;

        setExpanded(false);
    }

    public String getTitle() {
        return title;
    }

    public boolean isAdd_element_button() {
        return add_element_button;
    }

    public void setAdd_element_button(boolean add_element_button) {
        this.add_element_button = add_element_button;
    }

    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
