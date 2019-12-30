package com.star.e_learning.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.star.e_learning.bean.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {

    @Insert
    void insertTeacher(Teacher teacher);

    @Query("SELECT * FROM Teacher")
    LiveData<List<Teacher>> fetchAllTeachers();

    @Query("SELECT * FROM Teacher WHERE userid =:uid")
    LiveData<Teacher> getTeacher(int uid);

    @Query("SELECT * FROM Teacher WHERE course_id =:cid")
    LiveData<List<Teacher>> getTeacherByCourseId(String  cid);

    @Query("DELETE FROM Teacher WHERE course_id = :cid")
    void deleteTeacherByCourseId(String cid);

    @Insert
    void insertTeachers(List<Teacher> teachers);

    @Delete
    void deleteTeacher(Teacher teacher);

    @Update
    void updateTeacher(Teacher teacher);

}
