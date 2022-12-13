package com.yandex.mobile.ads.flutter.util

import com.yandex.mobile.ads.common.AdRequest

internal fun Map<String, Any?>.toAdRequest() = AdRequest.Builder().also { adRequestBuilder ->
    (get(AGE) as? String)?.let(adRequestBuilder::setAge)
    (get(CONTEXT_QUERY) as? String)?.let(adRequestBuilder::setContextQuery)
    (get(CONTEXT_TAGS) as? List<String>)?.let(adRequestBuilder::setContextTags)
    (get(GENDER) as? String)?.let(adRequestBuilder::setGender)
    (get(PARAMETERS) as? Map<String, String>)?.let(adRequestBuilder::setParameters)
    (get(BIDDING_DATA) as? String)?.let(adRequestBuilder::setBiddingData)
}.build()

private const val AGE = "age"
private const val CONTEXT_QUERY = "contextQuery"
private const val CONTEXT_TAGS = "contextTags"
private const val GENDER = "gender"
private const val PARAMETERS = "parameters"
private const val BIDDING_DATA = "biddingData"
