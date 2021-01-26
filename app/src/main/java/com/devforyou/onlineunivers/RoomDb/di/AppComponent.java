package com.devforyou.onlineunivers.RoomDb.di;

import android.app.Application;

import androidx.fragment.app.Fragment;

import com.devforyou.onlineunivers.Activity.MainActivity;
import com.devforyou.onlineunivers.RoomDb.AppDatabase;
import com.devforyou.onlineunivers.RoomDb.Dao.ChatDao;
import com.devforyou.onlineunivers.RoomDb.Dao.CourseDao;
import com.devforyou.onlineunivers.RoomDb.Dao.LessonsDao;
import com.devforyou.onlineunivers.RoomDb.repository.ChatRepository;
import com.devforyou.onlineunivers.RoomDb.repository.CourseRepository;
import com.devforyou.onlineunivers.RoomDb.repository.LessonsRepository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class})
public interface AppComponent  {



    void inject(MainActivity mainActivity);

    ChatDao chatDao();
    LessonsDao lessonDao();
    CourseDao courseDao();


    AppDatabase demoDatabase();


    LessonsRepository lessonsRepositoru();
    CourseRepository courseRepository();


    ChatRepository chatRepository();


    Application application();
}
