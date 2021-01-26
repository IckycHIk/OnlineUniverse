package com.devforyou.onlineunivers.RoomDb.repository;

import androidx.lifecycle.LiveData;

import com.devforyou.onlineunivers.entity.message;

import java.util.List;

public interface ChatRepository {


    LiveData<message> findById(int id);

    LiveData<List<message>> findAll();

    long insert(message message);

    int delete(message message);

}
