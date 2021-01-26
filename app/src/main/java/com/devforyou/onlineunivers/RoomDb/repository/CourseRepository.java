package com.devforyou.onlineunivers.RoomDb.repository;

import androidx.lifecycle.LiveData;

import com.devforyou.onlineunivers.entity.Course;
import com.devforyou.onlineunivers.entity.message;

import java.util.List;

public interface CourseRepository {


    LiveData<Course> findById(long id);

    LiveData<Course> findById(String F_id);

    LiveData<List<Course>> findAll();

    long insert(Course course);

    int delete(Course course);

    int getSize();
    int useCourse(boolean uses, String F_id);

    int unUseCourse();
    LiveData<Course> findUse();
}
