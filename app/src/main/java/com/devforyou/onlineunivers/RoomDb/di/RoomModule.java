package com.devforyou.onlineunivers.RoomDb.di;

import android.app.Application;

import androidx.room.Room;

import com.devforyou.onlineunivers.RoomDb.AppDatabase;
import com.devforyou.onlineunivers.RoomDb.Dao.ChatDao;
import com.devforyou.onlineunivers.RoomDb.Dao.CourseDao;
import com.devforyou.onlineunivers.RoomDb.Dao.LessonsDao;
import com.devforyou.onlineunivers.RoomDb.repository.ChatDataSource;
import com.devforyou.onlineunivers.RoomDb.repository.ChatRepository;
import com.devforyou.onlineunivers.RoomDb.repository.CourseDataSource;
import com.devforyou.onlineunivers.RoomDb.repository.CourseRepository;
import com.devforyou.onlineunivers.RoomDb.repository.LessonDataSource;
import com.devforyou.onlineunivers.RoomDb.repository.LessonDataSource_Factory;
import com.devforyou.onlineunivers.RoomDb.repository.LessonsRepository;
import com.devforyou.onlineunivers.entity.Course;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private AppDatabase Database;

    public RoomModule(Application mApplication) {
        Database = Room.databaseBuilder(mApplication, AppDatabase.class, "ez_tut_db").build();
    }

    @Singleton
    @Provides
    AppDatabase providesRoomDatabase() {
        return Database;
    }

    @Singleton
    @Provides
    ChatDao providesChatDao(AppDatabase Database) {
        return Database. getChatDao();
    }

    @Singleton
    @Provides
    ChatRepository productRepository(ChatDao chatDao) {
        return new ChatDataSource(chatDao);
    }


    @Singleton
    @Provides
    LessonsDao providesLesonsDao(AppDatabase Database) {
        return Database. getLesonsDao();
    }

    @Singleton
    @Provides
    LessonsRepository lessonsRepository(LessonsDao lessonsDao) {
        return new LessonDataSource(lessonsDao);
    }


    @Singleton
    @Provides
    CourseDao providesCourseDao(AppDatabase Database) {
        return Database. getCourseDao();
    }

    @Singleton
    @Provides
    CourseRepository courseRepository(CourseDao courseDao) {
        return new CourseDataSource(courseDao);
    }


}
