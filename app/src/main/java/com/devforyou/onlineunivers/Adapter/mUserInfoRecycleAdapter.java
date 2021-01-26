package com.devforyou.onlineunivers.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shangguansb on 2016-04-09.
 */
public class mUserInfoRecycleAdapter extends BaseQuickAdapter<CourseModelF, BaseViewHolder> {
    public ArrayList<CourseModelF> data;
private onItemClick onItemClick;
    public mUserInfoRecycleAdapter(ArrayList<CourseModelF> data ,onItemClick onItemClick) {
        super(R.layout.course_card, data);
        this.data = data;
       this.onItemClick = onItemClick;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder holder, CourseModelF courseModelF) {


        ((TextView) holder.getView(R.id.course_card_title)).setText(courseModelF.getTitle());
        ((TextView) holder.getView(R.id.course_card_rating)).setText(courseModelF.getRating()+"");
         Picasso.get().load(courseModelF.getImg_url()).into((ImageView) holder.getView(R.id.course_card_img));
        ((TextView) holder.getView(R.id.course_card_description)).setText(courseModelF.getDescription());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

           onItemClick.deleteCourse(courseModelF,holder.getPosition());
return false;
            }
        });
    }

    public  interface onItemClick{

        void deleteCourse(CourseModelF courseModelF,int position);

    }

}
