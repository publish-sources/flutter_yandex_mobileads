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
import com.yandex.mobile.ads.flutter.EventListener
import com.yandex.mobile.ads.flutter.YandexMobileAdsPlugin
import com.yandex.mobile.ads.flutter.interstitial.InterstitialAdCommandProvider
import com.yandex.mobile.ads.flutter.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.flutter.rewarded.RewardedAdCommandProvider
import com.yandex.mobile.ads.flutter.rewarded.RewardedAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.rewarded.RewardedAd
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result

internal class CreateAdCommandProvider(
    private val context: Context,
    private val messenger: BinaryMessenger,
) : CommandProvider {

    override val name = "createAd"
    override val commands = mapOf<String, Command>(
        INTERSTITIAL_AD to ::createInterstitialAd,
        REWARDED_AD to ::createRewardedAd,
    )

    private fun createInterstitialAd(args: Any?, result: Result) {
        val adUnitId = args as? String ?: ""
        val ad = InterstitialAd(context)
        val listener = InterstitialAdEventListener()
        ad.setAdUnitId(adUnitId)
        ad.setInterstitialAdEventListener(listener)
        createAd(result, INTERSTITIAL_AD, listener) { InterstitialAdCommandProvider(ad, it) }
    }

    private fun createRewardedAd(args: Any?, result: Result) {
        val adUnitId = args as? String ?: ""
        val ad = RewardedAd(context)
        val listener = RewardedAdEventListener()
        ad.setAdUnitId(adUnitId)
        ad.setRewardedAdEventListener(listener)
        createAd(result, REWARDED_AD, listener) { RewardedAdCommandProvider(ad, it) }
    }

    private inline fun createAd(
        result: Result,
        channelName: String,
        listener: EventListener,
        providerFactory: (onDestroy: () -> Unit) -> CommandProvider,
    ) {
        val id = idCount++
        val name = "${YandexMobileAdsPlugin.ROOT}.$channelName.$id"
        val methodChannel = MethodChannel(messenger, name)
        val eventChannel = EventChannel(messenger, "$name.events")
        val provider = providerFactory {
            methodChannel.setMethodCallHandler(null)
            eventChannel.setStreamHandler(null)
        }
        methodChannel.setMethodCallHandler { call, result ->
            provider.commands[call.method]?.invoke(call.arguments, result)
                ?: result.notImplemented()
        }
        eventChannel.setStreamHandler(listener)
        result.success(id)
    }

    private companion object {

        const val INTERSTITIAL_AD = "interstitialAd"
        const val REWARDED_AD = "rewardedAd"

        var idCount = 0
    }
}
