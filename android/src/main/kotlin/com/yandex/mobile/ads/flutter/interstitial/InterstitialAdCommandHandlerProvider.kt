/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.interstitial

import com.yandex.mobile.ads.flutter.common.CommandHandlerProvider
import com.yandex.mobile.ads.flutter.interstitial.command.DestroyInterstitialCommandHandler
import com.yandex.mobile.ads.flutter.interstitial.command.LoadInterstitialCommandHandler
import com.yandex.mobile.ads.flutter.interstitial.command.ShowInterstitialCommandHandler
import com.yandex.mobile.ads.interstitial.InterstitialAd

internal class InterstitialAdCommandHandlerProvider(
    ad: InterstitialAd,
    onDestroy: () -> Unit,
    adHolder: InterstitialAdHolder = InterstitialAdHolder(ad)
) : CommandHandlerProvider {

    override val name = PROVIDER_NAME
    override val commandHandlers = mapOf(
        LOAD to LoadInterstitialCommandHandler(adHolder),
        SHOW to ShowInterstitialCommandHandler(adHolder),
        DESTROY to DestroyInterstitialCommandHandler(adHolder, onDestroy),
    )

    private companion object {

        const val PROVIDER_NAME = "interstitialAd"

        const val LOAD = "load"
        const val SHOW = "show"
        const val DESTROY = "destroy"
    }
}
