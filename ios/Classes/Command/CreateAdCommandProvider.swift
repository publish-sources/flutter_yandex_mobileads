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

class CreateAdCommandProvider: CommandProvider {

    let messenger: FlutterBinaryMessenger
    var commands: [Command] {
        [
            .command(.createAd(.interstitialAd), createInterstitialAd),
            .command(.createAd(.rewardedAd), createRewardedAd),
        ]
    }

    private var idCount = 0

    static let name = "createAd"

    init(messenger: FlutterBinaryMessenger) {
        self.messenger = messenger
    }

    private func createInterstitialAd(args: Any?, result: MethodCallResult) {
        let adUnitID = args as? String ?? ""
        let ad = YMAInterstitialAd(adUnitID: adUnitID)
        let delegate = InterstitialAdEventDelegate()
        ad.delegate = delegate
        createAd(result: result, channelName: InterstitialAdCommandProvider.name, delegate: delegate) {
            InterstitialAdCommandProvider(ad: ad, onDestroy: $0)
        }
    }

    private func createRewardedAd(args: Any?, result: MethodCallResult) {
        let adUnitID = args as? String ?? ""
        let ad = YMARewardedAd(adUnitID: adUnitID)
        let delegate = RewardedAdEventDelegate()
        ad.delegate = delegate
        createAd(result: result, channelName: RewardedAdCommandProvider.name, delegate: delegate) {
            RewardedAdCommandProvider(ad: ad, onDestroy: $0)
        }
    }

    private func createAd(
            result: MethodCallResult,
            channelName: String,
            delegate: EventDelegate,
            providerFactory: (_ onDestroy: @escaping () -> Void) -> CommandProvider
    ) {
        let id = idCount
        idCount += 1
        let name = "\(YandexMobileAdsPlugin.channelName).\(channelName).\(id)"
        let methodChannel = FlutterMethodChannel(name: name, binaryMessenger: messenger)
        let eventChannel = FlutterEventChannel(name: "\(name).events", binaryMessenger: messenger)
        let provider = providerFactory {
            methodChannel.setMethodCallHandler(nil)
            eventChannel.setStreamHandler(nil)
        }
        methodChannel.setMethodCallHandler(provider.callHandler)
        eventChannel.setStreamHandler(delegate)
        result.success(id)
    }
}
