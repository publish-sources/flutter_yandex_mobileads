/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import YandexMobileAds

final class InterstitialAdViewController: BaseFullscreenAdViewController, YMAInterstitialAdDelegate {
    func interstitialAd(_ interstitialAd: YMAInterstitialAd, didFailToShowWithError error: Error) {
        super.adDidFailToShow(error)
    }
    
    func interstitialAdDidShow(_ interstitialAd: YMAInterstitialAd) {
        fullScreenEvendDelegate.respond(.onAdShown)
    }
    
    func interstitialAdDidDismiss(_ interstitialAd: YMAInterstitialAd) {
        super.adDidDismiss()
    }
    
    func interstitialAdDidClick(_ ad: YMAInterstitialAd) {
        fullScreenEvendDelegate.respond(.onAdClicked)
    }

    func interstitialAd(_ ad: YMAInterstitialAd, didTrackImpressionWith impressionData: YMAImpressionData?) {
        fullScreenEvendDelegate.respond(.onAdImpression, ["impressionData": impressionData?.rawData])
    }
}
