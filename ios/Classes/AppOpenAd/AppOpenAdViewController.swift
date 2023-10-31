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
import UIKit

final class AppOpenAdViewController: UIViewController {
    private let fullScreenEvendDelegate: FullScreenEventDelegate
    
    init(fullScreenEvendDelegate: FullScreenEventDelegate = FullScreenEventDelegate()) {
        self.fullScreenEvendDelegate = fullScreenEvendDelegate
        super.init(nibName: nil, bundle: nil)
        modalPresentationStyle = .overCurrentContext
    }
    
    required init?(coder: NSCoder) {
        fullScreenEvendDelegate = FullScreenEventDelegate()
        super.init(coder: coder)
        modalPresentationStyle = .overCurrentContext
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        guard self.presentedViewController == nil else {
            return
        }
        super.touchesBegan(touches, with: event)
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        guard self.presentedViewController == nil else {
            return
        }
        super.touchesMoved(touches, with: event)
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        guard self.presentedViewController == nil else {
            return
        }
        super.touchesEnded(touches, with: event)
    }
    
    override func touchesCancelled(_ touches: Set<UITouch>, with event: UIEvent?) {
        guard self.presentedViewController == nil else {
            return
        }
        super.touchesCancelled(touches, with: event)
    }
}

// MARK: FlutterStreamHandler

extension AppOpenAdViewController: FlutterStreamHandler {
    func onListen(withArguments arguments: Any?, eventSink events: @escaping FlutterEventSink) -> FlutterError? {
        fullScreenEvendDelegate.onListen(withArguments: arguments, eventSink: events)
    }
    
    func onCancel(withArguments arguments: Any?) -> FlutterError? {
        fullScreenEvendDelegate.onCancel(withArguments: arguments)
    }
}

// MARK: YMAAppOpenAdDelegate

extension AppOpenAdViewController: YMAAppOpenAdDelegate {
    func appOpenAd(_ interstitialAd: YMAAppOpenAd, didFailToShowWithError error: Error) {
        dismiss(animated: false)
        fullScreenEvendDelegate.respond(.onAdFailedToShow, error.toMap())
    }
    
    func appOpenAdDidShow(_ interstitialAd: YMAAppOpenAd) {
        fullScreenEvendDelegate.respond(.onAdShown)
    }
    
    func appOpenAdDidDismiss(_ interstitialAd: YMAAppOpenAd) {
        dismiss(animated: false)
        fullScreenEvendDelegate.respond(.onAdDismissed)
    }
    
    func appOpenAdDidClick(_ ad: YMAAppOpenAd) {
        fullScreenEvendDelegate.respond(.onAdClicked)
    }

    func appOpenAd(_ ad: YMAAppOpenAd, didTrackImpressionWith impressionData: YMAImpressionData?) {
        fullScreenEvendDelegate.respond(.onAdImpression, ["impressionData": impressionData?.rawData])
    }
}
