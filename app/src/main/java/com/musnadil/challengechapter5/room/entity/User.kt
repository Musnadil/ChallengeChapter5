package com.musnadil.challengechapter5.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    @ColumnInfo(name = "username") var username : String,
    @ColumnInfo(name = "email") var email : String,
    @ColumnInfo(name = "password") var password : String
)
