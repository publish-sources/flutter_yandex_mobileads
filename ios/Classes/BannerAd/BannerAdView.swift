/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import Flutter
import YandexMobileAds

final class BannerAdView: NSObject, FlutterPlatformView {

    let banner: YMAAdView

    private weak var methodChannel: FlutterMethodChannel?
    private weak var eventChannel: FlutterEventChannel?

    init(
        adUnitId: String,
        adSize: YMAAdSize,
        delegate: BannerAdEventDelegate,
        methodChannel: FlutterMethodChannel,
        eventChannel: FlutterEventChannel
    ) {
        banner = YMAAdView(adUnitID: adUnitId, adSize: adSize)
        banner.delegate = delegate
        self.methodChannel = methodChannel
        self.eventChannel = eventChannel
    }

    func view() -> UIView {
        banner
    }

    deinit {
        methodChannel?.setMethodCallHandler(nil)
        eventChannel?.setStreamHandler(nil)
    }
}
