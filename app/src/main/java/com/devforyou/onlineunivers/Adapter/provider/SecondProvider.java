package com.devforyou.onlineunivers.Adapter.provider;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.devforyou.onlineunivers.Adapter.NavigationAdapter;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.entity.NavigationEntity;
import com.devforyou.onlineunivers.entity.NavigationEntitySecondNode;
import com.devforyou.onlineunivers.interf.SecondProvideInClick;

import java.util.List;

public class SecondProvider extends BaseNodeProvider {

    SecondProvideInClick spic;


   public  SecondProvider(SecondProvideInClick spic){
       super();
       this.spic = spic;
   }

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.navigation_second_provide;
    }

    @Override
    public void convert(BaseViewHolder helper, BaseNode data) {

        NavigationEntitySecondNode entity = ( NavigationEntitySecondNode) data;
        helper.setText(R.id.nav_item_sec_title, entity.getTitle());

        if (entity.isExpanded()) {
            helper.setImageResource(R.id.nav_item_sec_arrow, R.drawable.icon_left_arrow_24sp);
        } else {
            helper.setImageResource(R.id.nav_item_sec_arrow, R.drawable.icons_more_24sp);
        }
    }

    @Override
    public void onClick( BaseViewHolder helper, View view, BaseNode data, int position) {
        NavigationEntitySecondNode entity = (NavigationEntitySecondNode) data;

        if (((NavigationEntitySecondNode) data).getType().equals("lesson")) {
            spic.getIdLesson(((NavigationEntitySecondNode) data).getId());

            if (entity.isExpanded()) {
                getAdapter().collapse(position);
            } else {
                getAdapter().expandAndCollapseOther(position);
            }
        }else  if(((NavigationEntitySecondNode) data).getType().equals("test")){

            spic.getIdTest(((NavigationEntitySecondNode) data).getId());

        }
        else  if(((NavigationEntitySecondNode) data).getType().equals("chat")){

            spic.getIdChatRoom(((NavigationEntitySecondNode) data).getId());

        }
    }




}

