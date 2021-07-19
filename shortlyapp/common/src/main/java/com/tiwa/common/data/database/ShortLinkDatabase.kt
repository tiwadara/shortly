package com.tiwa.common.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tiwa.common.data.dao.ShortLinkDao
import com.tiwa.common.data.model.ShortLinkData


@Database(entities = [ ShortLinkData::class], version = 3, exportSchema = false)

abstract class ShortLinkDatabase : RoomDatabase() {
    abstract fun shortLinkDao(): ShortLinkDao
}




