package com.star.e_learning.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.star.e_learning.bean.Course;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Query("SELECT * FROM Course ORDER BY open_date desc")
    LiveData<List<Course>> fetchAllCourses();

    @Query("SELECT * FROM Course WHERE id =:id")
    LiveData<Course> getCourse(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourses(List<Course> courses);

    @Delete
    void deleteCourse(Course course);

    @Query("DELETE FROM Course")
    void deleteAllCourses();

    @Update
    void updateCourse(Course course);

}
