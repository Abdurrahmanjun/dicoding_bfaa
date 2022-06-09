package com.example.submission01.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class SubmissionsDatabase : RoomDatabase() {
}