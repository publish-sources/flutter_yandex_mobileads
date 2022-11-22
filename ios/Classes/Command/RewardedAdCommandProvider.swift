/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import Flutter
import Foundation
import YandexMobileAds

class RewardedAdCommandProvider: CommandProvider {

    var commands: [Command] {
        [
            .command(.rewardedAd(.load), loadRewardedAd),
            .command(.rewardedAd(.show), showRewardedAd),
            .command(.rewardedAd(.destroy), destroyRewardedAd),
        ]
    }

    private let onDestroy: () -> Void
    private let ad: YMARewardedAd
    private var idCount = 0

    static let name = "rewardedAd"

    init(ad: YMARewardedAd, onDestroy: @escaping () -> Void) {
        self.ad = ad
        self.onDestroy = onDestroy
    }

    private func loadRewardedAd(args: Any?, result: MethodCallResult) {
        guard let args = args as? [String: Any?] else {
            return result.error(.argsIsNotMap)
        }
        ad.load(with: args.toAdRequest())
        result.success()
    }

    private func showRewardedAd(args: Any?, result: MethodCallResult) {
        guard let controller = Self.controller else {
            return result.error(.noViewController)
        }
        ad.present(from: controller)
        result.success()
    }

    private func destroyRewardedAd(args: Any?, result: MethodCallResult) {
        onDestroy()
        result.success()
    }
}
