package com.tiwa.shortlyapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiwa.common.data.event.ShortLinkEvent
import com.tiwa.common.data.repository.ShortLinkRepositoryImpl
import com.tiwa.common.data.state.ShortLinkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: ShortLinkRepositoryImpl): ViewModel() {

    private val eventChannel = Channel<ShortLinkEvent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<ShortLinkState<Any>>(ShortLinkState.Loading)
    val state: MutableStateFlow<ShortLinkState<Any>>
        get() = _state

    init {
        viewModelScope.launch {
            handleEvents()
        }
    }

    private suspend fun handleEvents() {
        eventChannel.consumeAsFlow().collectLatest { event ->
            when (event) {
                is ShortLinkEvent.GetNewShortLinkEvent -> {
                    repository.getNewShortLink(event.url).onEach { state ->
                        _state.value = state
                    }.launchIn(viewModelScope)
                }
                ShortLinkEvent.GetHistoryEvent -> {
                    repository.loadSavedShortLinks().onEach { state ->
                        _state.value = state
                    }.launchIn(viewModelScope)
                }
                is ShortLinkEvent.DeleteShortLinkEvent -> {
                    repository.deleteSavedShortLink(event.position)
                }
            }
        }
    }

    fun getHistory() {
        eventChannel.offer(ShortLinkEvent.GetHistoryEvent)
    }

    fun createShortLink(url: String) {
        eventChannel.offer(ShortLinkEvent.GetNewShortLinkEvent(url))
    }

    fun deleteShortLink(code: Int) {
        eventChannel.offer(ShortLinkEvent.DeleteShortLinkEvent(code))
    }
}