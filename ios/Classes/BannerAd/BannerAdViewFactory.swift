/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import Foundation
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
        let width: Int = params[.width] ?? 0
        let height: Int = params[.height] ?? 0
        let type: String = params[.type] ?? ""
        let adSize: YMAAdSize

        switch AdSizeType(rawValue: type) {
        case .flexible:
            adSize = YMAAdSize.flexibleSize(with: .init(width: width, height: height))
        case .sticky:
            adSize = YMAAdSize.stickySize(withContainerWidth: CGFloat(width))
        default:
            adSize = YMAAdSize.flexibleSize(with: .init(width: 0, height: 0))
        }

        let delegate = BannerAdEventDelegate()
        let name = "\(YandexMobileAdsPlugin.channelName).\(BannerAdCommandProvider.name).\(id)"
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
}

private enum AdSizeType: String {
    case flexible, sticky
}

private enum BannerCreationParameter: String {
    case adUnitId, id, width, height, type
}

private extension Dictionary where Key == String {
    subscript<T>(_ key: BannerCreationParameter) -> T? {
        self[key.rawValue] as? T
    }
}
