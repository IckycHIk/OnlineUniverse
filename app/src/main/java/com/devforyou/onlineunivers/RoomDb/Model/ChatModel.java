package com.devforyou.onlineunivers.RoomDb.Model;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devforyou.onlineunivers.entity.message;

import java.util.List;


public class ChatModel extends ViewModel {

    private final MutableLiveData<message> selected = new MutableLiveData<>();

    private final MutableLiveData<List<message>> selectedList = new MutableLiveData<>();


    public void select(message message) {
        selected.setValue(message);
    }

    public void selectList(List<message> messageList) {

        selectedList.setValue(messageList);
    }


    public LiveData<List<message>> getSelectedList() {
        return selectedList;
    }

    public LiveData<message> getSelected() {
        return selected;
    }


}
