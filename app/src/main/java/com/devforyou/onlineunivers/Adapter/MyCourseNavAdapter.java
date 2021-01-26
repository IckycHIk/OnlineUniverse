package com.devforyou.onlineunivers.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.Fragment.Courses.Lessons.DataServer;
import com.devforyou.onlineunivers.Fragment.Courses.Lessons.Status;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.RoomDb.Model.CourseModel;
import com.devforyou.onlineunivers.RoomDb.di.Executor;
import com.devforyou.onlineunivers.RoomDb.repository.CourseRepository;
import com.devforyou.onlineunivers.entity.Course;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyCourseNavAdapter extends BaseQuickAdapter<CourseModelF, BaseViewHolder> {
   private CourseRepository  courseRepository;
   private  String use_id="";
   private Context  context;
   private OnClickSwitchCourse clickSwitchCourse;
private List<CircleImageView>  circleImageViews = new ArrayList<>();
    public MyCourseNavAdapter(ArrayList<CourseModelF> data,CourseRepository  courseRepository,String use_id,OnClickSwitchCourse clickSwitchCourse) {
        super(R.layout.my_course_element_nav_bar, data);

this.clickSwitchCourse = clickSwitchCourse;
        this.courseRepository =courseRepository;
        this.use_id = use_id;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CourseModelF courseModelF) {
     Picasso.get().load(courseModelF.getImg_url()).into((CircleImageView)baseViewHolder.getView(R.id.my_course_image_view_item));

        circleImageViews.add(baseViewHolder.getView(R.id.my_course_image_view_item));


        if(use_id.equals(courseModelF.getId())){

            ((CircleImageView)baseViewHolder.getView(R.id.my_course_image_view_item)).setBorderColor(R.color.green);


        }else{

            ((CircleImageView)baseViewHolder.getView(R.id.my_course_image_view_item)).setBorderColor(R.color.transparent);

        }


       baseViewHolder.getView(R.id.my_course_image_view_item).setOnClickListener(view -> {


           Executor.IOThread(() -> courseRepository.unUseCourse());
           Executor.IOThread(() ->courseRepository.useCourse(true,courseModelF.getId()));

           for(int i =0;i<circleImageViews.size();i++) {

             circleImageViews.get(i).setBorderColor(R.color.transparent);

           }
           ((CircleImageView)baseViewHolder.getView(R.id.my_course_image_view_item)).setBorderColor(R.color.green);

           clickSwitchCourse.onClick(courseModelF);

       });


    }



    public interface OnClickSwitchCourse{
       void onClick(CourseModelF courseModelF);


    }

}

