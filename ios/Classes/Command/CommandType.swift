/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import Foundation

enum CommandType {
    case mobileAds(MobileAdsCommand)
    case createAd(CreateAdCommand)
    case bannerAd(BannerAdCommand)
    case interstitialAd(InterstitialAdCommand)
    case rewardedAd(RewardedAdCommand)

    var name: String {
        switch self {
        case .mobileAds:
            return "mobileAds"
        case .createAd:
            return "create"
        case .bannerAd:
            return "bannerAd"
        case .interstitialAd:
            return "interstitialAd"
        case .rewardedAd:
            return "rewardedAd"
        }
    }

    var commandName: String {
        switch self {
        case .mobileAds(let command):
            return command.rawValue
        case .createAd(let command):
            return command.rawValue
        case .bannerAd(let command):
            return command.rawValue
        case .interstitialAd(let command):
            return command.rawValue
        case .rewardedAd(let command):
            return command.rawValue
        }
    }
}

enum CreateAdCommand: String {
    case interstitialAd
    case rewardedAd
}

enum MobileAdsCommand: String {
    case enableLogging
    case enableDebugErrorIndicator
    case setLocationConsent
    case setUserConsent
}

enum BannerAdCommand: String {
    case load
    case destroy
}

enum InterstitialAdCommand: String {
    case load
    case show
    case destroy
}

enum RewardedAdCommand: String {
    case load
    case show
    case destroy
}