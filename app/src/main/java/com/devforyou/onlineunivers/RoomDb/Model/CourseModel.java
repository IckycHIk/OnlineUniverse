package com.devforyou.onlineunivers.RoomDb.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devforyou.onlineunivers.entity.Course;
import com.devforyou.onlineunivers.entity.message;

import java.util.List;

public class CourseModel extends ViewModel {

    private final MutableLiveData<Course> selected = new MutableLiveData<>();

    private final MutableLiveData<List<Course>> selectedList = new MutableLiveData<>();


    public void select(Course course) {
        selected.setValue(course);
    }

    public void selectList(List<Course> courseList) {

        selectedList.setValue(courseList);
    }


    public LiveData<List<Course>> getSelectedList() {
        return selectedList;
    }

    public LiveData<Course> getSelected() {
        return selected;
    }


}
