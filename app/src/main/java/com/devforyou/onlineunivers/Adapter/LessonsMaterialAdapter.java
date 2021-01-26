package com.devforyou.onlineunivers.Adapter;


import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.text.Html;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;




import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.devforyou.onlineunivers.FireBase.Model.LessonsModelF;
import com.devforyou.onlineunivers.Fragment.Courses.Lessons.DataServer;
import com.devforyou.onlineunivers.Fragment.Courses.Lessons.Status;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.entity.Lesson;
import com.devforyou.onlineunivers.entity.QuickMultipleEntity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.SimpleMainThreadMediaPlayerListener;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import java.util.List;

public class LessonsMaterialAdapter extends BaseMultiItemQuickAdapter<QuickMultipleEntity, BaseViewHolder> {

    public void setLesson(LessonsModelF lesson) {
        this.lesson = lesson;
    }

    private LessonsModelF lesson;



    public LessonsMaterialAdapter(List<QuickMultipleEntity> data, LessonsModelF lesson) {
        super(data);
        addItemType(QuickMultipleEntity.TEXT, R.layout.lessons_material_text_view);
        addItemType(QuickMultipleEntity.IMG, R.layout.lessons_material_image_view);
        addItemType(QuickMultipleEntity.VIDEO,R.layout.lessons_material_video_view);
        this.lesson = lesson;
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(@NonNull BaseViewHolder helper, QuickMultipleEntity item) {


        switch (helper.getItemViewType()) {
            case QuickMultipleEntity.TEXT:
                helper.setText(R.id.tv1, Html.fromHtml(item.getContent(), Html.FROM_HTML_MODE_COMPACT));
                break;
            case QuickMultipleEntity.IMG:
                Picasso.get().load(item.getImg()).into((ImageView) helper.itemView.findViewById(R.id.iv));


                break;

            case QuickMultipleEntity.VIDEO:


                break;

            default:
                break;
        }



    }

}


