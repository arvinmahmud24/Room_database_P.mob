package com.example.roomdatabase_140

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "DataKewarganegaraan")
data class DataKewarganegaraan(
    @PrimaryKey
    @ColumnInfo(name = "nama")
    val nama: String,

    @ColumnInfo(name = "nik")
    val nik: String,

    @ColumnInfo(name = "kabupaten")
    val kabupaten: String,

    @ColumnInfo(name = "kecamatan")
    val kecamatan: String,

    @ColumnInfo(name = "desa")
    val desa: String,

    @ColumnInfo(name = "rt")
    val rt: String,

    @ColumnInfo(name = "rw")
    val rw: String,

    @ColumnInfo(name = "jenisKelamin")
    val jenisKelamin: String,

    @ColumnInfo(name = "statusPernikahan")
    val statusPernikahan: String,

) : Parcelable
