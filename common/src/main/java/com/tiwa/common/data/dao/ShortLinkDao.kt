package com.tiwa.common.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tiwa.common.constant.Constants.SHORT_LINK_TABLE
import com.tiwa.common.data.model.ShortLinkData
import kotlinx.coroutines.flow.Flow

@Dao
interface ShortLinkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShortLink(link: ShortLinkData)

    @Query("DELETE FROM $SHORT_LINK_TABLE WHERE id is :position  ")
    suspend fun deleteShortLink(position : Int)

    @Query("SELECT * FROM $SHORT_LINK_TABLE")
    fun getAllShortLinks(): Flow<List<ShortLinkData>>

    @Query("SELECT * FROM $SHORT_LINK_TABLE WHERE original_link is :url")
    fun getShortLinkByUrl(url: String): ShortLinkData?

}
