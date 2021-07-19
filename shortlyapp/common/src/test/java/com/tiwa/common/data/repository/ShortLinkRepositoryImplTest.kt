package com.tiwa.common.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.tiwa.common.DummyData
import com.tiwa.common.DummyData.apiResponseObj
import com.tiwa.common.data.api.ShortLinkService
import com.tiwa.common.data.dao.ShortLinkDao
import com.tiwa.common.data.state.ShortLinkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShortLinkRepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var shortLinkRepository: ShortLinkRepository
    private lateinit var shortLinkService: ShortLinkService
    private lateinit var shortLinkDao: ShortLinkDao
    var testData = DummyData

    @Before
    fun setUp() {
        //Mocking shortLinkDao
        shortLinkDao = mock()
        whenever(shortLinkDao.getAllShortLinks()).thenReturn( flow { emit(testData.shortLinkList) })

        //Mocking ahortLinkService
        shortLinkService = mock()
        shortLinkRepository = ShortLinkRepositoryImpl(shortLinkDao, shortLinkService)

    }

    @Test
    fun getNewShortLink_returns_Success() {
        runBlockingTest {
            whenever(shortLinkService.getShortLinks(testData.url)).thenReturn(apiResponseObj)
            val result = shortLinkRepository.getNewShortLink(testData.url)
                assertEquals(result.first() ,ShortLinkState.Loading )
                assertEquals(result.count() , 2 )
            }
    }
}