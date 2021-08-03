package com.tiwa.common.constant

/**
 * This a Kotlin Object to hold all global constants
 */

object Constants {

    /**  Service Constants **/
    const val BASE_URL = "https://api.shrtco.de/v2/"
    const val ERROR_MESSAGE = "An unknown error occurred"
    const val NO_INTERNET_ERROR = "Couldn't reach the server. Check your internet connection"
    const val TIME_OUT : Long = 60

    /**   Database Constants **/
    const val SHORT_LINK_TABLE = "short_links_table"
    const val DATABASE_NAME = "short_links_database.db"

}