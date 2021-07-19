package com.tiwa.common.data.event


sealed class ShortLinkEvent {
    data class GetNewShortLinkEvent(var url : String) : ShortLinkEvent()
    object GetHistoryEvent : ShortLinkEvent()
    data class DeleteShortLinkEvent(var position : Int) : ShortLinkEvent()
}
