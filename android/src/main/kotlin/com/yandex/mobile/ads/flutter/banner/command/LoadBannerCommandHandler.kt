/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.banner.command

import com.yandex.mobile.ads.flutter.banner.BannerHolder
import com.yandex.mobile.ads.flutter.common.CommandError
import com.yandex.mobile.ads.flutter.common.CommandHandler
import io.flutter.plugin.common.MethodChannel.Result
import com.yandex.mobile.ads.flutter.util.toAdRequest
import com.yandex.mobile.ads.flutter.util.success
import com.yandex.mobile.ads.flutter.util.error

internal class LoadBannerCommandHandler(
    private val bannerHolder: BannerHolder
) : CommandHandler {

    override fun handleCommand(command: String, args: Any?, result: Result) {
        val args = args as? Map<String, Any?>
            ?: return result.error(CommandError.MissingArgsCast)
        val banner = bannerHolder.banner?.view
            ?: return result.error(CommandError.BannerIsNull)

        banner.loadAd(args.toAdRequest())
        result.success()
    }
}
