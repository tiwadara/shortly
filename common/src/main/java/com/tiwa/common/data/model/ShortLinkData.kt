package com.tiwa.common.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tiwa.common.constant.Constants.SHORT_LINK_TABLE

@Entity(tableName = SHORT_LINK_TABLE)
data class ShortLinkData(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val code: String,
    val full_share_link: String,
    val full_short_link: String,
    val full_short_link2: String,
    val original_link: String,
    val share_link: String,
    val short_link: String,
    val short_link2: String
)
