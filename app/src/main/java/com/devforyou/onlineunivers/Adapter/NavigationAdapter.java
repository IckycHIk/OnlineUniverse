package com.devforyou.onlineunivers.Adapter;


import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.devforyou.onlineunivers.Adapter.provider.FirstProvider;
import com.devforyou.onlineunivers.Adapter.provider.SecondProvider;
import com.devforyou.onlineunivers.entity.NavigationEntity;
import com.devforyou.onlineunivers.entity.NavigationEntitySecondNode;
import com.devforyou.onlineunivers.interf.OnClickAddSecondProvider;
import com.devforyou.onlineunivers.interf.SecondProvideInClick;

import java.util.List;

public class NavigationAdapter  extends BaseNodeAdapter {


    public  NavigationAdapter(SecondProvideInClick spic, OnClickAddSecondProvider click) {
        super();
        addNodeProvider(new FirstProvider(click));

        addNodeProvider(new SecondProvider(spic));
    }


    @Override
    protected int getItemType(List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof NavigationEntity) {
            return 1;
        } else if (node instanceof NavigationEntitySecondNode) {
            return 2;
        }

        return -1;
    }

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;
}
