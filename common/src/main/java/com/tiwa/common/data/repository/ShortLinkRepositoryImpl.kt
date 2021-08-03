package com.tiwa.common.data.repository

import com.tiwa.common.data.state.ShortLinkState
import com.tiwa.common.data.api.ShortLinkService
import com.tiwa.common.data.dao.ShortLinkDao
import com.tiwa.common.data.model.ShortLinkData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject



class ShortLinkRepositoryImpl @Inject constructor(
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

    override suspend fun loadSavedShortLinks() : Flow<ShortLinkState<Any>>  =  flow {
        val savedShortLinks = shortLinkDao.getAllShortLinks()
        emit(ShortLinkState.ShortLinkListReturned(savedShortLinks))
    }

    override suspend fun deleteSavedShortLink(position: Int) =  shortLinkDao.deleteShortLink(position)


    override suspend fun saveShortLink(shortLink: ShortLinkData) =  shortLinkDao.insertShortLink(shortLink)
}