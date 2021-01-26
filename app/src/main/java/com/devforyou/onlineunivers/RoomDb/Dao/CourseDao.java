package com.devforyou.onlineunivers.RoomDb.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.devforyou.onlineunivers.entity.Course;
import com.devforyou.onlineunivers.entity.Lesson;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM Course WHERE id = :id")
    LiveData<Course> findById(long id);

    @Query("SELECT * FROM Course WHERE F_id = :F_id")
    LiveData<Course> findById(String F_id);


    @Query("SELECT * FROM Course")
    LiveData<List<Course>> findAll();

    @Insert
    long insert(Course course);

    @Update
    void update(Course course);

    @Delete
    int delete(Course course);

    @Query("SELECT COUNT(*) FROM Course")
    int getDataCount();

    @Query("DELETE FROM Course")
    void deleteTable();

    @Query("UPDATE Course SET use = :uses WHERE F_id = :F_id")
    int useCourse(boolean uses, String F_id);

    @Query("UPDATE Course SET use = 0 WHERE use = 1")
    int unUseCourse();

    @Query("SELECT * FROM Course  WHERE use = 1")
    LiveData<Course> findUse();

}
