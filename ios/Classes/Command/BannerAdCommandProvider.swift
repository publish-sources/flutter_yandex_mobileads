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

class BannerAdCommandProvider: CommandProvider {

    var commands: [Command] {
        [
            .command(.bannerAd(.load), loadBannerAd),
            .command(.bannerAd(.destroy), destroyBannerAd),
        ]
    }

    private weak var banner: BannerAdView?
    private let onDestroy: () -> Void

    static let name = "bannerAd"

    init(banner: BannerAdView, onDestroy: @escaping () -> Void) {
        self.banner = banner
        self.onDestroy = onDestroy
    }

    private func loadBannerAd(args: Any?, result: MethodCallResult) {
        guard let args = args as? [String: Any?] else {
            return result.error(.argsIsNotMap)
        }
        banner?.banner.loadAd(with: args.toAdRequest())
        result.success()
    }

    private func destroyBannerAd(args: Any?, result: MethodCallResult) {
        banner = nil
        onDestroy()
        result.success()
    }
}