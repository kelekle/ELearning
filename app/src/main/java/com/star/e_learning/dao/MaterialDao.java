package com.star.e_learning.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.star.e_learning.bean.Material;

import java.util.List;

@Dao
public interface MaterialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMaterial(Material material);

    @Query("SELECT * FROM Material ORDER BY create_date desc")
    LiveData<List<Material>> fetchAllMaterials();

    @Query("SELECT * FROM Material WHERE id =:id")
    LiveData<Material> getMaterial(int id);

    @Query("DELETE FROM Material WHERE id =:id")
    void deleteMaterialByCourseId(String id);

    @Query("SELECT * FROM Material WHERE id =:id")
    LiveData<Material> getMaterialByCourseId(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMaterials(List<Material> materials);

    @Delete
    void deleteMaterial(Material material);

    @Update
    void updateMaterial(Material material);

}
