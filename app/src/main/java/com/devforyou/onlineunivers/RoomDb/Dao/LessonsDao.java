package com.devforyou.onlineunivers.RoomDb.Dao;

import androidx.annotation.Size;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.devforyou.onlineunivers.entity.Lesson;
import com.devforyou.onlineunivers.entity.message;

import java.util.List;


@Dao
public interface LessonsDao {

    @Query("SELECT * FROM Lesson WHERE id = :id")
    LiveData<Lesson> findById(long id);

    @Query("SELECT * FROM Lesson")
    LiveData<List<Lesson>> findAll();

    @Insert
    long insert(Lesson lesson);

    @Update
    void update(Lesson lesson);

    @Delete
    int delete(Lesson lesson);

    @Query("SELECT COUNT(*) FROM Lesson")
    int getDataCount();

    @Query("DELETE FROM Lesson")
    void deleteTable();

}
