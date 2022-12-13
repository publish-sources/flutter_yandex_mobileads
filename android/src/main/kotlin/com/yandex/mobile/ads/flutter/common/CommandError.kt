/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.common

internal sealed class CommandError(val tag: String, val description: String, val args: Any?) {

    object NoAdUnitId : CommandError("Ad Unit Id", "Empty Ad Unit Id", null)

    object BannerIsNull : CommandError(
        "Banner",
        "Banner cannot be null. Maybe you destroyed it",
        null
    )

    object RewardedIsNull : CommandError(
        "Rewarded",
        "Rewarded cannot be null. Maybe you destroyed it",
        null
    )

    object InterstitialIsNull : CommandError(
        "Interstitial",
        "Interstitial cannot be null. Maybe you destroyed it",
        null
    )

    object ArgsMustBeBool: CommandError("Args", "Args must be bool", null)

    object MissingArgsCast : CommandError("Args", "Args must be Map<String, Object?>", null)

    object CommandNotImplemented: CommandError("Command", "Command not implemented", null)

}
