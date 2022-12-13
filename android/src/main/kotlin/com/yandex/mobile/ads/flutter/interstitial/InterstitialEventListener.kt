/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.interstitial

import com.yandex.mobile.ads.flutter.EventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener

internal class InterstitialEventListener : EventListener(), InterstitialAdEventListener {

    override fun onAdShown() = respond(ON_AD_SHOWN)

    override fun onAdDismissed() = respond(ON_AD_DISMISSED)
}
