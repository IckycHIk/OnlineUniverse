package com.devforyou.onlineunivers.RoomDb.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.devforyou.onlineunivers.entity.message;

import java.util.List;

@Dao
public interface ChatDao {


    @Query("SELECT * FROM message WHERE id = :id")
    LiveData<message> findById(int id);

    @Query("SELECT * FROM message")
    LiveData<List<message>> findAll();

    @Insert
    long insert(message message);

    @Update
    void update(message message);

    @Delete
    int delete(message message);

    @Query("DELETE FROM message")
    void nukeTable();

}
