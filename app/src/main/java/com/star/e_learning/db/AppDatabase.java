package com.star.e_learning.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.star.e_learning.api.Utils;
import com.star.e_learning.bean.Course;
import com.star.e_learning.bean.Material;
import com.star.e_learning.bean.Teacher;
import com.star.e_learning.bean.User;
import com.star.e_learning.dao.CourseDao;
import com.star.e_learning.dao.MaterialDao;
import com.star.e_learning.dao.TeacherDao;
import com.star.e_learning.dao.UserDao;

@Database(entities = {Course.class, Teacher.class, Material.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

//    private static volatile AppDatabase INSTANCE;
//
//    public static AppDatabase getDatabase(Context context){
//        if (INSTANCE == null) {
//            synchronized (AppDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context,
//                            AppDatabase.class, Utils.DB_NAME).build();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    public abstract CourseDao courseDao();
    public abstract TeacherDao teacherDao();
    public abstract MaterialDao materialDao();
    public abstract UserDao userDao();

}
