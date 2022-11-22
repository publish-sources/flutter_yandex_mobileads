/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.calls

import com.yandex.mobile.ads.common.AdRequest
import io.flutter.plugin.common.MethodChannel

typealias Command = (args: Any?, result: MethodChannel.Result) -> Unit

internal interface CommandProvider {

    val name: String
    val commands: Map<String, Command>

    fun MethodChannel.Result.success() = success(null)

    fun Map<String, Any?>.toAdRequest() = AdRequest.Builder().apply {
        (get(AGE) as? String)?.let { setAge(it) }
        (get(CONTEXT_QUERY) as? String)?.let { setContextQuery(it) }
        (get(CONTEXT_TAGS) as? List<String>)?.let { setContextTags(it) }
        (get(GENDER) as? String)?.let { setGender(it) }
        (get(PARAMETERS) as? Map<String, String>)?.let { setParameters(it) }
        (get(BIDDING_DATA) as? String)?.let { setBiddingData(it) }
    }.build()

    private companion object {

        private const val AGE = "age"
        private const val CONTEXT_QUERY = "contextQuery"
        private const val CONTEXT_TAGS = "contextTags"
        private const val GENDER = "gender"
        private const val PARAMETERS = "parameters"
        private const val BIDDING_DATA = "biddingData"
    }
}
