package com.tiwa.common.data.repository

import com.tiwa.common.data.state.ShortLinkState
import com.tiwa.common.data.api.ShortLinkService
import com.tiwa.common.data.dao.ShortLinkDao
import com.tiwa.common.data.model.ShortLinkData
import com.tiwa.common.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject



@ExperimentalCoroutinesApi
class ShortLinkRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private var shortLinkDao: ShortLinkDao,
    private val shortLinkService: ShortLinkService,) : ShortLinkRepository {

    override suspend fun getNewShortLink(url: String): Flow<ShortLinkState<Any>> = flow {
        emit(ShortLinkState.Loading)
        try {
            val response = shortLinkService.getShortLinks(url)
            response.let {
                saveShortLink(it.result)
                emit(ShortLinkState.NewShortLinkReturned(it.result))
            }
        } catch (e: Exception) {
            emit(ShortLinkState.Failed<String>(e.message))
        }
    }

    override suspend fun getCachedShortLink(url: String): Flow<ShortLinkState<Any>> = channelFlow {
        send(ShortLinkState.Loading)
        try {
            CoroutineScope(defaultDispatcher).launch {
                val response = shortLinkDao.getShortLinkByUrl(url)
                response?.let {
                    send(ShortLinkState.ShortLinkExists)
                } ?: run {
                    getNewShortLink(url)
                }
            }
        } catch (e: Exception) {
            send(ShortLinkState.Failed<String>(e.message))
        }
        awaitClose()
    }

    override suspend fun loadSavedShortLinks() : Flow<ShortLinkState<Any>>  =  flow {
        val savedShortLinks = shortLinkDao.getAllShortLinks()
        emit(ShortLinkState.ShortLinkListReturned(savedShortLinks))
    }

    override suspend fun deleteSavedShortLink(position: Int) =  shortLinkDao.deleteShortLink(position)


    override suspend fun saveShortLink(shortLink: ShortLinkData) =  shortLinkDao.insertShortLink(shortLink)
}