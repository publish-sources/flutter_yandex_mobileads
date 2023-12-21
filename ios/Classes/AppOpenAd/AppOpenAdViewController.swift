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

final class AppOpenAdViewController: BaseFullscreenAdViewController, YMAAppOpenAdDelegate {
    func appOpenAd(_ interstitialAd: YMAAppOpenAd, didFailToShowWithError error: Error) {
        super.adDidFailToShow(error)
    }
    
    func appOpenAdDidShow(_ interstitialAd: YMAAppOpenAd) {
        fullScreenEvendDelegate.respond(.onAdShown)
    }
    
    func appOpenAdDidDismiss(_ interstitialAd: YMAAppOpenAd) {
        super.adDidDismiss()
    }
    
    func appOpenAdDidClick(_ ad: YMAAppOpenAd) {
        fullScreenEvendDelegate.respond(.onAdClicked)
    }

    func appOpenAd(_ ad: YMAAppOpenAd, didTrackImpressionWith impressionData: YMAImpressionData?) {
        fullScreenEvendDelegate.respond(.onAdImpression, ["impressionData": impressionData?.rawData])
    }
}
