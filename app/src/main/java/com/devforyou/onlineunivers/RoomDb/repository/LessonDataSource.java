package com.devforyou.onlineunivers.RoomDb.repository;

import androidx.lifecycle.LiveData;

import com.devforyou.onlineunivers.RoomDb.Dao.LessonsDao;
import com.devforyou.onlineunivers.entity.Lesson;

import java.util.List;

import javax.inject.Inject;

public class LessonDataSource implements LessonsRepository {


    private LessonsDao lessonsDao;

    @Inject
    public LessonDataSource(LessonsDao lessonsDao) {
        this.lessonsDao = lessonsDao;
    }


    @Override
    public LiveData<Lesson> findById(long id) {
        return lessonsDao.findById(id);
    }

    @Override
    public LiveData<List<Lesson>> findAll() {
        return lessonsDao.findAll();
    }


    @Override
    public long insert(Lesson lesson) {
        return lessonsDao.insert(lesson);
    }

    @Override
    public int getSize() {
        return lessonsDao.getDataCount();
    }

    @Override
    public int delete(Lesson lesson) {
        return lessonsDao.delete(lesson);
    }
}
