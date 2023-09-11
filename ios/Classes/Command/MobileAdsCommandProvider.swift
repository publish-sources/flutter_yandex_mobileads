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

final class MobileAdsCommandProvider: CommandProvider {

    var commands: [Command] {
        [
            .command(.mobileAds(.enableLogging), enableLogging),
            .command(.mobileAds(.enableDebugErrorIndicator), enableDebugErrorIndicator),
            .command(.mobileAds(.setLocationConsent), setLocationConsent),
            .command(.mobileAds(.setUserConsent), setUserConsent),
        ]
    }

    let name = "mobileAds"

    private func enableLogging(args: Any?, result: MethodCallResult) {
        YMAMobileAds.enableLogging()
        result.success()
    }

    private func enableDebugErrorIndicator(args: Any?, result: MethodCallResult) {
        YMAMobileAds.enableVisibilityErrorIndicator(for: .hardware)
        YMAMobileAds.enableVisibilityErrorIndicator(for: .simulator)
        result.success()
    }

    private func setLocationConsent(args: Any?, result: MethodCallResult) {
        guard let value = args as? Bool else {
            return result.error(.argsIsNotBool)
        }
        YMAMobileAds.setLocationTrackingEnabled(value)
        result.success()
    }

    private func setUserConsent(args: Any?, result: MethodCallResult) {
        guard let value = args as? Bool else {
            return result.error(.argsIsNotBool)
        }
        YMAMobileAds.setUserConsent(value)
        result.success()
    }
}
