/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.calls

import android.content.Context
import com.yandex.mobile.ads.common.MobileAds
import io.flutter.plugin.common.MethodChannel.Result

internal class MobileAdsCommandProvider(
    private val context: Context,
) : CommandProvider {

    override val name = "mobileAds"
    override val commands = mapOf(
        INITIALIZE to ::initialize,
        ENABLE_LOGGING to ::enableLogging,
        ENABLE_DEBUG_ERROR_INDICATOR to ::enableDebugErrorIndicator,
        SET_LOCATION_CONSENT to ::setLocationConsent,
        SET_USER_CONSENT to ::setUserConsent,
        SET_AGE_RESTRICTED_USER to ::setAgeRestrictedUser,
    )

    private fun initialize(args: Any?, result: Result) {
        MobileAds.initialize(context) {
            result.success()
        }
    }

    private fun enableLogging(args: Any?, result: Result) {
        MobileAds.enableLogging(true)
        result.success()
    }

    private fun enableDebugErrorIndicator(args: Any?, result: Result) {
        MobileAds.enableDebugErrorIndicator(true)
        result.success()
    }

    private fun setLocationConsent(args: Any?, result: Result) {
        val value = args as? Boolean
            ?: return result.error("value", "value must be bool", null)
        MobileAds.setLocationConsent(value)
        result.success()
    }

    private fun setUserConsent(args: Any?, result: Result) {
        val value = args as? Boolean
            ?: return result.error("value", "value must be bool", null)
        MobileAds.setUserConsent(value)
        result.success()
    }

    private fun setAgeRestrictedUser(args: Any?, result: Result) {
        val value = args as? Boolean
            ?: return result.error("value", "value must be bool", null)
        MobileAds.setAgeRestrictedUser(value)
        result.success()
    }

    private companion object {

        const val INITIALIZE = "initialize"
        const val ENABLE_LOGGING = "enableLogging"
        const val ENABLE_DEBUG_ERROR_INDICATOR = "enableDebugErrorIndicator"
        const val SET_LOCATION_CONSENT = "setLocationConsent"
        const val SET_USER_CONSENT = "setUserConsent"
        const val SET_AGE_RESTRICTED_USER = "setAgeRestrictedUser"
    }
}

