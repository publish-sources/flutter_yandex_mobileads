/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.banner

import com.yandex.mobile.ads.flutter.calls.Command
import com.yandex.mobile.ads.flutter.calls.CommandProvider
import io.flutter.plugin.common.MethodChannel.Result

internal class BannerAdCommandProvider(
    banner: BannerAdView,
    private val onDestroy: () -> Unit,
) : CommandProvider {

    override val name = "bannerAd"
    override val commands = mapOf<String, Command>(
        LOAD to ::load,
        DESTROY to ::destroy,
    )
    private var banner: BannerAdView? = banner

    private fun load(args: Any?, result: Result) {
        val args = args as? Map<String, Any?>
            ?: return result.error("args", "args must be Map<String, Object?>", null)
        banner?.view?.loadAd(args.toAdRequest())
        result.success()
    }

    private fun destroy(args: Any?, result: Result) {
        banner?.view?.destroy()
        banner = null
        onDestroy()
        result.success()
    }

    private companion object {

        const val LOAD = "load"
        const val DESTROY = "destroy"
    }
}
