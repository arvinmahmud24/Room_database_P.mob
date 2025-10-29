package com.example.roomdatabase_140

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DataKewarganegaraanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dataKewarganegaraan: DataKewarganegaraan)

    @Update
    fun update(dataKewarganegaraan: DataKewarganegaraan)

    @Delete
    fun delete(dataKewarganegaraan: DataKewarganegaraan)

    @Query("DELETE FROM DataKewarganegaraan")
    fun deleteAll()

    @Query("SELECT * from DataKewarganegaraan ORDER BY nik ASC")
    fun getAll(): LiveData<List<DataKewarganegaraan>>

    @Query("SELECT * from DataKewarganegaraan WHERE nik = :nik")
    fun getDataByNik(nik: String): DataKewarganegaraan



}
