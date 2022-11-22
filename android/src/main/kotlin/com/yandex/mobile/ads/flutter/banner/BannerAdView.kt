/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.banner

import android.content.Context
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdView
import io.flutter.plugin.platform.PlatformView

internal class BannerAdView(
    context: Context,
    adUnitId: String,
    adSize: AdSize,
) : PlatformView {

    private val banner = BannerAdView(context).apply {
        setAdUnitId(adUnitId)
        setAdSize(adSize)
    }

    override fun getView() = banner

    override fun dispose() = banner.destroy()
}
