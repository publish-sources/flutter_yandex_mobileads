/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.rewarded.command

import android.content.Context
import com.yandex.mobile.ads.flutter.common.AdCreator
import com.yandex.mobile.ads.flutter.common.CommandError
import com.yandex.mobile.ads.flutter.common.CommandHandler
import com.yandex.mobile.ads.flutter.rewarded.RewardedAdCommandHandlerProvider
import com.yandex.mobile.ads.flutter.rewarded.RewardedEventListener
import com.yandex.mobile.ads.rewarded.RewardedAd
import io.flutter.plugin.common.MethodChannel.Result
import com.yandex.mobile.ads.flutter.util.error

internal class CreateRewardedCommandHandler(
    private val context: Context,
    private val adCreator: AdCreator
) : CommandHandler {


    override fun handleCommand(command: String, args: Any?, result: Result) {
        val adUnitId = args as? String
        if (adUnitId.isNullOrEmpty()) {
            result.error(CommandError.NoAdUnitId)
            return
        }
        val ad = RewardedAd(context)
        val listener = RewardedEventListener()
        ad.setAdUnitId(adUnitId)
        ad.setRewardedAdEventListener(listener)
        adCreator.createAd(result, REWARDED_AD, listener) { RewardedAdCommandHandlerProvider(ad, it) }
    }

    private companion object {

        const val REWARDED_AD = "rewardedAd"
    }
}
