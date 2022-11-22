/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.rewarded

import com.yandex.mobile.ads.flutter.calls.Command
import com.yandex.mobile.ads.flutter.calls.CommandProvider
import com.yandex.mobile.ads.rewarded.RewardedAd
import io.flutter.plugin.common.MethodChannel.Result

class RewardedAdCommandProvider(
    ad: RewardedAd,
    private val onDestroy: () -> Unit,
) : CommandProvider {

    override val name = "rewardedAd"
    override val commands = mapOf<String, Command>(
        LOAD to ::load,
        SHOW to ::show,
        DESTROY to ::destroy,
    )
    private var ad: RewardedAd? = ad

    private fun load(args: Any?, result: Result) {
        val args = args as? Map<String, Any?>
            ?: return result.error("args", "args must be Map<String, Object?>", null)
        ad?.loadAd(args.toAdRequest())
        result.success()
    }

    private fun show(args: Any?, result: Result) {
        ad?.show()
        result.success()
    }

    private fun destroy(args: Any?, result: Result) {
        ad?.destroy()
        ad = null
        onDestroy()
        result.success()
    }

    private companion object {

        const val LOAD = "load"
        const val SHOW = "show"
        const val DESTROY = "destroy"
    }
}
