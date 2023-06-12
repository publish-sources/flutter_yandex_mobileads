/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.interstitial.command

import com.yandex.mobile.ads.flutter.common.AdCreator
import com.yandex.mobile.ads.flutter.common.CommandError
import com.yandex.mobile.ads.flutter.common.CommandHandler
import com.yandex.mobile.ads.flutter.interstitial.InterstitialAdCommandHandlerProvider
import com.yandex.mobile.ads.flutter.interstitial.InterstitialEventListener
import com.yandex.mobile.ads.flutter.util.ActivityContextHolder
import com.yandex.mobile.ads.flutter.util.error
import com.yandex.mobile.ads.interstitial.InterstitialAd
import io.flutter.plugin.common.MethodChannel.Result

internal class CreateInterstitialCommandHandler(
    private val activityContextHolder: ActivityContextHolder,
    private val adCreator: AdCreator,
) : CommandHandler {

    override fun handleCommand(command: String, args: Any?, result: Result) {
        val context = activityContextHolder.activityContext ?: return

        val adUnitId = args as? String
        if (adUnitId.isNullOrEmpty()) {
            result.error(CommandError.NoAdUnitId)
            return
        }
        val ad = InterstitialAd(context)
        val listener = InterstitialEventListener()
        ad.setAdUnitId(adUnitId)
        ad.setInterstitialAdEventListener(listener)
        adCreator.createAd(result, INTERSTITIAL_AD, listener) {
            InterstitialAdCommandHandlerProvider(ad, it)
        }
    }

    private companion object {

        const val INTERSTITIAL_AD = "interstitialAd"
    }
}
