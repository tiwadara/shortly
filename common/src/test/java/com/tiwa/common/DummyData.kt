package com.tiwa.common

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tiwa.common.data.model.ApiResponse
import com.tiwa.common.data.model.ShortLinkData

@Suppress("UNCHECKED_CAST")
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DummyData {
    
    const val url = "test.com"

    val apiResponseObj = ApiResponse(true, ShortLinkData(0,"","","","","","","",""))

    val shortLinkList: List<ShortLinkData> = Gson().fromJson( DependencyProvider.getResponseFromJson("response-list"), object : TypeToken<List<ShortLinkData>>(){}.type) as List<ShortLinkData>

}
