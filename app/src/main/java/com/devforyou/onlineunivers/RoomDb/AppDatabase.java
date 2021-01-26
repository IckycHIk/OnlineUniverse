package com.devforyou.onlineunivers.RoomDb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.devforyou.onlineunivers.RoomDb.Dao.ChatDao;
import com.devforyou.onlineunivers.RoomDb.Dao.CourseDao;
import com.devforyou.onlineunivers.RoomDb.Dao.LessonsDao;
import com.devforyou.onlineunivers.entity.Course;
import com.devforyou.onlineunivers.entity.Lesson;
import com.devforyou.onlineunivers.entity.message;


@Database(entities = {message.class,Lesson.class, Course.class}, version = 1,exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    public abstract ChatDao getChatDao();
    public abstract LessonsDao getLesonsDao();
    public abstract CourseDao getCourseDao();
}
