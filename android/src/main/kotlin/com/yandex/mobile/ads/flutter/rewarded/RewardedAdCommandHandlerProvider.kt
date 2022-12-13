/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.rewarded

import com.yandex.mobile.ads.flutter.common.CommandHandlerProvider
import com.yandex.mobile.ads.flutter.rewarded.command.DestroyRewardedCommandHandler
import com.yandex.mobile.ads.flutter.rewarded.command.LoadRewardedCommandHandler
import com.yandex.mobile.ads.flutter.rewarded.command.ShowRewardedCommandHandler
import com.yandex.mobile.ads.rewarded.RewardedAd

internal class RewardedAdCommandHandlerProvider(
    ad: RewardedAd,
    onDestroy: () -> Unit,
    adHolder: RewardedAdHolder = RewardedAdHolder(ad)
) : CommandHandlerProvider {

    override val name = PROVIDER_NAME
    override val commandHandlers = mapOf(
        LOAD to LoadRewardedCommandHandler(adHolder),
        SHOW to ShowRewardedCommandHandler(adHolder),
        DESTROY to DestroyRewardedCommandHandler(adHolder, onDestroy),
    )

    private companion object {

        const val PROVIDER_NAME = "rewardedAd"

        const val LOAD = "load"
        const val SHOW = "show"
        const val DESTROY = "destroy"
    }
}
