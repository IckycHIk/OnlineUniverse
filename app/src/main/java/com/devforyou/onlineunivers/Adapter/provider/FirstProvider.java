package com.devforyou.onlineunivers.Adapter.provider;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.view.ViewCompat;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.devforyou.onlineunivers.Adapter.NavigationAdapter;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.entity.NavigationEntity;
import com.devforyou.onlineunivers.interf.OnClickAddSecondProvider;

import java.util.List;
import java.util.Objects;

public class FirstProvider extends BaseNodeProvider {

    private OnClickAddSecondProvider click;

    public FirstProvider(OnClickAddSecondProvider click){

        this.click = click;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.navigation_first_provide;
    }

    @Override
    public void convert(BaseViewHolder baseViewHolder, BaseNode baseNode) {
        NavigationEntity  entity = (NavigationEntity) baseNode;

        baseViewHolder.setText(R.id.nav_item_first_title, entity.getTitle());
        baseViewHolder.setImageResource(R.id.nav_item_first_arrow, R.drawable.icons_more_24sp);

        if(entity.isAdd_element_button()){
            baseViewHolder.getView(R.id.nav_item_add).setVisibility(View.VISIBLE);

            if(entity.ACTIVITY==1)
            baseViewHolder.getView(R.id.nav_item_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.addTest();
                }
            });
            else if(entity.ACTIVITY==2){
                baseViewHolder.getView(R.id.nav_item_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        click.addChat();
                    }
                });

            }

        }
        else {
            baseViewHolder.getView(R.id.nav_item_add).setVisibility(View.INVISIBLE);
        }

        setArrowSpin(baseViewHolder, baseNode, true);

    }


    @Override
    public void convert( BaseViewHolder helper, BaseNode data,List<?> payloads) {
        for (Object payload : payloads) {
            if (payload instanceof Integer && (int) payload == NavigationAdapter.EXPAND_COLLAPSE_PAYLOAD) {

                setArrowSpin(helper, data, true);
            }
        }
    }

    private void setArrowSpin(BaseViewHolder helper, BaseNode data, boolean isAnimate) {
        NavigationEntity entity = (NavigationEntity) data;

        ImageView imageView = helper.getView(R.id.nav_item_first_arrow);

        if (entity.isExpanded()) {
            if (isAnimate) {
                ViewCompat.animate(imageView).setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .rotation(-90f)
                        .start();

                imageView.setImageResource(R.drawable.icon_left_arrow_24sp);
            } else {
                imageView.setRotation(0f);
            }
        } else {
            if (isAnimate) {
                ViewCompat.animate(imageView).setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .rotation(0f)
                        .start();
                imageView.setImageResource(R.drawable.icons_more_24sp);
            } else {
                imageView.setRotation(90f);
            }
        }
    }

    @Override
    public void onClick(BaseViewHolder helper, View view, BaseNode data, int position) {

        getAdapter().expandOrCollapse(position, true, true, NavigationAdapter.EXPAND_COLLAPSE_PAYLOAD);
    }
}
