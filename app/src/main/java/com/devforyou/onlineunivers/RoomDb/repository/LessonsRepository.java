package com.devforyou.onlineunivers.RoomDb.repository;

import androidx.lifecycle.LiveData;

import com.devforyou.onlineunivers.entity.Lesson;
import com.devforyou.onlineunivers.entity.message;

import java.util.List;

public interface LessonsRepository {


    LiveData<Lesson> findById(long id);

    LiveData<List<Lesson>> findAll();

    long insert(Lesson lesson);

    int getSize();

    int delete(Lesson lesson);



}
