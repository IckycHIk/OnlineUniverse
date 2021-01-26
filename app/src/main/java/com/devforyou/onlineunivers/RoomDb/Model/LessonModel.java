package com.devforyou.onlineunivers.RoomDb.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devforyou.onlineunivers.entity.Lesson;
import com.devforyou.onlineunivers.entity.message;

import java.util.List;

public class LessonModel extends ViewModel {


    private final MutableLiveData<Lesson> selected = new MutableLiveData<>();



    public void select(Lesson lesson) {
        selected.setValue(lesson);
    }



    public LiveData<Lesson> getSelected() {
        return selected;
    }


}
