package com.tiwa.common.data.state

import com.tiwa.common.data.model.ShortLinkData
import kotlinx.coroutines.flow.Flow

sealed class ShortLinkState<out R> {
    object Loading : ShortLinkState<Nothing>()
    data class NewShortLinkReturned(val data: ShortLinkData) : ShortLinkState<ShortLinkData>()
    data class ShortLinkListReturned(val data: Flow<List<ShortLinkData>>) : ShortLinkState<Flow<List<ShortLinkData>>>()
    data class Failed<out T>(val data: String?) : ShortLinkState<Nothing>()
}