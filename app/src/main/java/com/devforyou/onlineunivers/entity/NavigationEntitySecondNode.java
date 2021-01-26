package com.devforyou.onlineunivers.entity;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

public class NavigationEntitySecondNode extends BaseExpandNode {



    private List<BaseNode> childNode;
    private String id;
    private String title;
    private  String type;

    public NavigationEntitySecondNode(List<BaseNode> childNode, String title) {
        this.childNode = childNode;
        this.title = title;

        setExpanded(false);
    }
    public NavigationEntitySecondNode(List<BaseNode> childNode, String title,String  id,String type) {
        this.childNode = childNode;
        this.title = title;
        this.id = id;
        this.type =type;

        setExpanded(false);
    }

    public void setChildNode(List<BaseNode> childNode) {
        this.childNode = childNode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId(){return  id;}

    public String getTitle() {
        return title;
    }

    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
