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

    val dbTestData = ShortLinkData(0, "ZZZZ", "", "", "", "", "", "", "")

    val apiResponseObj = ApiResponse(true, ShortLinkData(0,"","","","","","","",""))

    val apiResponse: String = Gson().toJson("{\n" +
            "  \"ok\": true,\n" +
            "  \"result\": {\n" +
            "      \"code\": \"KCveN\",\n" +
            "      \"short_link\": \"shrtco.de/KCveN\",\n" +
            "      \"full_short_link\": \"https://shrtco.de/KCveN\",\n" +
            "      \"short_link2\": \"9qr.de/KCveN\",\n" +
            "      \"full_short_link2\": \"https://9qr.de/KCveN\",\n" +
            "      \"share_link\": \"shrtco.de/share/KCveN\",\n" +
            "      \"full_share_link\": \"https://shrtco.de/share/KCveN\",\n" +
            "      \"original_link\": \"http://example.org/very/long/link.html\"\n" +
            "  }\n" +
            "}")

     val shortLinkList: List<ShortLinkData> = Gson().fromJson("[\n" +
             "   {\n" +
             "      \"code\":\"KCveN\",\n" +
             "      \"short_link\":\"shrtco.de/KCveN\",\n" +
             "      \"full_short_link\":\"https://shrtco.de/KCveN\",\n" +
             "      \"short_link2\":\"9qr.de/KCveN\",\n" +
             "      \"full_short_link2\":\"https://9qr.de/KCveN\",\n" +
             "      \"share_link\":\"shrtco.de/share/KCveN\",\n" +
             "      \"full_share_link\":\"https://shrtco.de/share/KCveN\",\n" +
             "      \"original_link\":\"http://example.org/very/long/link.html\"\n" +
             "   },\n" +
             "   {\n" +
             "      \"code\":\"KCveN\",\n" +
             "      \"short_link\":\"shrtco.de/KCveN\",\n" +
             "      \"full_short_link\":\"https://shrtco.de/KCveN\",\n" +
             "      \"short_link2\":\"9qr.de/KCveN\",\n" +
             "      \"full_short_link2\":\"https://9qr.de/KCveN\",\n" +
             "      \"share_link\":\"shrtco.de/share/KCveN\",\n" +
             "      \"full_share_link\":\"https://shrtco.de/share/KCveN\",\n" +
             "      \"original_link\":\"http://example.org/very/long/link.html\"\n" +
             "   },\n" +
             "   {\n" +
             "      \"code\":\"KCveN\",\n" +
             "      \"short_link\":\"shrtco.de/KCveN\",\n" +
             "      \"full_short_link\":\"https://shrtco.de/KCveN\",\n" +
             "      \"short_link2\":\"9qr.de/KCveN\",\n" +
             "      \"full_short_link2\":\"https://9qr.de/KCveN\",\n" +
             "      \"share_link\":\"shrtco.de/share/KCveN\",\n" +
             "      \"full_share_link\":\"https://shrtco.de/share/KCveN\",\n" +
             "      \"original_link\":\"http://example.org/very/long/link.html\"\n" +
             "   }\n" +
             "]", object : TypeToken<List<ShortLinkData>>(){}.type) as List<ShortLinkData>

}
