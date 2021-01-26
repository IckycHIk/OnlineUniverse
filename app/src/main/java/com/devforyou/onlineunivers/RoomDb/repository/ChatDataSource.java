package com.devforyou.onlineunivers.RoomDb.repository;

import androidx.lifecycle.LiveData;

import com.devforyou.onlineunivers.RoomDb.Dao.ChatDao;
import com.devforyou.onlineunivers.entity.message;

import java.util.List;

import javax.inject.Inject;

public class ChatDataSource implements ChatRepository {

    private ChatDao chatDao;

    @Inject
    public ChatDataSource(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @Override
    public LiveData<message> findById(int id) {
        return chatDao.findById(id);
    }

    @Override
    public LiveData<List<message>> findAll() {
        return chatDao.findAll();
    }

    @Override
    public long insert(message ms) {
        return chatDao.insert(ms);
    }

    @Override
    public int delete(message ms) {
        return chatDao.delete(ms);
    }
}

