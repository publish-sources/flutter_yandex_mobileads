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

final class BannerAdViewFactory: NSObject, FlutterPlatformViewFactory {

    private let messenger: FlutterBinaryMessenger

    init(messenger: FlutterBinaryMessenger) {
        self.messenger = messenger
    }

    func create(
        withFrame frame: CGRect,
        viewIdentifier viewId: Int64,
        arguments args: Any?
    ) -> FlutterPlatformView {
        let params = args as? [String: Any?] ?? [:]
        let adUnitId: String = params[.adUnitId] ?? ""
        let id: Int = params[.id] ?? -1
        let width: CGFloat = params[.width] ?? 0
        let height: CGFloat = params[.height] ?? 0
        let type: String = params[.type] ?? ""
        let adSize: YMABannerAdSize

        switch AdSizeType(rawValue: type) {
        case .inline:
            adSize = YMABannerAdSize.inlineSize(withWidth: width, maxHeight: height)
        case .sticky:
            adSize = YMABannerAdSize.stickySize(withContainerWidth: width)
        default:
            adSize = YMABannerAdSize.inlineSize(withWidth: 0, maxHeight: 0)
        }

        let delegate = BannerAdEventDelegate()
        let name = "\(YandexMobileAdsPlugin.channelName).\(bannerAdChannelName).\(id)"
        let methodChannel = FlutterMethodChannel(name: name, binaryMessenger: messenger)
        let eventChannel = FlutterEventChannel(name: "\(name).events", binaryMessenger: messenger)
        let banner = BannerAdView(
                adUnitId: adUnitId,
                adSize: adSize,
                delegate: delegate,
                methodChannel: methodChannel,
                eventChannel: eventChannel
        )
        let provider = BannerAdCommandProvider(banner: banner) {
            methodChannel.setMethodCallHandler(nil)
            eventChannel.setStreamHandler(nil)
        }
        methodChannel.setMethodCallHandler(provider.callHandler)
        eventChannel.setStreamHandler(delegate)

        return banner
    }

    func createArgsCodec() -> FlutterMessageCodec & NSObjectProtocol {
        FlutterStandardMessageCodec.sharedInstance()
    }
    
    private let bannerAdChannelName = "bannerAd"
}

private enum AdSizeType: String {
    case inline, sticky
}

private enum BannerCreationParameter: String {
    case adUnitId, id, width, height, type
}

private extension Dictionary where Key == String {
    subscript<T>(_ key: BannerCreationParameter) -> T? {
        self[key.rawValue] as? T
    }
}
