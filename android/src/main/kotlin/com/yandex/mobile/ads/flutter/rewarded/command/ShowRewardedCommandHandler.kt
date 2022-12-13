/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.rewarded.command

import com.yandex.mobile.ads.flutter.common.CommandHandler
import com.yandex.mobile.ads.flutter.rewarded.RewardedAdHolder
import io.flutter.plugin.common.MethodChannel.Result
import com.yandex.mobile.ads.flutter.util.success

internal class ShowRewardedCommandHandler(
    private val adHolder: RewardedAdHolder
) : CommandHandler {

    override fun handleCommand(command: String, args: Any?, result: Result) {
        adHolder.ad?.show()
        result.success()
    }
}
