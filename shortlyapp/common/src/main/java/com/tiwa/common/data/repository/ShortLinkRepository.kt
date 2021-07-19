package com.tiwa.common.data.repository

import com.tiwa.common.data.model.ShortLinkData
import com.tiwa.common.data.state.ShortLinkState
import kotlinx.coroutines.flow.Flow

interface ShortLinkRepository {
    suspend fun getNewShortLink(url: String) : Flow<ShortLinkState<Any>>
    suspend fun loadSavedShortLinks(): Flow<ShortLinkState<Any>>
    suspend fun saveShortLink(shortLink: ShortLinkData)
    suspend fun deleteSavedShortLink(position: Int)
}