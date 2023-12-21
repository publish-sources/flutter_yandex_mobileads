/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import Flutter
import YandexMobileAds

final class RewardedAdCommandProvider: CommandProvider {

    var commands: [Command] {
        [
            .command(.rewardedAd(.show), showRewardedAd),
            .command(.rewardedAd(.destroy), destroyRewardedAd),
        ]
    }

    private let onDestroy: () -> Void
    private let ad: YMARewardedAd
    private var idCount = 0
    private let rewardedAdViewController: RewardedAdViewController

    let name = "rewardedAd"

    init(
        ad: YMARewardedAd,
        onDestroy: @escaping () -> Void,
        rewardedAdViewController: RewardedAdViewController
    ) {
        self.ad = ad
        self.onDestroy = onDestroy
        self.rewardedAdViewController = rewardedAdViewController
    }

    private func showRewardedAd(args: Any?, result: MethodCallResult) {
        guard let controller = Self.controller else {
            return result.error(.noViewController)
        }
        controller.present(rewardedAdViewController, animated: false)
        ad.show(from: rewardedAdViewController)
        result.success()
    }

    private func destroyRewardedAd(args: Any?, result: MethodCallResult) {
        onDestroy()
        result.success()
    }
}
