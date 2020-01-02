package com.star.e_learning.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.star.e_learning.bean.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM User")
    LiveData<List<User>> fetchAllUsers();

    @Query("SELECT * FROM User WHERE userid =:id")
    LiveData<User> getUser(int id);

    @Query("SELECT * FROM User WHERE email =:email")
    LiveData<User> getUserByEmail(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<User> users);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

}
