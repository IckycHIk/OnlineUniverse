package com.devforyou.onlineunivers.RoomDb.repository;

import androidx.lifecycle.LiveData;

import com.devforyou.onlineunivers.RoomDb.Dao.CourseDao;
import com.devforyou.onlineunivers.entity.Course;

import java.util.List;

import javax.inject.Inject;

public class CourseDataSource implements CourseRepository {

    private CourseDao courseDao;

    @Inject
    public CourseDataSource(CourseDao courseDao){
        this.courseDao = courseDao;

    }

    @Override
    public LiveData<Course> findById(long id) {
        return courseDao.findById(id);
    }

    @Override
    public LiveData<Course> findById(String F_id) {
        return courseDao.findById(F_id);
    }
    @Override
    public LiveData<List<Course>> findAll() {
        return courseDao.findAll();
    }

    @Override
    public long insert(Course course) {
        return courseDao.insert(course);
    }

    @Override
    public int delete(Course course) {
        return 0;
    }

    @Override
    public int getSize() {
        return courseDao.getDataCount();
    }

    @Override
    public int useCourse(boolean uses, String F_id) {
        return courseDao.useCourse(uses,F_id);
    }

    @Override
    public int unUseCourse() {
        return courseDao.unUseCourse();
    }

    @Override
    public LiveData<Course> findUse() {
        return courseDao.findUse();
    }


}
