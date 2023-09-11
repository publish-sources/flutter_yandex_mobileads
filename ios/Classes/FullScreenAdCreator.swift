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

final class FullScreenAdCreator {

    let messenger: FlutterBinaryMessenger

    private var idCount = 0

    init(messenger: FlutterBinaryMessenger) {
        self.messenger = messenger
    }
    
    func createAppOpenAd(ad: YMAAppOpenAd) -> Int {
        let delegate = AppOpenAdEventDelegate()
        ad.delegate = delegate
        return createAd(channelName: appOpenAdChannelName, delegate: delegate) {
            AppOpenAdCommandProvider(ad: ad, onDestroy: $0)
        }
    }

    func createInterstitialAd(ad: YMAInterstitialAd) -> Int {
        let delegate = InterstitialAdEventDelegate()
        ad.delegate = delegate
        return createAd(channelName: interstitialAdChannelName, delegate: delegate) {
            InterstitialAdCommandProvider(ad: ad, onDestroy: $0)
        }
    }

    func createRewardedAd(ad: YMARewardedAd) -> Int {
        let delegate = RewardedAdEventDelegate()
        ad.delegate = delegate
        return createAd(channelName: rewardedAdChannelName, delegate: delegate) {
            RewardedAdCommandProvider(ad: ad, onDestroy: $0)
        }
    }
    
    private func createAd(
        channelName: String,
        delegate: FullScreenEventDelegate,
        providerFactory: (_ onDestroy: @escaping () -> Void) -> CommandProvider
    ) -> Int {
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
        return id
    }
    
    private let appOpenAdChannelName = "appOpenAd"
    private let interstitialAdChannelName = "interstitialAd"
    private let rewardedAdChannelName = "rewardedAd"
}

