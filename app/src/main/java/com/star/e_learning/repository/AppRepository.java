package com.star.e_learning.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.star.e_learning.bean.Course;
import com.star.e_learning.bean.Material;
import com.star.e_learning.bean.Teacher;
import com.star.e_learning.bean.User;
import com.star.e_learning.dao.CourseDao;
import com.star.e_learning.dao.MaterialDao;
import com.star.e_learning.dao.TeacherDao;
import com.star.e_learning.dao.UserDao;
import com.star.e_learning.db.AppDatabase;

import java.util.List;

import static com.star.e_learning.util.AppConfig.DB_NAME;

public class AppRepository implements UserDao, CourseDao, TeacherDao, MaterialDao {

    private AppDatabase appDatabase;

    public AppRepository(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    //user dao
    public void insertUser(final User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().insertUser(user);
                return null;
            }
        }.execute();
    }

    @Override
    public void insertUsers(final List<User> users) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().insertUsers(users);
                return null;
            }
        }.execute();
    }

    @Override
    public void deleteUser(final User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().deleteUser(user);
                return null;
            }
        }.execute();
    }

    @Override
    public void updateUser(final User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().updateUser(user);
                return null;
            }
        }.execute();
    }

    @Override
    public LiveData<List<User>> fetchAllUsers() {
        return appDatabase.userDao().fetchAllUsers();
    }

    @Override
    public LiveData<User> getUser(int id) {
        return appDatabase.userDao().getUser(id);
    }

    @Override
    public LiveData<User> getUserByEmail(String email) {
        return appDatabase.userDao().getUserByEmail(email);
    }

    //course dao
    @Override
    public void insertCourse(final Course course) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.courseDao().insertCourse(course);
                return null;
            }
        }.execute();
    }

    @Override
    public LiveData<List<Course>> fetchAllCourses() {
        return appDatabase.courseDao().fetchAllCourses();
    }

    @Override
    public LiveData<Course> getCourse(int id) {
        return appDatabase.courseDao().getCourse(id);
    }

//    @Override
//    public Flowable<List<Course>> fetchAllCourses() {
//        return appDatabase.courseDao().fetchAllCourses();
//    }
//
//    @Override
//    public Flowable<Course> getCourse(int id) {
//        return appDatabase.courseDao().getCourse(id);
//    }

    @Override
    public void insertCourses(final List<Course> courses) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.courseDao().insertCourses(courses);
                return null;
            }
        }.execute();
    }

    @Override
    public void deleteCourse(final Course course) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.courseDao().deleteCourse(course);
                return null;
            }
        }.execute();
    }

    @Override
    public void deleteAllCourses() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.courseDao().deleteAllCourses();
                return null;
            }
        }.execute();
    }

    @Override
    public void updateCourse(final Course course) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.courseDao().updateCourse(course);
                return null;
            }
        }.execute();
    }

    @Override
    public void insertMaterial(final Material material) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.materialDao().insertMaterial(material);
                return null;
            }
        }.execute();
    }

    @Override
    public LiveData<List<Material>> fetchAllMaterials() {
        return appDatabase.materialDao().fetchAllMaterials();
    }

    @Override
    public LiveData<Material> getMaterial(int id) {
        return appDatabase.materialDao().getMaterial(id);
    }

    @Override
    public void deleteMaterialByCourseId(final String id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.materialDao().deleteMaterialByCourseId(id);
                return null;
            }
        }.execute();
    }

    @Override
    public LiveData<Material> getMaterialByCourseId(String id) {
        return appDatabase.materialDao().getMaterialByCourseId(id);
    }

    @Override
    public void insertMaterials(final List<Material> materials) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.materialDao().insertMaterials(materials);
                return null;
            }
        }.execute();
    }

    @Override
    public void deleteMaterial(final Material material) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.materialDao().deleteMaterial(material);
                return null;
            }
        }.execute();
    }

    @Override
    public void updateMaterial(final Material material) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.materialDao().updateMaterial(material);
                return null;
            }
        }.execute();
    }

    @Override
    public void insertTeacher(final Teacher teacher) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.teacherDao().insertTeacher(teacher);
                return null;
            }
        }.execute();
    }

    @Override
    public LiveData<List<Teacher>> fetchAllTeachers() {
        return appDatabase.teacherDao().fetchAllTeachers();
    }

    @Override
    public LiveData<Teacher> getTeacher(int uid) {
        return appDatabase.teacherDao().getTeacher(uid);
    }

    @Override
    public LiveData<List<Teacher>> getTeacherByCourseId(String  cid) {
        return appDatabase.teacherDao().getTeacherByCourseId(cid);
    }

    @Override
    public void insertTeachers(final List<Teacher> teachers) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.teacherDao().insertTeachers(teachers);
                return null;
            }
        }.execute();
    }

    @Override
    public void deleteTeacher(final Teacher teacher) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.teacherDao().deleteTeacher(teacher);
                return null;
            }
        }.execute();
    }

    @Override
    public void deleteTeacherByCourseId(final String cid) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.teacherDao().deleteTeacherByCourseId(cid);
                return null;
            }
        }.execute();
    }

    @Override
    public void updateTeacher(final Teacher teacher) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.teacherDao().updateTeacher(teacher);
                return null;
            }
        }.execute();
    }

    //course dao


}
